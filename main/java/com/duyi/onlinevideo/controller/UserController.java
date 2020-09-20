package com.duyi.onlinevideo.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.exception.UserException;
import com.duyi.onlinevideo.service.UserService;
import com.duyi.onlinevideo.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 再次验证了邮箱、手机、密码、验证码是否符合规则,验证过后进行注册
     *
     * @param user 用户信息
     * @param session 存放会话数据
     * @param vcode 用户输入的验证码数据
     * @return 返回视图名
     */
    @RequestMapping("/register")
    public String register(User user, HttpSession session,String vcode) {

        String sVcode = (String) session.getAttribute("session_vcode");

        /* 后台再次判断验证码输入是否为空 */
        if (StrUtil.isEmpty(vcode) || !sVcode.equals(vcode)) {
            throw new UserException("验证码错误");
        }

        /* 将用户输入的数据进行正则匹配 */
        boolean emailMatch = ReUtil.isMatch("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", user.getEmail());
        boolean phoneMatch = ReUtil.isMatch("1[3456789]\\d{9}$", user.getMobile());
        boolean passwordMatch = ReUtil.isMatch("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$", user.getPassword());
        if (!phoneMatch || !emailMatch || !passwordMatch) {
            throw new UserException("注册信息错误");
        }

        /* 往数据库里插入该条记录 */
        int code = userService.register(user);
        if (code != 1) {
            throw new UserException("注册失败");
        }

        /* 注册成功直接登录 */
        session.setAttribute("login_user", user);

        /*  重定向到首页 */
        return "redirect:test";
    }

    /**
     * 验证了注册页面邮箱地址的正确性与可用性,用AJAX异步请求的方式,
     * 向后端发送checkEmail请求,后端拿着JSON对象返回给AJAX回调函数
     *
     * @param email 用户输入的邮箱地址
     * @return 返回json对象
     */
    @ResponseBody
    @RequestMapping("/checkEmail")
    public ResponseResult checkEmail(String email) {
        ResponseResult<String> result = new ResponseResult<>(-1,"email exist");
        User user = userService.isExistEmail(email);
        boolean emailMatch = ReUtil.isMatch("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#" +
                "$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", email);
        /* 数据库没有该条记录,说明该邮箱可用 */
        if (user == null && emailMatch) {
            result.setRcode(1);
            result.setMessage("ok");
            return result;

        }
        return result;
    }

}
