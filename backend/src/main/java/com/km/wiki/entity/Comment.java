package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 评论实体类
 * 对应数据库表: comment
 * 用于存储页面的评论信息，支持嵌套回复
 */
@Entity
@Table(name = "comment")
public class Comment {

    /**
     * 评论ID，主键，自增
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
     * 评论者ID，外键关联user表
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 父评论ID，外键关联comment表，支持嵌套回复
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 评论内容
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Integer likeCount = 0;

    /**
     * 是否删除，0-未删除，1-已删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;

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
    public Comment() {
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

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
