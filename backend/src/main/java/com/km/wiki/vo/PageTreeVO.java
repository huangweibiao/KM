package com.km.wiki.vo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 页面树形视图对象
 * 用于返回页面的树形结构给前端展示
 */
public class PageTreeVO {

    /**
     * 页面ID
     */
    private Long id;

    /**
     * 知识库ID
     */
    private Long wikiId;

    /**
     * 页面标题
     */
    private String title;

    /**
     * URL标识符
     */
    private String slug;

    /**
     * 页面类型
     */
    private String type;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 子页面列表
     */
    private List<PageTreeVO> children;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 是否已发布
     */
    private Boolean published;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<PageTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<PageTreeVO> children) {
        this.children = children;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     * 父页面ID
     */
    private Long parentPageId;

    public Long getParentPageId() {
        return parentPageId;
    }

    public void setParentPageId(Long parentPageId) {
        this.parentPageId = parentPageId;
    }
}
