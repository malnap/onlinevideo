package com.duyi.onlinevideo.dto;

import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.onlinevideo.entity.User;


/**
 * 封装了 用户 + ip + user-agent浏览器信息 + 登陆时间 作为自动登陆的token
 */
public class LoginToken {

    private String ip;
    private String now;
    private String userAgent;
    private User user;

    /**
     * 生成自动登录的凭证
     */
    public String generateToken() {
        /* 时间 + 用户(email) + IP + 浏览器信息 = （MD5）*/
        StringBuilder sb = new StringBuilder();
        sb.append(ip);
        sb.append(now);
        sb.append(userAgent);
        sb.append(user.getEmail());
        /* MD5加盐,增加MD5生成的可靠性 */
        sb.append("12*2&&#!@#w");
        return DigestUtil.md5Hex(sb.toString());
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
