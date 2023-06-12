package com.example.mobile_banking_api.api.user.validater.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstraintValidator implements ConstraintValidator<PasswordMatch,Object> {

    private String password;
    private String confirmedPassword;
    private String message;
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmedPassword = constraintAnnotation.confirmedPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object passwordValue =new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmedPWValue = new BeanWrapperImpl(value).getPropertyValue(confirmedPassword);

        boolean isValid=false;
        if (passwordValue != null){
            isValid = passwordValue.equals(confirmedPWValue);
        }
        if (!isValid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmedPassword)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return isValid;
    }
}
