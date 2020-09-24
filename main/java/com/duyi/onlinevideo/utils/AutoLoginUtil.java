package com.duyi.onlinevideo.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.duyi.onlinevideo.dto.LoginToken;
import com.duyi.onlinevideo.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 自动登录工具类
 */
public class AutoLoginUtil {

    /**
     * 获取IP地址
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;
        /* X-Forwarded-For：Squid 服务代理 */
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            /* Proxy-Client-IP：apache 服务代理 */
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            /* WL-Proxy-Client-IP：weblogic 服务代理 */
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            /* HTTP_CLIENT_IP：有些代理服务器 */
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            /* X-Real-IP：nginx服务代理 */
            ipAddresses = request.getHeader("X-Real-IP");
        }

        /* 有些网络通过多层代理,那么获取到的ip就会有多个,一般都是通过逗号（,）分割开来,并且第一个ip为客户端的真实IP */
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        /* 还是不能获取到,最后再通过request.getRemoteAddr();获取 */
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 生成服务器保存的用户token对象
     *
     * @param request 用于获取浏览器信息
     * @param user 用于获取用户信息
     * @return 返回LoginToken对象
     */
    public static LoginToken generateLoginToken(HttpServletRequest request, User user) {
        /* 时间 + 用户(email) + IP + 浏览器信息 = （MD5）*/
        LoginToken loginToken = new LoginToken();
        loginToken.setNow(DateUtil.now());
        loginToken.setUserAgent(request.getHeader("User-Agent"));
        loginToken.setUser(user);
        loginToken.setIp(getIPAddress(request));
        return loginToken;
    }

    /**
     * 通过全局application获取用户信息
     */
    public static User getUserByApplication(Cookie[] cookies, ServletContext application) {
        /* 拿到存储在浏览器端的名为autoToken的cookie值 */
        String cookieToken = getCookieOfAutoToken(cookies);

        /* 获取服务器中的所有用户登录的token数据 */
        @SuppressWarnings("unchecked")
        HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>) application.getAttribute(Constants.AUTO_LOGIN_TOKEN);

        /* 根据客户端的token,获取服务器中的用户数据 */
        LoginToken token = tokenMap.get(cookieToken);
        return token.getUser();
    }

    /**
     * 校验用户的token是否有效,token存在且没有失效
     */
    public static boolean checkLoginToken(Cookie[] cookies, ServletContext application) {

        /* 需要重新登陆,cookie没有数据 */
        if (ObjectUtil.isEmpty(cookies) || cookies.length == 0) {
            return false;
        }

        /* 拿到存储在浏览器端的名为autoToken的cookie值 */
        String cookieToken = getCookieOfAutoToken(cookies);

        /* 需要重新登陆,没有服务器返回对应的登录凭证 */
        if (StrUtil.isEmpty(cookieToken)) {
            return false;
        }

        /* 获取服务器中的所有用户登录的token数据 */
        @SuppressWarnings("unchecked")
        HashMap<String, LoginToken> tokenMap = (HashMap<String, LoginToken>) application.getAttribute(Constants.AUTO_LOGIN_TOKEN);

        /* 服务器还未初始化HashMap */
        if (ObjectUtil.isEmpty(tokenMap)) {
            return false;
        }

        /* 根据客户端的token,获取服务器中的用户数据 */
        LoginToken token = tokenMap.get(cookieToken);

        /* 服务器中没有对应的用户数据 */
        if (ObjectUtil.isEmpty(token)) {
            return false;
        }

        /* 生成token(只要数据不变,token值就唯一) */
        String serverToken = token.generateToken();
        /* 用户上一次登录的时间 */
        String tokenTime = token.getNow();
        DateTime tokenDateTime = DateUtil.parse(tokenTime);

        /* 判断token是否超时,48小时 60 * 60 * 48 * 1000 = 172800000 */
        /* 当前毫秒数 - 上一次登录时间毫秒数 > 172800000 */
        if (System.currentTimeMillis() - tokenDateTime.getTime() >= 172800000) {
            return false;
        }

        /* 验证通过可以自动登录;客户端token凭证失效,需要重新登陆 */
        return cookieToken.equals(serverToken);
    }

    /**
     * 拿到存储在浏览器端的名为autoToken的cookie值
     */
    public static String getCookieOfAutoToken(Cookie[] cookies) {
        if (ObjectUtil.isEmpty(cookies) || cookies.length == 0) {
            return "";
        }

        for (Cookie cookie : cookies) {
            if ("autoToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
