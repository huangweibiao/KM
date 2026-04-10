package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表: user
 * 用于存储系统用户信息
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * 用户ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名，唯一标识
     */
    @Column(name = "username", nullable = false, unique = true, length = 64)
    private String username;

    /**
     * 邮箱，唯一标识
     */
    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    /**
     * 昵称
     */
    @Column(name = "nickname", length = 64)
    private String nickname;

    /**
     * 头像URL
     */
    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    /**
     * 密码（加密存储）
     */
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    /**
     * 状态: 0禁用, 1正常
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 部门ID
     */
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 默认构造函数
     */
    public User() {
    }

    /**
     * 在实体持久化前设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 在实体更新前设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取部门ID（兼容getDeptId调用）
     */
    public Long getDeptId() {
        return departmentId;
    }

    /**
     * 设置部门ID
     */
    public void setDeptId(Long deptId) {
        this.departmentId = deptId;
    }

    /**
     * 获取最后登录时间
     */
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * 设置最后登录时间
     */
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * 获取密码哈希（兼容方法）
     */
    public String getPasswordHash() {
        return password;
    }
}
