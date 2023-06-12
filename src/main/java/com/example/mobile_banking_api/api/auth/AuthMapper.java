package com.example.mobile_banking_api.api.auth;

import com.example.mobile_banking_api.api.user.Authority;
import com.example.mobile_banking_api.api.user.Role;
import com.example.mobile_banking_api.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {
    @InsertProvider(type = AuthProvider.class,method = "buildRegisterSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean register(@Param("u")User user);

    @InsertProvider(type = AuthProvider.class,method = "buildCreateUserRoleSql")
    void createUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE")
    @Results(id = "authResultMap", value = {
            @Result(column = "id",property = "id"),
            @Result(column = "is_deleted",property = "isDeleted"),
            @Result(column = "is_verified",property = "isVerified"),
            @Result(column = "verified_code",property = "verifiedCode"),
            @Result(column = "id",property = "roles",many = @Many(select = "loadUserRole"))
    })
    Optional<User> selectByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE email = #{email} AND is_verified = TRUE")
    @ResultMap(value = "authResultMap")
    Optional<User> loadUserByUsername(@Param("email") String email);

    @SelectProvider(type = AuthProvider.class,method = "buildLoadUserRolesSql")
    @Result(column = "id",property = "authorities",
    many = @Many(select = "loadUserAuthorities"))
    List<Role> loadUserRole(Integer userId);

    @SelectProvider(type = AuthProvider.class,method = "buildLoadUserAuthorities")
    List<Authority> loadUserAuthorities(Integer roleId);

    @Select("SELECT * FROM users WHERE email = #{email} AND verified_code = #{verifiedCode} ")
    @ResultMap(value = "authResultMap")
    Optional<User> selectByEmailAndVerifiedCode(@Param("email")String email,@Param("verifiedCode")String verifiedCode);

   @UpdateProvider(type = AuthProvider.class,method = "buildVerifiedSql")
    void verify(@Param("email")String email,@Param("verifiedCode")String verifiedCode);

    @UpdateProvider(type = AuthProvider.class,method = "buildUpdateVerifiedCodeSQL")
   void updateVerifiedCode(@Param("email")String email,@Param("verifiedCode")String verifiedCode);

}
