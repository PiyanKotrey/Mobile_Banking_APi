package com.example.mobile_banking_api.api.user.validater.roleId;

import com.example.mobile_banking_api.api.user.UserMapper;
import com.example.mobile_banking_api.api.user.validater.roleId.RoleIdConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, List<Integer>> {

    private final UserMapper userMapper;
    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {
        for (Integer roleId : roleIds){
            if (!userMapper.existsByRoleId(roleId)){
                return false;
            }
        }
        return true;
    }
}
