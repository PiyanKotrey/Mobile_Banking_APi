package com.example.mobile_banking_api.api.auth;

import com.example.mobile_banking_api.api.auth.web.AthDto;
import com.example.mobile_banking_api.api.auth.web.LoginDto;
import com.example.mobile_banking_api.api.auth.web.RegisterDto;
import com.example.mobile_banking_api.api.user.User;
import com.example.mobile_banking_api.api.user.UserMapStruct;

import com.example.mobile_banking_api.util.MailUtill;
import jakarta.mail.MessagingException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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
    private final JwtEncoder jwtEncoder;
    @Value("${spring.mail.username}")
    private String AppMail;
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

        //define scope
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("READ"));
//        authorities.add(new SimpleGrantedAuthority("WRITE"));
//        authorities.add(new SimpleGrantedAuthority("UPDATE"));
//        authorities.add(new SimpleGrantedAuthority("DELETE"));
//        authorities.add(new SimpleGrantedAuthority("FULL_CONTROL"));
//                String scope = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.joining(""));
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth->!auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("kot")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope",scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return new AthDto(accessToken);


//       Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());
//        authentication = daoAuthenticationProvider.authenticate(authentication);
//        String basicAuthFormat = authentication.getName()+":"+authentication.getCredentials();
//        String encoding = Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());
//        return new AthDto((String.format("Basic %s", encoding)));
    }
}
