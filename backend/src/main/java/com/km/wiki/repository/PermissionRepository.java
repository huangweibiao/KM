package com.km.wiki.repository;

import com.km.wiki.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 权限数据访问接口
 * 提供权限实体的CRUD操作和自定义查询方法
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据权限代码查询权限
     *
     * @param code 权限代码
     * @return 权限Optional对象
     */
    Optional<Permission> findByCode(String code);

    /**
     * 根据资源类型查询权限列表
     *
     * @param resourceType 资源类型
     * @return 权限列表
     */
    List<Permission> findByResourceType(String resourceType);

    /**
     * 检查权限代码是否已存在
     *
     * @param code 权限代码
     * @return 是否存在
     */
    boolean existsByCode(String code);
}
