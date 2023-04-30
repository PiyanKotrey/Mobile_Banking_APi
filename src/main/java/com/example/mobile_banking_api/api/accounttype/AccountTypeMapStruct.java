package com.example.mobile_banking_api.api.accounttype;

import com.example.mobile_banking_api.api.accounttype.wep.AccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.CreateAccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.UpdateAccTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapStruct {
    List<AccountTypeDto> toDtoList(List<AccountType> model);
    AccountTypeDto toDto(AccountType model);
    AccountType createAccountTypeDtoToAccountType(CreateAccountTypeDto createAccountTypeDto);
    AccountType updateAccountTypeDtoToAccountType(UpdateAccTypeDto updateAccTypeDto);

}
