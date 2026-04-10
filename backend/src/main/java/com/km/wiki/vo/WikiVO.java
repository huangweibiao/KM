package com.km.wiki.vo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库视图对象
 * 用于返回知识库信息给前端展示
 */
public class WikiVO {

    /**
     * 知识库ID
     */
    private Long id;

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
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 创建者用户名
     */
    private String ownerName;

    /**
     * 创建者昵称
     */
    private String ownerNickname;

    /**
     * 创建者头像
     */
    private String ownerAvatar;

    /**
     * 可见性: 1公开, 2内部, 3私有
     */
    private Integer visibility;

    /**
     * 可见性显示名称
     */
    private String visibilityName;

    /**
     * 是否归档
     */
    private Boolean isArchived;

    /**
     * 页面数量
     */
    private Integer pageCount;

    /**
     * 成员数量
     */
    private Integer memberCount;

    /**
     * 分类列表
     */
    private List<CategoryVO> categories;

    /**
     * 成员列表
     */
    private List<WikiMemberVO> members;

    /**
     * 当前用户在知识库中的角色
     */
    private String currentUserRole;

    /**
     * 当前用户是否有权限编辑
     */
    private Boolean canEdit;

    /**
     * 当前用户是否有权限删除
     */
    private Boolean canDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getVisibilityName() {
        return visibilityName;
    }

    public void setVisibilityName(String visibilityName) {
        this.visibilityName = visibilityName;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public List<CategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVO> categories) {
        this.categories = categories;
    }

    public List<WikiMemberVO> getMembers() {
        return members;
    }

    public void setMembers(List<WikiMemberVO> members) {
        this.members = members;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(String currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 成员视图对象
     */
    public static class MemberVO {
        private Long id;
        private Long userId;
        private String username;
        private String nickname;
        private String avatarUrl;
        private String roleInWiki;
        private LocalDateTime joinedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getRoleInWiki() {
            return roleInWiki;
        }

        public void setRoleInWiki(String roleInWiki) {
            this.roleInWiki = roleInWiki;
        }

        public LocalDateTime getJoinedAt() {
            return joinedAt;
        }

        public void setJoinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
        }
    }
}
