package com.example.mobile_banking_api.api.account.wep;

import com.example.mobile_banking_api.api.account.AccountService;
import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import com.example.mobile_banking_api.api.user.wep.UserDto;
import com.example.mobile_banking_api.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/account")
public class AccountRestController {
    private final AccountService accountService;
    @GetMapping("/{id}")
    public BaseRest<?> findAccountById(@PathVariable Integer id){
        AccountDto accountDto = accountService.findAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Have Been Found By Id")
                .timestamp(LocalDateTime.now())
                .data(accountDto)
                .build();
    }
    @PostMapping
    public BaseRest<?> createNewAccount(@RequestBody @Valid CreateAccDto createAccDto){
        AccountDto accountDto = accountService.createNewAccount(createAccDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account has been created successfully")
                .timestamp(LocalDateTime.now())
                .data(accountDto)
                .build();
    }
    @DeleteMapping("/{id}")
    public BaseRest<?> deletedAccById(@PathVariable Integer id){
        Integer delete = accountService.deleteAccById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account has been deleted successfully")
                .timestamp(LocalDateTime.now())
                .data(delete)
                .build();
    }
    @PutMapping ("/{id}")
    public BaseRest<?> updateAccById(@PathVariable("id") Integer id,@RequestBody UpdateAccountDto updateAccountDto){
        AccountDto accountDto = accountService.updateAccById(id, updateAccountDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account has been Updated successfully")
                .timestamp(LocalDateTime.now())
                .data(accountDto)
                .build();
    }

}
