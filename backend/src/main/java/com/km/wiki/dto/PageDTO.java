package com.km.wiki.dto;

import java.util.List;

/**
 * 页面数据传输对象
 * 用于创建和更新页面的请求参数
 */
public class PageDTO {

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
     * 内容格式: markdown, html
     */
    private String contentFormat;

    /**
     * 页面类型: doc, wiki, faq
     */
    private String type;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 父页面ID
     */
    private Long parentPageId;

    /**
     * 修改备注
     */
    private String changeNote;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;

    // Getters and Setters
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

    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    /**
     * 创建页面请求
     */
    public static class CreateRequest {

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
         * 页面内容
         */
        private String content;

        /**
         * 内容格式: markdown, html
         */
        private String contentFormat;

        /**
         * 页面类型: doc, wiki, faq
         */
        private String type;

        /**
         * 状态: 0草稿, 1已发布
         */
        private Integer status;

        /**
         * 分类ID
         */
        private Long categoryId;

        /**
         * 父页面ID
         */
        private Long parentPageId;

        /**
         * 标签ID列表
         */
        private List<Long> tagIds;

        // Getters and Setters
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public List<Long> getTagIds() {
            return tagIds;
        }

        public void setTagIds(List<Long> tagIds) {
            this.tagIds = tagIds;
        }
    }

    /**
     * 更新页面请求
     */
    public static class UpdateRequest {

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
         * 内容格式: markdown, html
         */
        private String contentFormat;

        /**
         * 页面类型: doc, wiki, faq
         */
        private String type;

        /**
         * 状态: 0草稿, 1已发布
         */
        private Integer status;

        /**
         * 分类ID
         */
        private Long categoryId;

        /**
         * 父页面ID
         */
        private Long parentPageId;

        /**
         * 修改备注
         */
        private String changeNote;

        /**
         * 标签ID列表
         */
        private List<Long> tagIds;

        // Getters and Setters
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public String getChangeNote() {
            return changeNote;
        }

        public void setChangeNote(String changeNote) {
            this.changeNote = changeNote;
        }

        public List<Long> getTagIds() {
            return tagIds;
        }

        public void setTagIds(List<Long> tagIds) {
            this.tagIds = tagIds;
        }
    }

    /**
     * 关注页面请求
     */
    public static class WatchRequest {

        /**
         * 关注类型: ALL(所有更新), COMMENT(仅评论), EDIT(仅编辑)
         */
        private String watchType;

        // Getters and Setters
        public String getWatchType() {
            return watchType;
        }

        public void setWatchType(String watchType) {
            this.watchType = watchType;
        }
    }
}
