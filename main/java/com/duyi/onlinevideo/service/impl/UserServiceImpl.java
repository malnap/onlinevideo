package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.UserDao;
import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int regist(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User login(User user) {
        return null;
    }
}
