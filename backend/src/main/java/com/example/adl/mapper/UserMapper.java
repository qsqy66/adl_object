package com.example.adl.mapper;

import com.example.adl.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    // 查询所有用户。
    @Select("SELECT user_id, user_name, user_account, user_phone " +
            "FROM `user` ORDER BY user_id")
    @Results(id = "userResultMap", value = {
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userAccount", column = "user_account"),
            @Result(property = "userPhone", column = "user_phone")
    })
    List<User> findAll();

    // 根据主键查询用户。
    @Select("SELECT user_id, user_name, user_account, user_phone " +
            "FROM `user` WHERE user_id = #{userId}")
    @Results(id = "userByIdResultMap", value = {
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userAccount", column = "user_account"),
            @Result(property = "userPhone", column = "user_phone")
    })
    User findById(Integer userId);

    // 根据登录账号或用户姓名和密码查询用户，用于登录。
    @Select("SELECT user_id, user_name, user_account, user_pwd, user_phone " +
            "FROM `user` WHERE (user_account = #{username} OR user_name = #{username}) " +
            "AND user_pwd = #{password}")
    @Results(id = "loginUserResultMap", value = {
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userAccount", column = "user_account"),
            @Result(property = "userPwd", column = "user_pwd"),
            @Result(property = "userPhone", column = "user_phone")
    })
    User findByCredentials(@Param("username") String username,
                           @Param("password") String password);

    // 新增用户，数据库生成的主键会回写到 user.userId。
    @Insert("INSERT INTO `user` " +
            "(user_name, user_account, user_pwd, user_phone) " +
            "VALUES (#{userName}, #{userAccount}, #{userPwd}, #{userPhone})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insert(User user);

    // 修改用户的非主键字段。
    @Update("UPDATE `user` SET user_name = #{userName}, " +
            "user_account = #{userAccount}, user_pwd = #{userPwd}, " +
            "user_phone = #{userPhone} WHERE user_id = #{userId}")
    int update(User user);

    // 根据主键删除用户。
    @Delete("DELETE FROM `user` WHERE user_id = #{userId}")
    int deleteById(@Param("userId") Integer userId);
}