package com.example.mobile_banking_api.api.auth;

import com.example.mobile_banking_api.api.auth.web.AthDto;
import com.example.mobile_banking_api.api.auth.web.LoginDto;
import com.example.mobile_banking_api.api.auth.web.RegisterDto;
import com.example.mobile_banking_api.api.auth.web.TokenDto;
import com.example.mobile_banking_api.api.user.User;
import com.example.mobile_banking_api.api.user.UserMapStruct;

import com.example.mobile_banking_api.util.MailUtill;
import jakarta.mail.MessagingException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j

public class AuthServiceImpl implements AuthService{
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtill mailUtill;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtAccessTokenEncoder;
    private  JwtEncoder jwtRefreshTokenEncoder;

    @Autowired
    public void setJwtRefreshTokenEncoder(@Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @Value("${spring.mail.username}")
    private String AppMail;

    @Override
    public AthDto refreshToken(TokenDto tokenDto) {
        Authentication authentication = new BearerTokenAuthenticationToken(tokenDto.refreshToken());
        authentication = jwtAuthenticationProvider.authenticate(authentication);
        Instant now = Instant.now();

        Jwt jwt = (Jwt) authentication.getCredentials();

        System.out.println(jwt.getSubject());
        System.out.println(jwt.getClaims());
        System.out.println(jwt.getClaimAsString("scope"));

        JwtClaimsSet jwtAccessTokenClaimSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(jwt.getSubject())
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = jwtAccessTokenEncoder.encode(
                JwtEncoderParameters.from(jwtAccessTokenClaimSet)).getTokenValue();

        return new AthDto("Bearer",
                accessToken,
                tokenDto.refreshToken());
    }
    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));
        log.info("User: {}",user.getEmail());
        if (authMapper.register(user)){
            for (Integer role : registerDto.roleIds()){
               authMapper.createUserRole(user.getId(),role);
            }
        }
    }

    @Override
    public void verify(String email) {

    User user = authMapper.selectByEmail(email).orElseThrow(()->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"Email have been Failed to verify"));
    String verifiedCode = UUID.randomUUID().toString();
    authMapper.updateVerifiedCode(email,verifiedCode);
    user.setVerifiedCode(verifiedCode);

    MailUtill.Meta<?> meta = MailUtill.Meta.builder()
            .to(email)
            .from(AppMail)
            .subject("Verify Account")
            .templateUrl("auth/verify")
            .data(user)
            .build();

        try {
            mailUtill.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }

    @Override
    public void checkVerify(String email, String verifiedCode) {
        User user = authMapper.selectByEmailAndVerifiedCode(email,verifiedCode).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getIsVerified()){
            authMapper.verify(email,verifiedCode);
        }
    }

    @Override
    public AthDto login(LoginDto loginDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());
        authentication = daoAuthenticationProvider.authenticate(authentication);
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth->!auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .claim("scope",scope)
                .build();
        JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope", scope)
                .build();

        String accessToken = jwtAccessTokenEncoder.encode(
                JwtEncoderParameters.from(jwtClaimsSet)
        ).getTokenValue();

        String refreshToken = jwtRefreshTokenEncoder.encode(
                JwtEncoderParameters.from(jwtRefreshClaimsSet)
        ).getTokenValue();
        return new AthDto("Bearer",
                accessToken,
                refreshToken);
    }


}
