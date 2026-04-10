package com.km.wiki.repository;

import com.km.wiki.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户角色关联数据访问接口
 * 提供用户角色关联实体的CRUD操作和自定义查询方法
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * 根据用户ID查询角色关联列表
     *
     * @param userId 用户ID
     * @return 角色关联列表
     */
    List<UserRole> findByUserId(Long userId);

    /**
     * 根据角色ID查询用户关联列表
     *
     * @param roleId 角色ID
     * @return 用户关联列表
     */
    List<UserRole> findByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID查询关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色关联Optional对象
     */
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 是否存在
     */
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户ID删除角色关联
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据角色ID删除用户关联
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID删除关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void deleteByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 统计用户的角色数量
     *
     * @param userId 用户ID
     * @return 角色数量
     */
    long countByUserId(Long userId);

    /**
     * 统计角色的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    long countByRoleId(Long roleId);

    /**
     * 根据用户ID查询角色代码列表
     *
     * @param userId 用户ID
     * @return 角色代码列表
     */
    @Query(value = "SELECT r.code FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = :userId", nativeQuery = true)
    List<String> findRoleCodesByUserId(@Param("userId") Long userId);
}
