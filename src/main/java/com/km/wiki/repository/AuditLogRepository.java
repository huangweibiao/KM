package com.km.wiki.repository;

import com.km.wiki.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 审计日志数据访问接口
 * 提供AuditLog实体的数据库操作方法
 */
@Repository
public interface AuditLogRepository extends JpaRepositoryRepository<AuditLog, Long> {

    /**
     * 根据用户ID查询审计日志（分页）
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    Page Page<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 根据资源类型和资源ID查询审计日志
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 审计日志列表
     */
    List List<AuditLog> findByResourceTypeAndResourceIdOrderByCreatedAtDesc(String resourceType, Long resourceId);

    /**
     * 根据操作类型查询审计日志（分页）
     *
     * @param action   操作类型
     * @param pageable 分页参数
     * @return 审计日志分页列表
     */
    Page Page<AuditLog> findByActionOrderByCreatedAtDesc(String action, Pageable pageable);
}
