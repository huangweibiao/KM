package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 附件实体类
 * 对应数据库表: attachment
 * 用于存储页面附件信息
 */
@Entity
@Table(name = "attachment")
public class Attachment {

    /**
     * 附件ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联页面ID
     */
    @Column(name = "page_id", nullable = false)
    private Long pageId;

    /**
     * 文件名
     */
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    /**
     * 原始文件名
     */
    @Column(name = "original_name", nullable = false, length = 256)
    private String originalName;

    /**
     * 文件大小（字节）
     */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * 文件类型（MIME）
     */
    @Column(name = "file_type", nullable = false, length = 64)
    private String fileType;

    /**
     * 存储路径
     */
    @Column(name = "storage_path", nullable = false, length = 512)
    private String storagePath;

    /**
     * 访问URL
     */
    @Column(name = "url", nullable = false, length = 512)
    private String url;

    /**
     * 上传者ID
     */
    @Column(name = "uploader_id", nullable = false)
    private Long uploaderId;

    /**
     * 是否删除: 0未删除, 1已删除
     */
    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted = 0;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public Attachment() {
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

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
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
}
