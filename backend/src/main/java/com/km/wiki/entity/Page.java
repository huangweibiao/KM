package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 页面实体类
 * 对应数据库表: page
 * 用于存储知识库页面内容，支持多种格式(Markdown/HTML)
 */
@Entity
@Table(name = "page")
public class Page {

    /**
     * 页面ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属知识库ID
     */
    @Column(name = "wiki_id", nullable = false)
    private Long wikiId;

    /**
     * 分类ID，可为null
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 父页面ID，支持嵌套页面结构
     */
    @Column(name = "parent_page_id")
    private Long parentPageId;

    /**
     * 页面标题
     */
    @Column(name = "title", nullable = false, length = 256)
    private String title;

    /**
     * URL标识符
     */
    @Column(name = "slug", nullable = false, length = 256)
    private String slug;

    /**
     * 页面内容，支持长文本
     */
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    /**
     * 内容格式: markdown, html
     */
    @Column(name = "content_format", length = 16)
    private String contentFormat = "markdown";

    /**
     * 知识类型: doc, wiki, faq
     */
    @Column(name = "type", length = 16)
    private String type = "doc";

    /**
     * 当前版本号
     */
    @Column(name = "version", nullable = false)
    private Integer version = 1;

    /**
     * 创建者ID
     */
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    /**
     * 最后编辑者ID
     */
    @Column(name = "last_editor_id")
    private Long lastEditorId;

    /**
     * 状态: 0草稿, 1已发布
     */
    @Column(name = "status")
    private Integer status = 0;

    /**
     * 发布时间
     */
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Integer viewCount = 0;

    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Integer likeCount = 0;

    /**
     * 逻辑删除: 0未删除, 1已删除
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
    public Page() {
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

    public Long getWikiId() {
        return wikiId;
    }

    public void setWikiId(Long wikiId) {
        this.wikiId = wikiId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentPageId() {
        return parentPageId;
    }

    public void setParentPageId(Long parentPageId) {
        this.parentPageId = parentPageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(Long lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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
