package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 知识库实体类
 * 对应数据库表: wiki
 * 用于存储知识库信息，一个知识库包含多个页面和分类
 */
@Entity
@Table(name = "wiki")
public class Wiki {

    /**
     * 知识库ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 知识库名称
     */
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    /**
     * URL标识符，用于URL路径
     */
    @Column(name = "slug", nullable = false, unique = true, length = 128)
    private String slug;

    /**
     * 知识库描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Logo URL
     */
    @Column(name = "logo_url", length = 512)
    private String logoUrl;

    /**
     * 创建者/所有者ID
     */
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    /**
     * 可见性: 1公开, 2内部, 3私有
     */
    @Column(name = "visibility", nullable = false)
    private Integer visibility = 1;

    /**
     * 是否归档: 0否, 1是
     */
    @Column(name = "is_archived", nullable = false)
    private Integer isArchived = 0;

    /**
     * 页面数量(冗余字段，用于快速查询)
     */
    @Column(name = "page_count")
    private Integer pageCount = 0;

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
    public Wiki() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Integer isArchived) {
        this.isArchived = isArchived;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
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
