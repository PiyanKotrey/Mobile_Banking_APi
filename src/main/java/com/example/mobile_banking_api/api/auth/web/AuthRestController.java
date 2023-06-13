package com.example.mobile_banking_api.api.auth.web;

import com.example.mobile_banking_api.api.auth.AuthService;
import com.example.mobile_banking_api.api.auth.web.RegisterDto;
import com.example.mobile_banking_api.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public BaseRest<?> refreshToken(@RequestBody TokenDto tokenDto) {
        AthDto authDto = authService.refreshToken(tokenDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Token has been refreshed")
                .timestamp(LocalDateTime.now())
                .data(authDto)
                .build();
    }

    @PostMapping("/login")
    public BaseRest<?> login(@Valid @RequestBody LoginDto loginDto){
        AthDto athDto=authService.login(loginDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Successfully for Login")
                .timestamp(LocalDateTime.now())
                .data(athDto)
                .build();
    }

    @PostMapping("/register")
    public BaseRest<?> register(@Valid @RequestBody RegisterDto registerDto){
        authService.register(registerDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Successfully for Register")
                .timestamp(LocalDateTime.now())
                .data(registerDto.email())
                .build();
    }

    @PostMapping("/verify")
    public BaseRest<?> verify(@RequestParam String email){
        authService.verify(email);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Please check email and verify")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();
    }
    @GetMapping("/check-verify")
    public BaseRest<?> checkVerified(@RequestParam String email,
                                     @RequestParam String verifiedCode){
       authService.checkVerify(email,verifiedCode);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have Been verified Successfully")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();


    }

}
