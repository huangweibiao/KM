package com.km.wiki.service.impl;

import com.km.wiki.entity.AuditLog;
import com.km.wiki.repository.AuditLogRepository;
import com.km.wiki.service.AuditLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 审计日志服务实现类
 * 实现审计日志的异步记录和查询功能
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    @Override
    @Async
    public void log(Long userId, String username, String action, String resourceType,
                    Long resourceId, Object oldValue, Object newValue,
                    String ipAddress, String userAgent) {
        try {
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setUsername(username);
            log.setAction(action);
            log.setResourceType(resourceType);
            log.setResourceId(resourceId);
            log.setOldValue(oldValue != null ? objectMapper.writeValueAsString(oldValue) : null);
            log.setNewValue(newValue != null ? objectMapper.writeValueAsString(newValue) : null);
            log.setIpAddress(ipAddress);
            log.setUserAgent(userAgent);
            log.setCreatedAt(LocalDateTime.now());

            auditLogRepository.save(log);
        } catch (Exception e) {
            // 日志记录失败不应影响主业务流程
            System.err.println("Failed to save audit log: " + e.getMessage());
        }
    }

    /**
     * 获取审计日志列表
     *
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    @Override
    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /**
     * 获取用户的审计日志
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    @Override
    public Page<AuditLog> getAuditLogsByUser(Long userId, Pageable pageable) {
        return auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    /**
     * 获取资源的审计日志
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param pageable     分页参数
     * @return 审计日志分页列表
     */
    @Override
    public Page<AuditLog> getAuditLogsByResource(String resourceType, Long resourceId, Pageable pageable) {
        return auditLogRepository.findByResourceTypeAndResourceIdOrderByCreatedAtDesc(
                resourceType, resourceId, pageable);
    }

    @Override
    public AuditLog createAuditLog(Long userId, String username, String action, String resourceType,
                                   Long resourceId, String oldValue, String newValue,
                                   String ipAddress, String userAgent) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setUsername(username);
        log.setAction(action);
        log.setResourceType(resourceType);
        log.setResourceId(resourceId);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setCreatedAt(LocalDateTime.now());
        return auditLogRepository.save(log);
    }

    @Override
    @Async
    public void logLogin(Long userId, String username, String ipAddress, String userAgent) {
        log(userId, username, "LOGIN", null, null, null, null, ipAddress, userAgent);
    }

    @Override
    @Async
    public void logLogout(Long userId, String username, String ipAddress, String userAgent) {
        log(userId, username, "LOGOUT", null, null, null, null, ipAddress, userAgent);
    }

    @Override
    @Async
    public void logCreate(Long userId, String username, String resourceType, Long resourceId,
                          String newValue, String ipAddress, String userAgent) {
        log(userId, username, "CREATE", resourceType, resourceId, null, newValue, ipAddress, userAgent);
    }

    @Override
    @Async
    public void logUpdate(Long userId, String username, String resourceType, Long resourceId,
                          String oldValue, String newValue, String ipAddress, String userAgent) {
        log(userId, username, "UPDATE", resourceType, resourceId, oldValue, newValue, ipAddress, userAgent);
    }

    @Override
    @Async
    public void logDelete(Long userId, String username, String resourceType, Long resourceId,
                          String oldValue, String ipAddress, String userAgent) {
        log(userId, username, "DELETE", resourceType, resourceId, oldValue, null, ipAddress, userAgent);
    }

    @Override
    public Page<AuditLog> getUserAuditLogs(Long userId, Pageable pageable) {
        return getAuditLogsByUser(userId, pageable);
    }

    @Override
    public Page<AuditLog> getResourceAuditLogs(String resourceType, Long resourceId, Pageable pageable) {
        return getAuditLogsByResource(resourceType, resourceId, pageable);
    }

    @Override
    public Page<AuditLog> getAuditLogsByAction(String action, Pageable pageable) {
        return auditLogRepository.findByActionOrderByCreatedAtDesc(action, pageable);
    }

    @Override
    public Page<AuditLog> getAuditLogsByTimeRange(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        // 简化实现，返回空结果
        return Page.empty(pageable);
    }

    @Override
    @Transactional
    public void deleteAuditLogsBefore(LocalDateTime beforeDate) {
        // 简化实现
    }

    @Override
    public Map<String, Object> getAuditLogStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", auditLogRepository.count());
        return stats;
    }

    @Override
    public List<AuditLog> exportAuditLogs(LocalDateTime startDate, LocalDateTime endDate, String action) {
        // 简化实现，返回空列表
        return Collections.emptyList();
    }
}
