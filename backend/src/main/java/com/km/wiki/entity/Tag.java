package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 标签实体类
 * 对应数据库表: tag
 * 用于存储知识库和页面的标签信息
 */
@Entity
@Table(name = "tag")
public class Tag {

    /**
     * 标签ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属知识库ID，外键关联wiki表，为空表示全局标签
     */
    @Column(name = "wiki_id")
    private Long wikiId;

    /**
     * 标签名称
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public Tag() {
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

    public Long getWikiId() {
        return wikiId;
    }

    public void setWikiId(Long wikiId) {
        this.wikiId = wikiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
