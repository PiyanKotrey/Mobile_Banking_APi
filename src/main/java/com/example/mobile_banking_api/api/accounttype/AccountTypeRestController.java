package com.example.mobile_banking_api.api.accounttype;

import com.example.mobile_banking_api.base.BaseRest;
import com.example.mobile_banking_api.base.BaseRest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
