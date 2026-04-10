package com.km.wiki.vo;

import java.time.LocalDateTime;

/**
 * 标签视图对象
 * 用于返回标签信息给前端展示
 */
public class TagVO {

    /**
     * 标签ID
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
     * 标签名称
     */
    private String name;

    /**
     * 使用次数
     */
    private Integer usageCount;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
