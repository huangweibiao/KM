package com.km.wiki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 知识库成员实体类
 * 对应数据库表: wiki_member
 * 用于存储知识库成员及其角色信息
 */
@Entity
@Table(name = "wiki_member")
public class WikiMember {

    /**
     * 关联ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 知识库ID
     */
    @Column(name = "wiki_id", nullable = false)
    private Long wikiId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色: OWNER(所有者), ADMIN(管理员), EDITOR(编辑者), VIEWER(查看者)
     */
    @Column(name = "role_in_wiki", nullable = false, length = 32)
    private String roleInWiki;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 默认构造函数
     */
    public WikiMember() {
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

    public Long getWikiId() {
        return wikiId;
    }

    public void setWikiId(Long wikiId) {
        this.wikiId = wikiId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
