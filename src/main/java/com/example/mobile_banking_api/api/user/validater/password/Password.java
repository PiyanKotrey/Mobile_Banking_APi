package com.example.mobile_banking_api.api.user.validater.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Password {
    String message() default "What the F***! Your Password is so weak!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
