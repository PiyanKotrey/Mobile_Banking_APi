package com.example.mobile_banking_api.api.account;

import com.example.mobile_banking_api.api.account.wep.AccountDto;
import com.example.mobile_banking_api.api.account.wep.CreateAccDto;
import com.example.mobile_banking_api.api.account.wep.UpdateAccountDto;
import com.example.mobile_banking_api.api.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final AccountMapper accountMapper;
    private final AccountMapStruct accountMapStruct;
    @Override
    public AccountDto findAccountById(Integer id) {
        Account account = accountMapper.selectAccountById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Account with %d is not found",id)));
        return accountMapStruct.accToDto(account);
    }

    @Override
    public AccountDto createNewAccount(CreateAccDto createAccDto) {
        Account account = accountMapStruct.createAccDtoToAcc(createAccDto);
            accountMapper.insertAcc(account);
            log.info("Account = {}",account.getId());
            return this.findAccountById(account.getId());
    }

    @Override
    public Integer deleteAccById(Integer id) {
        boolean isExisted = accountMapper.exitsById(id);
        if (isExisted){
            accountMapper.deletedAccById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Account with %d is not found",id));
    }

    @Override
    public AccountDto updateAccById(Integer id,UpdateAccountDto updateAccountDto) {
        if (accountMapper.exitsById(id)){
            Account account = accountMapStruct.updateAccDtoToAcc(updateAccountDto);
            account.setId(id);
            accountMapper.updateAccById(account);
            return this.findAccountById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Account with %d is not found",id));
    }
}
