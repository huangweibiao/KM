package com.km.wiki.dto;

/**
 * 分类DTO
 * 用于创建和更新分类时的数据传输
 */
public class CategoryDTO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * URL标识符
     */
    private String slug;

    /**
     * 父分类ID，用于构建树形结构
     */
    private Long parentId;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 默认构造函数
     */
    public CategoryDTO() {
    }

    // Getters and Setters
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 创建分类请求
     */
    public static class CreateRequest {

        /**
         * 知识库ID
         */
        private Long wikiId;

        /**
         * 分类名称
         */
        private String name;

        /**
         * URL标识符
         */
        private String slug;

        /**
         * 父分类ID
         */
        private Long parentId;

        /**
         * 排序顺序
         */
        private Integer sortOrder;

        // Getters and Setters
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

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public Integer getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }
    }

    /**
     * 更新分类请求
     */
    public static class UpdateRequest {

        /**
         * 分类名称
         */
        private String name;

        /**
         * URL标识符
         */
        private String slug;

        /**
         * 父分类ID
         */
        private Long parentId;

        /**
         * 排序顺序
         */
        private Integer sortOrder;

        // Getters and Setters
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

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public Integer getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }
    }
}
