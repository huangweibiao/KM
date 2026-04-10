package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 页面版本实体类
 * 对应数据库表: page_version
 * 用于存储页面的历史版本信息，支持版本回滚
 */
@Entity
@Table(name = "page_version")
public class PageVersion {

    /**
     * 版本ID，主键，自增
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
     * 版本号
     */
    @Column(name = "version", nullable = false)
    private Integer version;

    /**
     * 标题快照
     */
    @Column(name = "title", nullable = false, length = 256)
    private String title;

    /**
     * 内容快照
     */
    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    /**
     * 编辑者ID，外键关联user表
     */
    @Column(name = "editor_id", nullable = false)
    private Long editorId;

    /**
     * 修改备注
     */
    @Column(name = "change_note", length = 512)
    private String changeNote;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public PageVersion() {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
