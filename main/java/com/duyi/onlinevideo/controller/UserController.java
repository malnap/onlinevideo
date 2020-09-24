package com.duyi.onlinevideo.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.duyi.onlinevideo.dto.LoginToken;
import com.duyi.onlinevideo.entity.User;
import com.duyi.onlinevideo.exception.UserException;
import com.duyi.onlinevideo.service.UserService;
import com.duyi.onlinevideo.dto.ResponseResult;
import com.duyi.onlinevideo.utils.Constants;
import com.duyi.onlinevideo.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 在表单提交时会触发ajax发送该请求,
     * 用来判断用户名与密码是否匹配,
     * 若不正确,告诉错误信息给用户.
     */
    @ResponseBody
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public ResponseResult checkLogin(User user) {
        ResponseResult responseResult = new ResponseResult(-1, "login error");

        if (StrUtil.isEmpty(user.getEmail()) || StrUtil.isEmpty(user.getPassword())) {
            return responseResult;
        }

        User dbUser = userService.login(user);
        if (dbUser == null) {
            return responseResult;
        }
        responseResult.setRcode(1);
        responseResult.setMessage("ok");
        return responseResult;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, String autoLogin, HttpServletRequest request,
                        HttpSession session, HttpServletResponse response) {

        ServletContext application = request.getServletContext();

        if (StrUtil.isEmpty(user.getEmail()) || StrUtil.isEmpty(user.getPassword())) {
            throw new UserException("参数错误");
        }

        User dbUser = userService.login(user);
        if (dbUser != null) {
            /* dbUser存在,登陆成功存入session  */
            session.setAttribute("login_user", user);
        }

        /* 自动登陆的逻辑 */
        if (StrUtil.isEmpty(autoLogin) || "1".equals(autoLogin)) {
            /* 服务器端保存token对应的loginToken数据,保存在application */
            LoginToken loginToken = VideoUtil.generateLoginToken(request, user);
            Cookie cookie = new Cookie("autoToken", loginToken.generateToken());
            /* 设置cookie保存属性 */
            cookie.setPath("/");
            /* 设置cookie的存活时间48h,单位为s */
            cookie.setMaxAge(60 * 60 * 24 * 2);
            /* 服务端将生成的凭证cookie返回给客户端cookie */
            response.addCookie(cookie);

            /* 服务器保存对应的LoginToken用户登录数据 */
            @SuppressWarnings("unchecked")
            HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>)application.getAttribute(Constants.AUTO_LOGIN_TOKEN);
            if (tokenMap == null) {
                /* 初始化tokenMap */
                tokenMap = new HashMap<>();
                tokenMap.put(loginToken.generateToken(), loginToken);
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
        String tokenValue = VideoUtil.getCookieTokenValue(request);
        if (!StrUtil.isEmpty(tokenValue)) {
            ServletContext application = request.getServletContext();
            @SuppressWarnings("unchecked")
            HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>) application.getAttribute(Constants.AUTO_LOGIN_TOKEN);
            /* 删除服务器中token对应的user数据 */
            tokenMap.remove(tokenValue);
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
