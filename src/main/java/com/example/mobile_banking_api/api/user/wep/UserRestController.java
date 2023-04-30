package com.example.mobile_banking_api.api.user.wep;

import com.example.mobile_banking_api.api.user.UserService;
import com.example.mobile_banking_api.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PutMapping("/update/{id}")
    public BaseRest<?> updateUserById(@PathVariable Integer id,@RequestBody UserDto userDto){
        Integer updateUserById = userService.updateUserById(id,userDto.name(), userDto.gender(), userDto.studentCardId());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been Update successfully")
                .timestamp(LocalDateTime.now())
                .data(updateUserById)
                .build();
    }

    @PutMapping("/{id}")
    public BaseRest<?> updateIsDeletedStatusById(@PathVariable Integer id,@RequestBody IsDeletedDto dto ){
        Integer deletedId=userService.updateIsDeletedStatusById(id,dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been Deleted successfully")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deletedUserById(@PathVariable Integer id){
        Integer deletedId=userService.deletedUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been Delete by Id successfully")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }
    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable Integer id){
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been find by Id successfully")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @GetMapping(params = "name")
    public BaseRest<?> findUserByName(@RequestParam String name){
        UserDto userDto = userService.findUserByName(name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been find by name successfully")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
    @GetMapping(params = "studentCardId")
    public BaseRest<?> findUserByStudentCardId(@RequestParam String studentCardId){
        UserDto userDto = userService.findUserByStudentCardId(studentCardId);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been find by StudentCardId successfully")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
    @GetMapping
    public BaseRest<?> findAllUsers(@RequestParam(name = "page",required = false,defaultValue = "1")int page,
                                    @RequestParam(name = "limit",required = false,defaultValue = "20")int limit){
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUsers(page, limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been find successfully")
                .timestamp(LocalDateTime.now())
                .data(userDtoPageInfo)
                .build();
    }

    @PostMapping
    public BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto userDto = userService.createNewUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been created successfully")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();

    }

}
