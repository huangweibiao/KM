package com.km.wiki.service;

import com.km.wiki.entity.Notification;
import com.km.wiki.vo.NotificationVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 通知服务接口
 * 定义通知相关的业务逻辑操作
 */
public interface NotificationService {

    /**
     * 创建通知
     *
     * @param userId  接收通知的用户ID
     * @param type    通知类型
     * @param title   通知标题
     * @param content 通知内容
     * @param linkUrl 跳转链接
     * @return 创建后的通知实体
     */
    Notification createNotification(Long userId, String type, String title, String content, String linkUrl);

    /**
     * 获取用户的通知列表（分页）
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 通知分页数据
     */
    Page<NotificationVO> getUserNotifications(Long userId, Pageable pageable);

    /**
     * 获取用户的未读通知列表
     *
     * @param userId 用户ID
     * @return 未读通知列表
     */
    List<NotificationVO> getUnreadNotifications(Long userId);

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 更新后的通知实体
     */
    Notification markAsRead(Long id);

    /**
     * 标记通知为已读（兼容旧调用）
     *
     * @param id     通知ID
     * @param userId 用户ID
     * @return 更新后的通知实体
     */
    Notification markAsRead(Long id, Long userId);

    /**
     * 标记所有通知为已读
     *
     * @param userId 用户ID
     */
    void markAllAsRead(Long userId);

    /**
     * 删除通知
     *
     * @param id 通知ID
     */
    void deleteNotification(Long id);

    /**
     * 获取未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Long getUnreadCount(Long userId);

    /**
     * 获取通知列表（兼容旧调用）
     *
     * @param userId   用户ID
     * @param isRead   是否已读
     * @param pageable 分页参数
     * @return 通知列表
     */
    List<Notification> getNotifications(Long userId, Boolean isRead, Pageable pageable);

    /**
     * 发送评论通知
     *
     * @param pageId  页面ID
     * @param userId  评论者ID
     * @param content 评论内容
     */
    void sendCommentNotification(Long pageId, Long userId, String content);

    /**
     * 发送提及通知
     *
     * @param pageId     页面ID
     * @param mentionedUserIds 被提及的用户ID列表
     * @param userId     提及者ID
     * @param content    提及内容
     */
    void sendMentionNotification(Long pageId, List<Long> mentionedUserIds, Long userId, String content);

    /**
     * 发送页面编辑通知
     *
     * @param pageId     页面ID
     * @param editorId   编辑者ID
     * @param watchUsers 关注该页面的用户ID列表
     */
    void sendEditNotification(Long pageId, Long editorId, List<Long> watchUsers);

    /**
     * 清理过期通知
     *
     * @param days 保留天数
     */
    void cleanupOldNotifications(int days);
}
