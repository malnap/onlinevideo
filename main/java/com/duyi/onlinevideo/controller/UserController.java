package com.duyi.onlinevideo.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.duyi.onlinevideo.dto.LoginToken;
import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.exception.UserException;
import com.duyi.onlinevideo.service.UserService;
import com.duyi.onlinevideo.dto.ResponseResult;
import com.duyi.onlinevideo.utils.Constants;
import com.duyi.onlinevideo.utils.AutoLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 点登录按钮以后,先发ajax校验,校验用户名或密码是否正确,对用户进行提示;
     * 再次进行form表单验证.无论ajax验证成功还是失败,都会再次提交form表单.
     * @param user 前台传来的数据,框架自动帮我们组成user对象
     * @return 返回JSON对象
     */
    @ResponseBody
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public ResponseResult checkLogin(User user) {
        /* 默认是失败状态 */
        ResponseResult responseResult = new ResponseResult(-1, "login error");

        /* 如果表单提交过来的邮箱或密码为空,直接返回错误码 */
        if (StrUtil.isEmpty(user.getEmail()) || StrUtil.isEmpty(user.getPassword())) {
            return responseResult;
        }

        /* 在DB中查询并验证该条记录 */
        User dbUser = userService.login(user);
        /* 该记录不存在 */
        if (dbUser == null) {
            return responseResult;
        }
        /* 记录存在,修改返回码 */
        responseResult.setRcode(1);
        responseResult.setMessage("ok");
        return responseResult;
    }

    /**
     *
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, @RequestParam("autoLogin") String autoLogin, HttpServletRequest request,
                        HttpSession session, HttpServletResponse response) {

        /* 创建全局作用域,存放token数据 */
        ServletContext application = request.getServletContext();

        /* 判断用户输入数据的有效性 */
        if (StrUtil.isEmpty(user.getEmail()) || StrUtil.isEmpty(user.getPassword())) {
            throw new UserException("参数错误");
        }

        /* 在DB中查询并验证该条记录 */
        User dbUser = userService.login(user);
        /* dbUser存在,存入session */
        if (dbUser != null) {
            session.setAttribute("login_user", user);
        }

        /* 自动登陆的逻辑 */
        if (StrUtil.isEmpty(autoLogin) || "1".equals(autoLogin)) {
            /* 服务器端保存token对应的loginToken数据,保存在application */
            LoginToken loginToken = AutoLoginUtil.generateLoginToken(request, user);
            /* 拿着封装好数据的loginToken去生成md5加密过的令牌cookie */
            Cookie cookie = new Cookie("autoToken", loginToken.generateToken());
            /* 设置cookie保存属性 */
            cookie.setPath("/");
            /* 设置cookie的存活时间48h,单位为s */
            cookie.setMaxAge(60 * 60 * 24 * 2);
            /* 服务端将生成的凭证cookie返回给客户端cookie */
            response.addCookie(cookie);

            /* 服务器保存对应的LoginToken用户登录数据到tokenMap */
            @SuppressWarnings("unchecked")
            HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>)application.getAttribute(Constants.AUTO_LOGIN_TOKEN);
            if (tokenMap == null) {
                /* 初始化tokenMap */
                tokenMap = new HashMap<>();
                /* key=md5加密过后的令牌,value为封装了用户数据 + 浏览器信息 + ip的loginToken对象 */
                tokenMap.put(loginToken.generateToken(), loginToken);
                /* 将tokenMap设置到全局作用域 */
                application.setAttribute(Constants.AUTO_LOGIN_TOKEN, tokenMap);
            } else {
                /* 已经初始化,直接保存loginToken */
                tokenMap.put(loginToken.generateToken(), loginToken);
            }
        }

        /* 重定向到首页 */
        return "redirect:/";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        /* 1.清空用户session */
        session.removeAttribute("login_user");

        /* 2.清空application中用户的登录数据 */
        String autoToken = AutoLoginUtil.getCookieOfAutoToken(request.getCookies());
        if (!StrUtil.isEmpty(autoToken)) {
            ServletContext application = request.getServletContext();
            @SuppressWarnings("unchecked")
            HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>) application.getAttribute(Constants.AUTO_LOGIN_TOKEN);
            /* 删除服务器中tokenMap对应的user数据 */
            tokenMap.remove(autoToken);
        }

        /* 3.设置cookie失效 */
        Cookie cookie = new Cookie("autoToken", "invalid");
        /*  设置cookie保存属性 */
        cookie.setPath("/");
        cookie.setMaxAge(1);
        response.addCookie(cookie);
        return "redirect:/";
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
        return "redirect:index";
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
