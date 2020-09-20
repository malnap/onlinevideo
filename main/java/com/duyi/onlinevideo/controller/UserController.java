package com.duyi.onlinevideo.controller;

import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/regist")
    public String regist(User user) {
        // 表单数据验证
        int code = userService.regist(user);
        return "test";
    }
}
