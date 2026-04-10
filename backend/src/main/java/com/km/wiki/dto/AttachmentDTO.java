package com.km.wiki.dto;

/**
 * 附件数据传输对象
 * 用于创建和更新附件的请求参数
 */
public class AttachmentDTO {

    /**
     * 附件ID
     */
    private Long id;

    /**
     * 关联页面ID
     */
    private Long pageId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型（MIME）
     */
    private String fileType;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 访问URL
     */
    private String url;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 创建附件请求
     */
    public static class CreateRequest {

        /**
         * 关联页面ID
         */
        private Long pageId;

        /**
         * 文件名
         */
        private String name;

        /**
         * 原始文件名
         */
        private String originalName;

        /**
         * 文件大小（字节）
         */
        private Long fileSize;

        /**
         * 文件类型（MIME）
         */
        private String fileType;

        /**
         * 存储路径
         */
        private String storagePath;

        /**
         * 访问URL
         */
        private String url;

        // Getters and Setters
        public Long getPageId() {
            return pageId;
        }

        public void setPageId(Long pageId) {
            this.pageId = pageId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getStoragePath() {
            return storagePath;
        }

        public void setStoragePath(String storagePath) {
            this.storagePath = storagePath;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 更新附件请求
     */
    public static class UpdateRequest {

        /**
         * 文件名
         */
        private String name;

        /**
         * 存储路径
         */
        private String storagePath;

        /**
         * 访问URL
         */
        private String url;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStoragePath() {
            return storagePath;
        }

        public void setStoragePath(String storagePath) {
            this.storagePath = storagePath;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
