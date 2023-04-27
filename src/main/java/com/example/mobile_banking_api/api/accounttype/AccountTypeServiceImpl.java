package com.example.mobile_banking_api.api.accounttype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;



    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes = accountTypeMapper.select();
//        List<AccountTypeDto> accountTypeDtoList = accountTypes.stream()
//                .map(accountType -> new AccountTypeDto(accountType.getName())).toList();
        System.out.println(accountTypes.get(0).getName());
        return accountTypeMapStruct.toDtoList(accountTypes);
    }
}
