package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.UserDao;
import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int register(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public User isExistEmail(String email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        return userDao.findUserByCondition(map);
    }
}
