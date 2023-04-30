package com.example.mobile_banking_api.api.accounttype;

import com.example.mobile_banking_api.api.user.User;
import com.example.mobile_banking_api.api.user.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountTypeMapper {
    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectSql")
    List<AccountType> select();

    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectByIdSql")
    Optional<AccountType> selectAccTypeById(@Param("id") Integer id);

    @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void Insert(@Param("a")AccountType accountType);

    @UpdateProvider(type = AccountTypeProvider.class,method = "buildUpdateById")
    void updateById(@Param("a") AccountType accountType);

    @Select("SELECT EXISTS(SELECT * FROM account_types WHERE id = #{id})")
    boolean existById(@Param("id")Integer id);

    @DeleteProvider(type = AccountTypeProvider.class,method = "buildDeleteById")
    void deleteById(@Param("id")Integer id);
}
