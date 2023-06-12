package com.example.mobile_banking_api.api.auth.web;

import com.example.mobile_banking_api.api.user.validater.email.EmailUnique;
import com.example.mobile_banking_api.api.user.validater.password.PasswordMatch;
import com.example.mobile_banking_api.api.user.validater.roleId.RoleIdConstraint;
import com.example.mobile_banking_api.api.user.validater.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@PasswordMatch(message = "What the f*** Your Password is not match!",password="password",confirmedPassword="confirmedPassword")
public record RegisterDto(
                        @NotBlank(message = "Rq email!")
                                @EmailUnique
                        @Email String email,
                        @NotBlank(message = "Rq password!")
                                @Password
                        String password,
                        @NotBlank(message = "Rq to Confirm!")
                                @Password
                        String confirmedPassword,
                        @NotNull(message = "Rq Role!")
                                @RoleIdConstraint
                        List<Integer> roleIds) {
}
