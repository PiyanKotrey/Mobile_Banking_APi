package com.example.mobile_banking_api.api.user;

import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User createUserDtoToUser(CreateUserDto createUserDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}