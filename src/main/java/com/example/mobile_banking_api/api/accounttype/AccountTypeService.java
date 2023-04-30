package com.example.mobile_banking_api.api.accounttype;

import com.example.mobile_banking_api.api.accounttype.wep.AccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.CreateAccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.UpdateAccTypeDto;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();
    AccountTypeDto findAccountTypeById(Integer id);
    AccountTypeDto createNewAccountTypeDto (CreateAccountTypeDto createAccountTypeDto);
    AccountTypeDto updateById (Integer id, UpdateAccTypeDto updateAccTypeDto);
    Integer deleteAccountTypeById(Integer id);
}
