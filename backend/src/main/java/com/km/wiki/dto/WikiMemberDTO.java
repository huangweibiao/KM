package com.km.wiki.dto;

/**
 * 知识库成员DTO
 * 用于添加成员时的数据传输
 */
public class WikiMemberDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成员在知识库中的角色
     * 可选值: OWNER, ADMIN, EDITOR, VIEWER
     */
    private String roleInWiki;

    /**
     * 默认构造函数
     */
    public WikiMemberDTO() {
    }

    // Getters and Setters
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
