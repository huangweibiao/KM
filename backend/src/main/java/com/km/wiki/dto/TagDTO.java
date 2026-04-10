package com.km.wiki.dto;

/**
 * 标签DTO
 * 用于创建标签时的数据传输
 */
public class TagDTO {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 默认构造函数
     */
    public TagDTO() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
