package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 权限实体类
 * 对应数据库表: permission
 * 用于存储系统权限信息，支持RBAC权限模型
 */
@Entity
@Table(name = "permission")
public class Permission {

    /**
     * 权限ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限代码，唯一标识，如: wiki:create, page:edit
     */
    @Column(name = "code", nullable = false, unique = true, length = 128)
    private String code;

    /**
     * 权限名称
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * 资源类型: WIKI, PAGE, COMMENT等
     */
    @Column(name = "resource_type", length = 32)
    private String resourceType;

    /**
     * 操作类型: CREATE, READ, UPDATE, DELETE
     */
    @Column(name = "action", length = 32)
    private String action;

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
    public Permission() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
}
