package com.km.wiki.vo;

import java.time.LocalDateTime;

/**
 * 附件视图对象
 * 用于返回附件信息给前端展示
 */
public class AttachmentVO {

    /**
     * 附件ID
     */
    private Long id;

    /**
     * 页面ID
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
     * 文件大小（格式化显示）
     */
    private String fileSizeDisplay;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 访问URL
     */
    private String url;

    /**
     * 上传者ID
     */
    private Long uploaderId;

    /**
     * 上传者用户名
     */
    private String uploaderName;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

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

    public String getFileSizeDisplay() {
        return fileSizeDisplay;
    }

    public void setFileSizeDisplay(String fileSizeDisplay) {
        this.fileSizeDisplay = fileSizeDisplay;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
