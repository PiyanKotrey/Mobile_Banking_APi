package com.example.mobile_banking_api.api.account;

import com.example.mobile_banking_api.api.user.User;
import com.example.mobile_banking_api.api.user.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface AccountMapper {
    @SelectProvider(type = AccountProvider.class,method = "buildSelectAccByIdSql")
    @Results(id ="AccResultMap",value= {
            @Result(column = "account_no", property = "accountNo"),
            @Result(column = "account_name", property = "accountName"),
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "transfer_limit", property = "transferLimit"),
            @Result(column = "account_type", property = "accountType")
            })
    Optional < Account > selectAccountById(@Param("id") Integer id);

    @InsertProvider(type = AccountProvider.class,method = "buildInsertAccSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insertAcc(@Param("acc") Account account);

    @Select("SELECT EXISTS(SELECT * FROM accounts WHERE id = #{id})")
    boolean exitsById(@Param("id")Integer id);

    @DeleteProvider(type = AccountProvider.class,method = "buildDeleteAccByIdSql")
    void deletedAccById(@Param("id")Integer id);

    @UpdateProvider(type = AccountProvider.class,method = "buildUpdateAccById")
    void updateAccById(@Param("acc")Account account);
}
