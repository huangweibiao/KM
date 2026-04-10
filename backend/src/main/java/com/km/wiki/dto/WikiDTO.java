package com.km.wiki.dto;

/**
 * 知识库数据传输对象
 * 用于创建和更新知识库的请求参数
 */
public class WikiDTO {

    /**
     * 知识库名称
     */
    private String name;

    /**
     * URL标识符
     */
    private String slug;

    /**
     * 知识库描述
     */
    private String description;

    /**
     * Logo URL
     */
    private String logoUrl;

    /**
     * 可见性: 1公开, 2内部, 3私有
     */
    private Integer visibility;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    /**
     * 创建知识库请求
     */
    public static class CreateRequest {

        /**
         * 知识库名称
         */
        private String name;

        /**
         * URL标识符
         */
        private String slug;

        /**
         * 知识库描述
         */
        private String description;

        /**
         * Logo URL
         */
        private String logoUrl;

        /**
         * 可见性: 1公开, 2内部, 3私有
         */
        private Integer visibility;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }
    }

    /**
     * 更新知识库请求
     */
    public static class UpdateRequest {

        /**
         * 知识库名称
         */
        private String name;

        /**
         * URL标识符
         */
        private String slug;

        /**
         * 知识库描述
         */
        private String description;

        /**
         * Logo URL
         */
        private String logoUrl;

        /**
         * 可见性: 1公开, 2内部, 3私有
         */
        private Integer visibility;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }
    }

    /**
     * 添加知识库成员请求
     */
    public static class AddMemberRequest {
        private Long userId;
        private String roleInWiki;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getRoleInWiki() {
            return roleInWiki;
        }

        public void setRoleInWiki(String roleInWiki) {
            this.roleInWiki = roleInWiki;
        }
    }
}
