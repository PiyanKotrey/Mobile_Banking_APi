package com.example.mobile_banking_api.api.user.wep;

import com.example.mobile_banking_api.api.user.UserService;
import com.example.mobile_banking_api.base.BaseRest;
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
