package com.km.wiki.repository;

import com.km.wiki.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知数据访问接口
 * 提供通知相关的数据库操作方法
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 根据用户ID查询通知列表
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 通知分页列表
     */
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 根据用户ID和是否已读查询通知
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @param pageable 分页参数
     * @return 通知分页列表
     */
    Page<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(
            Long userId, Integer isRead, Pageable pageable);

    /**
     * 查询用户的未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    long countByUserIdAndIsRead(Long userId, Integer isRead);

    /**
     * 查询用户的所有未读通知
     *
     * @param userId 用户ID
     * @return 未读通知列表
     */
    List<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Integer isRead);

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @param updatedAt 更新时间
     */
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = 1, n.updatedAt = :updatedAt WHERE n.id = :id")
    void markAsRead(@Param("id") Long id, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 标记用户的所有通知为已读
     *
     * @param userId 用户ID
     * @param updatedAt 更新时间
     */
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = 1, n.updatedAt = :updatedAt WHERE n.userId = :userId AND n.isRead = 0")
    void markAllAsRead(@Param("userId") Long userId, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 根据类型查询通知
     *
     * @param userId 用户ID
     * @param type 通知类型
     * @param pageable 分页参数
     * @return 通知分页列表
     */
    Page<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(
            Long userId, String type, Pageable pageable);

    /**
     * 删除指定时间之前的已读通知
     *
     * @param userId 用户ID
     * @param beforeTime 时间点
     * @return 删除的记录数
     */
    long deleteByUserIdAndIsReadAndCreatedAtBefore(Long userId, Integer isRead, LocalDateTime beforeTime);

    /**
     * 查询用户的未读通知
     *
     * @param userId 用户ID
     * @return 未读通知列表
     */
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    /**
     * 统计用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    long countByUserIdAndIsReadFalse(Long userId);

    /**
     * 删除指定时间之前的所有通知
     *
     * @param beforeTime 时间点
     */
    void deleteByCreatedAtBefore(LocalDateTime beforeTime);
}
