package com.example.mobile_banking_api.api.user;

import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;
    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {
        User user = userMapStruct.createUserDtoToUser(createUserDto);
        userMapper.insert(user);
        log.info("User = {}",user.getId());
        return this.findUserById(user.getId());
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userMapper.selectById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with %d is not found",id)));

        return userMapStruct.userToUserDto(user);
    }

    @Override
    public Integer deletedUserById(Integer id) {
        boolean isExisted = userMapper.existById(id);
        if (isExisted){
            userMapper.deletedById(id);
            return id;
        }
     throw new ResponseStatusException(HttpStatus.NOT_FOUND,
             String.format("User with %d is not found",id));

    }

    @Override
    public Integer updateIsDeletedStatusById(Integer id, boolean status) {
        boolean isExisted = userMapper.existById(id);
        if (isExisted){
            userMapper.updatedIsDeletedById(id,status);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with %d is not found",id));
    }
}
