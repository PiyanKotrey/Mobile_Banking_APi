package com.example.mobile_banking_api.api.account;

import com.example.mobile_banking_api.api.account.wep.AccountDto;
import com.example.mobile_banking_api.api.account.wep.CreateAccDto;
import com.example.mobile_banking_api.api.account.wep.UpdateAccountDto;
import com.example.mobile_banking_api.api.user.User;
import com.example.mobile_banking_api.api.user.wep.CreateUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapStruct {
    AccountDto accToDto(Account account);
    Account createAccDtoToAcc(CreateAccDto createAccDto);
    Account updateAccDtoToAcc(UpdateAccountDto updateAccountDto);
}
