package com.km.wiki.repository;

import com.km.wiki.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 角色数据访问接口
 * 提供角色实体的CRUD操作和自定义查询方法
 */
@Repository
public interface RoleRepository extends JpaRepositoryRepository<Role, Long> {

    /**
     * 根据角色代码查询角色
     *
     * @param code 角色代码
     * @return 角色Optional对象
     */
    Optional Optional<Role> findByCode(String code);

    /**
     * 检查角色代码是否已存在
     *
     * @param code 角色代码
     * @return 是否存在
     */
    boolean existsByCode(String code);
}
