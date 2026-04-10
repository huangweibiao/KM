package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 审计日志实体类
 * 对应数据库表: audit_log
 * 用于记录系统操作日志
 */
@Entity
@Table(name = "audit_log")
public class AuditLog {

    /**
     * 日志ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名快照
     */
    @Column(name = "username", length = 64)
    private String username;

    /**
     * 操作类型: CREATE, UPDATE, DELETE, LOGIN, LOGOUT等
     */
    @Column(name = "action", nullable = false, length = 64)
    private String action;

    /**
     * 资源类型: PAGE, WIKI, COMMENT, USER等
     */
    @Column(name = "resource_type", nullable = false, length = 32)
    private String resourceType;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 旧值(JSON格式)
     */
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    /**
     * 新值(JSON格式)
     */
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    /**
     * IP地址
     */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /**
     * User Agent
     */
    @Column(name = "user_agent", length = 512)
    private String userAgent;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public AuditLog() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
