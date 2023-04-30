package com.example.mobile_banking_api.api.user;

import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public UserDto findUserByName(String name) {
        User user = userMapper.selectByName(name).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with %s is not found",name)));
        return userMapStruct.userToUserDto(user);
    }

    @Override
    public UserDto findUserByStudentCardId(String studentCardId) {
        User user = userMapper.selectByStudentCardId(studentCardId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with %s is not found",studentCardId)));
        return userMapStruct.userToUserDto(user);
    }

    @Override
    public PageInfo<UserDto> findAllUsers(int page, int limit) {
       PageInfo<User> userPageInfo= PageHelper.startPage(page,limit).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                userMapper.select();
            }
        });
        return userMapStruct.userpageInfoToUserDtopageInfo(userPageInfo);
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

    @Override
    public Integer updateUserById(Integer id, String name, String gender, String studentCardId) {
        boolean isExisted = userMapper.existById(id);
        if (isExisted){
            userMapper.updateUserById(id,name,gender,studentCardId);
            return id;
        }
       throw new ResponseStatusException(HttpStatus.NOT_FOUND,
               String.format("User with %d is not found",id));
    }



}
