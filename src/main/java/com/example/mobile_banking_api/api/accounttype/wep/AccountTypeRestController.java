package com.example.mobile_banking_api.api.accounttype.wep;

import com.example.mobile_banking_api.api.accounttype.AccountTypeService;
import com.example.mobile_banking_api.base.BaseRest;
import com.example.mobile_banking_api.base.BaseRest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account-type")
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;

    public AccountTypeRestController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GetMapping
    public BaseRest<?> findAll(){
        var accoutTypeDtoList = accountTypeService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type Have Been Found")
                .timestamp(LocalDateTime.now())
                .data(accoutTypeDtoList)
                .build();
    }
    @GetMapping("/{id}")
    public BaseRest<?> findAccountTypeById(@PathVariable Integer id){
        AccountTypeDto accountTypeDto = accountTypeService.findAccountTypeById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type Have Been Found")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }
    @PostMapping
    public  BaseRest<?> createNewAccountType(@RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
        AccountTypeDto accountTypeDto = accountTypeService.createNewAccountTypeDto(createAccountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type Have Been Created Success")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }

    @PutMapping("/{id}")
    public BaseRest<?> updateAccTypeById(@PathVariable("id")Integer id,@RequestBody UpdateAccTypeDto updateAccountTypeDto){
        AccountTypeDto accountTypeDto = accountTypeService.updateById(id,updateAccountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type Have Been Update Success")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteAccTypeById(@PathVariable Integer id){
        Integer deleteAccTypeById = accountTypeService.deleteAccountTypeById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type Have Been Deleted Success")
                .timestamp(LocalDateTime.now())
                .data(deleteAccTypeById)
                .build();
    }
}
