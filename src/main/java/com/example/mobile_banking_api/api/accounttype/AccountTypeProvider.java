package com.example.mobile_banking_api.api.accounttype;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
        }}.toString();
    }
}
