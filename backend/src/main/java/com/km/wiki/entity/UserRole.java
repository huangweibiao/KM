package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 * 对应数据库表: user_role
 * 用于建立用户和角色的多对多关联关系
 */
@Entity
@Table(name = "user_role")
public class UserRole {

    /**
     * 关联ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID，外键关联user表
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色ID，外键关联role表
     */
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public UserRole() {
    }

    /**
     * 在实体持久化前设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
