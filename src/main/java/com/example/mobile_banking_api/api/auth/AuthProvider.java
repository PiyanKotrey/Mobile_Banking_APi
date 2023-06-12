package com.example.mobile_banking_api.api.auth;

import org.apache.ibatis.jdbc.SQL;

import java.awt.*;

public class AuthProvider {
    public String buildRegisterSql(){
        return new SQL(){{
            INSERT_INTO("users");
            VALUES("email","#{u.email}");
            VALUES("password","#{u.password}");
            VALUES("is_verified","#{u.isVerified}");
            VALUES("is_deleted","FALSE");
        }}.toString();

    }

    public String buildVerifiedSql(){
        return new SQL(){{
            UPDATE("users");
            SET("is_verified = TRUE");
            SET("verified_code = NULL");
            WHERE("email = #{email}","verified_code = #{verifiedCode}");
        }}.toString();
    }
    public String buildUpdateVerifiedCodeSQL(){
        return new SQL(){{
            UPDATE("users");
            SET("verified_code = #{verifiedCode}");
            WHERE("email = #{email}");
        }}.toString();
    }

    public String buildCreateUserRoleSql(){
        return new SQL(){{
            INSERT_INTO("users_roles");
            VALUES("user_id","#{userId}");
            VALUES("role_id","#{roleId}");
        }}.toString();
    }

    public String buildLoadUserRolesSql(){
        return new SQL(){{
            SELECT("r.id,r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles as ur ON ur.role_id=r.id");
            WHERE("ur.user_id = #{userId}");
        }}.toString();
    }

    public String buildLoadUserAuthorities(){
        return new SQL(){{
            SELECT("a.id,a.name");
            FROM("authorities AS a");
            INNER_JOIN("roles_authorities AS ra ON ra.authority_id = a.id");
            WHERE("ra.role_id = #{roleId}");
        }}.toString();
    }

}
