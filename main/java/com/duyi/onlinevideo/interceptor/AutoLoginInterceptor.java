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

    /**
     * 在请求进入Controller层方法执行前执行这个方法;
     * 判断token是否有效(时间 + 用户 + IP + 浏览器信息);时间是否超时.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /* 获取浏览器发送请求携带的所有cookie */
        Cookie[] cookies = request.getCookies();
        /* 获取全局application数据 */
        ServletContext application = request.getServletContext();
        /* 获取session数据 */
        HttpSession session = request.getSession(true);
        /* 判断token是否有效 */
        boolean result = AutoLoginUtil.checkLoginToken(cookies, application);
        if (result) {
            /* token有效,直接session放入user,恢复用户的登录状态;如果失效什么都不做*/
            session.setAttribute(Constants.LOGIN_USER, AutoLoginUtil.getUserByApplication(cookies, application));
        }
        /* 始终都需要放行,只有保不保存session的考量 */
        return true;
    }
}
