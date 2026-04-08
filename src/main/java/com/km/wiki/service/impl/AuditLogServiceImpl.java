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

import java.time.LocalDateTime;

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
    public Page Page<AuditLog> getAuditLogs(Pageable pageable) {
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
    public Page Page<AuditLog> getAuditLogsByUser(Long userId, Pageable pageable) {
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
    public Page Page<AuditLog> getAuditLogsByResource(String resourceType, Long resourceId, Pageable pageable) {
        return auditLogRepository.findByResourceTypeAndResourceIdOrderByCreatedAtDesc(
                resourceType, resourceId, pageable);
    }
}
