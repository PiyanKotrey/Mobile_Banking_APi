package com.example.mobile_banking_api.api.accounttype;

import com.example.mobile_banking_api.api.accounttype.wep.AccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.CreateAccountTypeDto;
import com.example.mobile_banking_api.api.accounttype.wep.UpdateAccTypeDto;
import com.example.mobile_banking_api.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
//        System.out.println(accountTypes.get(0).getName());
        return accountTypeMapStruct.toDtoList(accountTypes);
    }

    @Override
    public AccountTypeDto findAccountTypeById(Integer id) {
        AccountType model = accountTypeMapper.selectAccTypeById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with %d is not found",id)));
        return accountTypeMapStruct.toDto(model);
    }

    @Override
    public AccountTypeDto createNewAccountTypeDto(CreateAccountTypeDto createAccountTypeDto) {
        AccountType model = accountTypeMapStruct.createAccountTypeDtoToAccountType(createAccountTypeDto);
        accountTypeMapper.Insert(model);
        return this.findAccountTypeById(model.getId());
    }

    @Override
    public AccountTypeDto updateById(Integer id, UpdateAccTypeDto updateAccTypeDto) {
        if (accountTypeMapper.existById(id)){
            AccountType model = accountTypeMapStruct.updateAccountTypeDtoToAccountType(updateAccTypeDto);
            model.setId(id);
            accountTypeMapper.updateById(model);
            return this.findAccountTypeById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AccountType with %d is not found",id));

//        AccountType model = accountTypeMapper.selectAccTypeById(id).orElseThrow(()->
//                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with %d is not found",id)));
//        model.setId(id);
//        model = accountTypeMapStruct.updateAccountTypeDtoToAccountType(updateAccTypeDto);
//        accountTypeMapper.updateById(model);
//        return this.findAccountTypeById(id);
    }

    @Override
    public Integer deleteAccountTypeById(Integer id) {
        boolean isExisted = accountTypeMapper.existById(id);
        if (isExisted){
            accountTypeMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with %d is not found",id));
    }
}
