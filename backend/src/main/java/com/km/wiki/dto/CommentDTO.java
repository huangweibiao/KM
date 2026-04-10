package com.km.wiki.dto;

/**
 * 评论DTO
 * 用于创建和更新评论时的数据传输
 */
public class CommentDTO {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID，用于回复评论
     */
    private Long parentId;

    /**
     * 默认构造函数
     */
    public CommentDTO() {
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 创建评论请求
     */
    public static class CreateRequest {

        /**
         * 评论内容
         */
        private String content;

        /**
         * 父评论ID，用于回复评论
         */
        private Long parentId;

        // Getters and Setters
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }
    }

    /**
     * 更新评论请求
     */
    public static class UpdateRequest {

        /**
         * 评论内容
         */
        private String content;

        // Getters and Setters
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
