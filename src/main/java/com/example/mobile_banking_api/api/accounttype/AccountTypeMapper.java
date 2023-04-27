package com.example.mobile_banking_api.api.accounttype;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface AccountTypeMapper {
    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectSql")
    List<AccountType> select();
}
