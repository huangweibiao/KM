package com.km.wiki.dto;

/**
 * 登录请求DTO
 * 用于接收用户登录请求参数
 */
public class LoginRequest {

    /**
     * 用户名或邮箱
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 默认构造函数
     */
    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
