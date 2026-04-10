package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 关注实体类
 * 用于存储用户对页面的关注关系
 */
@Entity
@Table(name = "watch", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "page_id"})
})
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "page_id", nullable = false)
    private Long pageId;

    /**
     * 关注类型: ALL(所有更新), COMMENT(仅评论), EDIT(仅编辑)
     */
    @Column(name = "watch_type", length = 20)
    private String watchType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

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

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getWatchType() {
        return watchType;
    }

    public void setWatchType(String watchType) {
        this.watchType = watchType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
