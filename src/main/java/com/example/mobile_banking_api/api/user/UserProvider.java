package com.example.mobile_banking_api.api.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private static final String tableName="users";



    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO(tableName);
            VALUES("name","#{u.name}");
            VALUES("gender","#{u.gender}");
            VALUES("one_signal_id","#{u.oneSignalId}");
            VALUES("student_card_id","#{u.studentCardId}");
            VALUES("is_student","#{u.isStudent}");
            VALUES("is_deleted","FALSE");
        }}.toString();
    }
    public String buildSelectById(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("id = #{id}","is_deleted = FALSE");
        }}.toString();
    }

    public String buildSelectAll(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("is_deleted = FALSE");
            ORDER_BY("id DESC");
        }}.toString();
    }

    public String buildDeletedByIdSql(){
        return new SQL(){{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String BuildUpdateIsDeleteByIdSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("is_deleted = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildUpdateUserById(){
        return new SQL(){{
            UPDATE(tableName);
            SET("name = #{name}");
            SET("gender = #{gender}");
            SET("student_card_id = #{studentCardId}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildSelectByName(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("name ILIKE #{name}");

        }}.toString();
    }

    public String buildSelectByStudentCardId(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("student_card_id = #{studentCardId}");
        }}.toString();
    }
}
