package com.example.mobile_banking_api.api.accounttype;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
        }}.toString();
    }

    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO("account_types");
            VALUES("name","#{a.name}");
        }}.toString();
    }

    public String buildUpdateById(){
        return new SQL(){{
            UPDATE("account_types");
            SET("name = #{a.name}");
            WHERE("id = #{a.id}");
        }}.toString();
    }

    public String buildDeleteById(){
        return new SQL(){{
            DELETE_FROM("account_types");
            WHERE("id = #{id}");
        }}.toString();
    }
}
