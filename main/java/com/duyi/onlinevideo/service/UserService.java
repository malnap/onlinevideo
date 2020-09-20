package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.User;

public interface UserService {

    int regist(User user);

    User login(User user);
}
