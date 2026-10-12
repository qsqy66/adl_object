package com.example.adl.service.Impl;

import com.example.adl.bean.User;
import com.example.adl.mapper.UserMapper;
import com.example.adl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    // 校验用户名和密码是否匹配。
    @Override
    public User login(String username, String password) {
        return mapper.findByCredentials(username, password);
    }

    @Override
    public List<User> findAll() {
        return mapper.findAll();
    }

    @Override
    public User findById(Integer userId) {
        return mapper.findById(userId);
    }

    @Override
    public User add(User user) {
        mapper.insert(user);
        return user;
    }

    @Override
    public boolean update(Integer userId, User user) {
        user.setUserId(userId);
        return mapper.update(user) > 0;
    }

    @Override
    public boolean deleteById(Integer userId) {
        return mapper.deleteById(userId) > 0;
    }
}
