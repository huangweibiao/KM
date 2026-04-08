package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 页面标签关联实体类
 * 对应数据库表: page_tag
 * 用于建立页面和标签的多对多关系
 */
@Entity
@Table(name = "page_tag", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"page_id", "tag_id"})
})
public class PageTag {

    /**
     * 关联ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 页面ID，外键关联page表
     */
    @Column(name = "page_id", nullable = false)
    private Long pageId;

    /**
     * 标签ID，外键关联tag表
     */
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public PageTag() {
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

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
