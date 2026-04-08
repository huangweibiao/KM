package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.entity.Notification;
import com.km.wiki.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 * 处理通知查询、标记已读等接口
 */
@RestController
@RequestMapping("/api")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取当前用户的通知列表
     *
     * @param isRead         是否已读筛选
     * @param pageable       分页参数
     * @param authentication 当前认证信息
     * @return 通知列表
     */
    @GetMapping("/notifications")
    public Result<List<List<Notification>> getNotifications(
            @RequestParam(required = false) Boolean isRead,
            Pageable pageable,
            Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List List<Notification> notifications = notificationService.getNotifications(userId, isRead, pageable);
        return Result.success(notifications);
    }

    /**
     * 获取未读通知数量
     *
     * @param authentication 当前认证信息
     * @return 未读数量
     */
    @GetMapping("/notifications/unread-count")
    public Result<Long> getUnreadCount(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        Long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }

    /**
     * 标记通知为已读
     *
     * @param id             通知ID
     * @param authentication 当前认证信息
     * @return 操作结果
     */
    @PutMapping("/notifications/{id}/read")
    public Result<Void> markAsRead(
            @PathVariable Long id,
            Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        notificationService.markAsRead(id, userId);
        logger.info("用户 {} 标记通知 {} 为已读", userId, id);
        return Result.success();
    }

    /**
     * 标记所有通知为已读
     *
     * @param authentication 当前认证信息
     * @return 操作结果
     */
    @PutMapping("/notifications/read-all")
    public Result<Void> markAllAsRead(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        notificationService.markAllAsRead(userId);
        logger.info("用户 {} 标记所有通知为已读", userId);
        return Result.success();
    }

    /**
     * 获取当前用户ID
     *
     * @param authentication 认证信息
     * @return 用户ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("用户未登录");
        }
        return Long.valueOf(authentication.getName());
    }
}
