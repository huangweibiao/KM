package com.km.wiki.vo;

import java.time.LocalDateTime;

/**
 * 页面版本视图对象
 * 用于返回页面版本信息给前端展示
 */
public class PageVersionVO {

    /**
     * 版本ID
     */
    private Long id;

    /**
     * 页面ID
     */
    private Long pageId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 标题快照
     */
    private String title;

    /**
     * 内容快照
     */
    private String content;

    /**
     * 编辑者ID
     */
    private Long editorId;

    /**
     * 编辑者用户名
     */
    private String editorName;

    /**
     * 编辑者昵称
     */
    private String editorNickname;

    /**
     * 编辑者头像
     */
    private String editorAvatar;

    /**
     * 修改备注
     */
    private String changeNote;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getEditorNickname() {
        return editorNickname;
    }

    public void setEditorNickname(String editorNickname) {
        this.editorNickname = editorNickname;
    }

    public String getEditorAvatar() {
        return editorAvatar;
    }

    public void setEditorAvatar(String editorAvatar) {
        this.editorAvatar = editorAvatar;
    }

    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
