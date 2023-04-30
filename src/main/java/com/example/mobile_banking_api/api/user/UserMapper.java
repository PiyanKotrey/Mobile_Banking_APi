package com.example.mobile_banking_api.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    @InsertProvider(type = UserProvider.class,method = "buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("u") User user);
    @SelectProvider(type = UserProvider.class,method = "buildSelectById")
    @Results(id ="userResultMap",value= {
            @Result(column = "student_card_id", property = "studentCardId"),
            @Result(column = "is_student", property = "isStudent"),
            @Result(column = "is_deleted", property = "isDeleted")

    })
    Optional<User> selectById(@Param("id") Integer id);
    @SelectProvider(type = UserProvider.class,method = "buildSelectByName")
    @ResultMap("userResultMap")
    Optional<User> selectByName(@Param("name")String name);

    @SelectProvider(type = UserProvider.class,method = "buildSelectByStudentCardId")
    @ResultMap("userResultMap")
    Optional<User> selectByStudentCardId(@Param("studentCardId") String studentCardId);

    @SelectProvider(type = UserProvider.class,method = "buildSelectAll")
    @ResultMap("userResultMap")
    List<User> select();
    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    boolean existById(@Param("id")Integer id);
    @DeleteProvider(type = UserProvider.class,method = "buildDeletedByIdSql")
    void deletedById(@Param("id") Integer id);

    @UpdateProvider(type = UserProvider.class,method = "BuildUpdateIsDeleteByIdSql")
    void updatedIsDeletedById(@Param("id")Integer id,@Param("status") boolean status);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateUserById")
    void updateUserById(@Param("id")Integer id,
                        @Param("name")String name,
                        @Param("gender")String gender,
                        @Param("studentCardId")String studentCardId);


}
