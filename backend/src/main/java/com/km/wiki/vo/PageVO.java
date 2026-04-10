package com.km.wiki.vo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 页面视图对象
 * 用于返回页面详细信息给前端展示
 */
public class PageVO {

    /**
     * 页面ID
     */
    private Long id;

    /**
     * 所属知识库ID
     */
    private Long wikiId;

    /**
     * 知识库名称
     */
    private String wikiName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父页面ID
     */
    private Long parentPageId;

    /**
     * 父页面标题
     */
    private String parentPageTitle;

    /**
     * 页面标题
     */
    private String title;

    /**
     * URL标识符
     */
    private String slug;

    /**
     * 页面内容
     */
    private String content;

    /**
     * 内容格式
     */
    private String contentFormat;

    /**
     * 页面类型
     */
    private String type;

    /**
     * 当前版本号
     */
    private Integer version;

    /**
     * 创建者ID
     */
    private Long authorId;

    /**
     * 创建者用户名
     */
    private String authorName;

    /**
     * 创建者昵称
     */
    private String authorNickname;

    /**
     * 创建者头像
     */
    private String authorAvatar;

    /**
     * 最后编辑者ID
     */
    private Long lastEditorId;

    /**
     * 最后编辑者用户名
     */
    private String lastEditorName;

    /**
     * 状态: 0草稿, 1已发布
     */
    private Integer status;

    /**
     * 发布时间
     */
    private LocalDateTime publishedAt;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 是否已点赞
     */
    private Boolean isLiked;

    /**
     * 是否已收藏
     */
    private Boolean isFavorited;

    /**
     * 是否已关注
     */
    private Boolean isWatched;

    /**
     * 标签列表
     */
    private List<TagVO> tags;

    /**
     * 附件列表
     */
    private List<AttachmentVO> attachments;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

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

    public String getWikiName() {
        return wikiName;
    }

    public void setWikiName(String wikiName) {
        this.wikiName = wikiName;
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

    public Long getParentPageId() {
        return parentPageId;
    }

    public void setParentPageId(Long parentPageId) {
        this.parentPageId = parentPageId;
    }

    public String getParentPageTitle() {
        return parentPageTitle;
    }

    public void setParentPageTitle(String parentPageTitle) {
        this.parentPageTitle = parentPageTitle;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Long getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(Long lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public String getLastEditorName() {
        return lastEditorName;
    }

    public void setLastEditorName(String lastEditorName) {
        this.lastEditorName = lastEditorName;
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

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean isWatched) {
        this.isWatched = isWatched;
    }

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }

    public List<AttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVO> attachments) {
        this.attachments = attachments;
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
