package com.example.mobile_banking_api.api.user;

import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);
    UserDto findUserByName(String name);
    UserDto findUserByStudentCardId(String studentCardId);
    PageInfo<UserDto> findAllUsers(int page,int limit);
    Integer deletedUserById(Integer id);
    Integer updateIsDeletedStatusById(Integer id, boolean status);
    Integer updateUserById(Integer id,String name,String gender,String StudentCardId);

}
