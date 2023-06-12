package com.example.mobile_banking_api.api.user.validater.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordMatchConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "The password not match!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    String password();
    String confirmedPassword();
    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        PasswordMatch[] value();
    }
}
