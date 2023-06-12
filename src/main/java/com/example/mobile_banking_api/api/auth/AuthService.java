package com.example.mobile_banking_api.api.auth;

import com.example.mobile_banking_api.api.auth.web.AthDto;
import com.example.mobile_banking_api.api.auth.web.LoginDto;
import com.example.mobile_banking_api.api.auth.web.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);
    void verify(String email);
    void checkVerify(String email,String verifiedCode);
    AthDto login(LoginDto loginDto);
}
