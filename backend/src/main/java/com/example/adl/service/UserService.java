package com.example.adl.service;

import com.example.adl.bean.User;

import java.util.List;

public interface UserService {
    // 用户登录，成功时返回用户信息，失败时返回 null。
    User login(String username, String password);

    // 查询所有用户。
    List<User> findAll();

    // 根据主键查询用户。
    User findById(Integer userId);

    // 新增用户。
    User add(User user);

    // 修改用户。
    boolean update(Integer userId, User user);

    // 删除用户。
    boolean deleteById(Integer userId);
}
