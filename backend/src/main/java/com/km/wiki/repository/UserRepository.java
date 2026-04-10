package com.km.wiki.repository;

import com.km.wiki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户数据访问接口
 * 提供用户实体的CRUD操作和自定义查询方法
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户Optional对象
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱地址
     * @return 用户Optional对象
     */
    Optional<User> findByEmail(String email);

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否已存在
     *
     * @param email 邮箱地址
     * @return 是否存在
     */
    boolean existsByEmail(String email);
}
