package com.example.mobile_banking_api.api.user;

import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;

public interface UserService {
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);
    Integer deletedUserById(Integer id);
    Integer updateIsDeletedStatusById(Integer id, boolean status);
}
