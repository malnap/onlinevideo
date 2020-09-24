package com.duyi.onlinevideo.interceptor;

import com.duyi.onlinevideo.utils.Constants;
import com.duyi.onlinevideo.utils.AutoLoginUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自动登陆拦截器
 */
public class AutoLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /* 拿到浏览器发送请求携带的所有cookie */
        Cookie[] cookies = request.getCookies();
        ServletContext application = request.getServletContext();
        HttpSession session = request.getSession(true);
        boolean result = AutoLoginUtil.checkLoginToken(cookies, application);
        if (result) {
            /* 说明token有效,恢复用户的登录状态 */
            session.setAttribute(Constants.LOGIN_USER, AutoLoginUtil.getUserByApplication(cookies, application));
        }
        return true;
    }
}
