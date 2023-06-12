package com.example.mobile_banking_api.api.user.validater.email;

import com.example.mobile_banking_api.api.user.UserMapper;
import com.example.mobile_banking_api.api.user.validater.email.EmailUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUniqueConstraintValidator implements ConstraintValidator<EmailUnique,String> {
    private final UserMapper userMapper;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userMapper.existsByEmail(email);
    }
}
