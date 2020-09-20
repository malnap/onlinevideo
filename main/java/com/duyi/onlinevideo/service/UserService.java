package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.User;

public interface UserService {

    int register(User user);

    User login(User user);

    User isExistEmail(String email);
}
