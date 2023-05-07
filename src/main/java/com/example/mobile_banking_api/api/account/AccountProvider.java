package com.example.mobile_banking_api.api.account;

import org.apache.ibatis.jdbc.SQL;

import java.util.Set;

public class AccountProvider {
    public String buildSelectAccByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM("accounts");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildInsertAccSql(){
        return new SQL(){{
            INSERT_INTO("accounts");
            VALUES("account_no","#{acc.accountNo}");
            VALUES("account_name","#{acc.accountName}");
            VALUES("profile","#{acc.profile}");
            VALUES("pin","#{acc.pin}");
            VALUES("password","#{acc.password}");
            VALUES("phone_number","#{acc.phoneNumber}");
            VALUES("transfer_limit","#{acc.transferLimit}");
            VALUES("account_type","#{acc.accountType}");
        }}.toString();
    }

    public String buildDeleteAccByIdSql(){
        return new SQL(){{
            DELETE_FROM("accounts");
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildUpdateAccById(){
        return new SQL(){{
            UPDATE("accounts");
            SET("profile = #{acc.profile}");
            SET("pin = #{acc.pin}");
            SET("password = #{acc.password}");
            SET("phone_number = #{acc.phoneNumber}");
            SET("transfer_limit = #{acc.transferLimit}");
            SET("account_type = #{acc.accountType}");
            WHERE("id = #{acc.id}");
        }}.toString();
    }
}
