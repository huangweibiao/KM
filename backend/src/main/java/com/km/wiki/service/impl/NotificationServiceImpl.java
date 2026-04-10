package com.km.wiki.service.impl;

import com.km.wiki.entity.Notification;
import com.km.wiki.repository.NotificationRepository;
import com.km.wiki.service.NotificationService;
import com.km.wiki.vo.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知服务实现类
 * 实现通知相关的业务逻辑
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * 创建通知
     */
    @Override
    @Transactional
    public Notification createNotification(Long userId, String type, String title, String content, String linkUrl) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(type);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setLinkUrl(linkUrl);
        notification.setIsRead(0);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    /**
     * 获取用户的通知列表（分页）
     */
    @Override
    public Page<NotificationVO> getUserNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::convertToVO);
    }

    /**
     * 获取用户的未读通知列表
     */
    @Override
    public List<NotificationVO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 标记通知为已读
     */
    @Override
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("通知不存在"));
        notification.setIsRead(1);
        return notificationRepository.save(notification);
    }

    /**
     * 标记通知为已读（兼容旧调用）
     */
    @Override
    @Transactional
    public Notification markAsRead(Long id, Long userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("通知不存在"));
        notification.setIsRead(1);
        return notificationRepository.save(notification);
    }

    /**
     * 标记所有通知为已读
     */
    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        notifications.forEach(n -> n.setIsRead(1));
        notificationRepository.saveAll(notifications);
    }

    /**
     * 获取通知列表（兼容旧调用）
     */
    @Override
    public List<Notification> getNotifications(Long userId, Boolean isRead, Pageable pageable) {
        if (isRead == null) {
            return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable).getContent();
        } else {
            Integer isReadInt = isRead ? 1 : 0;
            return notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(userId, isReadInt, pageable).getContent();
        }
    }

    /**
     * 删除通知
     */
    @Override
    @Transactional
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    /**
     * 获取未读通知数量
     */
    @Override
    public Long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    /**
     * 发送评论通知
     */
    @Override
    @Async
    public void sendCommentNotification(Long pageId, Long userId, String content) {
        // 实现评论通知逻辑
    }

    /**
     * 发送提及通知
     */
    @Override
    @Async
    public void sendMentionNotification(Long pageId, List<Long> mentionedUserIds, Long userId, String content) {
        // 实现提及通知逻辑
    }

    /**
     * 发送页面编辑通知
     */
    @Override
    @Async
    public void sendEditNotification(Long pageId, Long editorId, List<Long> watchUsers) {
        // 实现编辑通知逻辑
    }

    /**
     * 清理过期通知
     */
    @Override
    @Transactional
    public void cleanupOldNotifications(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        notificationRepository.deleteByCreatedAtBefore(cutoffDate);
    }

    /**
     * 转换为VO
     */
    private NotificationVO convertToVO(Notification notification) {
        NotificationVO vo = new NotificationVO();
        vo.setId(notification.getId());
        vo.setUserId(notification.getUserId());
        vo.setType(notification.getType());
        vo.setTitle(notification.getTitle());
        vo.setContent(notification.getContent());
        vo.setLinkUrl(notification.getLinkUrl());
        vo.setIsRead(notification.getIsRead() != null && notification.getIsRead() == 1);
        vo.setCreatedAt(notification.getCreatedAt());
        return vo;
    }
}
