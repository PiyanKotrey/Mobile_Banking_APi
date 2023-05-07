package com.example.mobile_banking_api.api.account;

import com.example.mobile_banking_api.api.account.wep.AccountDto;
import com.example.mobile_banking_api.api.account.wep.CreateAccDto;
import com.example.mobile_banking_api.api.account.wep.UpdateAccountDto;

public interface AccountService {
    AccountDto findAccountById(Integer id);
    AccountDto createNewAccount(CreateAccDto createAccDto);
    Integer deleteAccById(Integer id);
    AccountDto updateAccById(Integer id, UpdateAccountDto updateAccountDto);
}
