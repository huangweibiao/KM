package com.km.wiki.service;

import com.km.wiki.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 审计日志服务接口
 * 定义审计日志相关的业务逻辑操作
 */
public interface AuditLogService {

    /**
     * 异步记录审计日志
     *
     * @param userId       用户ID
     * @param username     用户名
     * @param action       操作类型
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param oldValue     旧值
     * @param newValue     新值
     * @param ipAddress    IP地址
     * @param userAgent    User Agent
     */
    void log(Long userId, String username, String action, String resourceType,
             Long resourceId, Object oldValue, Object newValue,
             String ipAddress, String userAgent);

    /**
     * 获取审计日志列表
     *
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    Page<AuditLog> getAuditLogs(Pageable pageable);

    /**
     * 获取用户的审计日志
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    Page<AuditLog> getAuditLogsByUser(Long userId, Pageable pageable);

    /**
     * 获取资源的审计日志
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param pageable     分页参数
     * @return 审计日志分页列表
     */
    Page<AuditLog> getAuditLogsByResource(String resourceType, Long resourceId, Pageable pageable);

    /**
     * 创建审计日志
     *
     * @param userId      操作用户ID
     * @param username    用户名
     * @param action      操作类型
     * @param resourceType 资源类型
     * @param resourceId  资源ID
     * @param oldValue    旧值（JSON格式）
     * @param newValue    新值（JSON格式）
     * @param ipAddress   IP地址
     * @param userAgent   User Agent
     * @return 创建后的审计日志实体
     */
    AuditLog createAuditLog(Long userId, String username, String action, String resourceType,
                            Long resourceId, String oldValue, String newValue,
                            String ipAddress, String userAgent);

    /**
     * 记录用户登录
     *
     * @param userId    用户ID
     * @param username  用户名
     * @param ipAddress IP地址
     * @param userAgent User Agent
     */
    void logLogin(Long userId, String username, String ipAddress, String userAgent);

    /**
     * 记录用户登出
     *
     * @param userId    用户ID
     * @param username  用户名
     * @param ipAddress IP地址
     * @param userAgent User Agent
     */
    void logLogout(Long userId, String username, String ipAddress, String userAgent);

    /**
     * 记录资源创建
     *
     * @param userId       操作用户ID
     * @param username     用户名
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param newValue     新值（JSON格式）
     * @param ipAddress    IP地址
     * @param userAgent    User Agent
     */
    void logCreate(Long userId, String username, String resourceType, Long resourceId,
                   String newValue, String ipAddress, String userAgent);

    /**
     * 记录资源更新
     *
     * @param userId       操作用户ID
     * @param username     用户名
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param oldValue     旧值（JSON格式）
     * @param newValue     新值（JSON格式）
     * @param ipAddress    IP地址
     * @param userAgent    User Agent
     */
    void logUpdate(Long userId, String username, String resourceType, Long resourceId,
                   String oldValue, String newValue, String ipAddress, String userAgent);

    /**
     * 记录资源删除
     *
     * @param userId       操作用户ID
     * @param username     用户名
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param oldValue     旧值（JSON格式）
     * @param ipAddress    IP地址
     * @param userAgent    User Agent
     */
    void logDelete(Long userId, String username, String resourceType, Long resourceId,
                   String oldValue, String ipAddress, String userAgent);

    /**
     * 获取用户的操作日志
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 审计日志分页数据
     */
    Page<AuditLog> getUserAuditLogs(Long userId, Pageable pageable);

    /**
     * 获取资源的操作日志
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param pageable     分页参数
     * @return 审计日志分页数据
     */
    Page<AuditLog> getResourceAuditLogs(String resourceType, Long resourceId, Pageable pageable);

    /**
     * 根据操作类型查询审计日志
     *
     * @param action   操作类型
     * @param pageable 分页参数
     * @return 审计日志分页数据
     */
    Page<AuditLog> getAuditLogsByAction(String action, Pageable pageable);

    /**
     * 获取指定时间范围内的审计日志
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pageable  分页参数
     * @return 审计日志分页数据
     */
    Page<AuditLog> getAuditLogsByTimeRange(LocalDateTime startTime,
                                           LocalDateTime endTime,
                                           Pageable pageable);

    /**
     * 删除指定日期之前的审计日志
     *
     * @param beforeDate 日期
     */
    void deleteAuditLogsBefore(LocalDateTime beforeDate);

    /**
     * 获取审计日志统计信息
     *
     * @return 统计信息
     */
    Map<String, Object> getAuditLogStats();

    /**
     * 导出审计日志
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param action    操作类型（可选）
     * @return 审计日志列表
     */
    List<AuditLog> exportAuditLogs(LocalDateTime startDate, LocalDateTime endDate, String action);
}
