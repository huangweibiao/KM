package com.km.wiki.vo;

import java.time.LocalDateTime;

/**
 * 知识库成员视图对象
 * 用于返回知识库成员信息给前端展示
 */
public class WikiMemberVO {

    /**
     * 关联ID
     */
    private Long id;

    /**
     * 知识库ID
     */
    private Long wikiId;

    /**
     * 知识库名称
     */
    private String wikiName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色: OWNER(所有者), ADMIN(管理员), EDITOR(编辑者), VIEWER(查看者)
     */
    private String roleInWiki;

    /**
     * 角色显示名称
     */
    private String roleName;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleInWiki() {
        return roleInWiki;
    }

    public void setRoleInWiki(String roleInWiki) {
        this.roleInWiki = roleInWiki;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
