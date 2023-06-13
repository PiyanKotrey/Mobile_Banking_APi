package com.example.mobile_banking_api.api.auth;

import com.example.mobile_banking_api.api.auth.web.AthDto;
import com.example.mobile_banking_api.api.auth.web.LoginDto;
import com.example.mobile_banking_api.api.auth.web.RegisterDto;
import com.example.mobile_banking_api.api.auth.web.TokenDto;

public interface AuthService {
    AthDto refreshToken(TokenDto tokenDto);
    void register(RegisterDto registerDto);
    void verify(String email);
    void checkVerify(String email,String verifiedCode);
    AthDto login(LoginDto loginDto);

}
