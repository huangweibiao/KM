package com.km.wiki.repository;

import com.km.wiki.entity.Wiki;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 知识库数据访问接口
 * 提供知识库实体的CRUD操作和自定义查询方法
 */
@Repository
public interface WikiRepository extends JpaRepository<Wiki, Long> {

    /**
     * 根据URL标识符查询知识库
     *
     * @param slug URL标识符
     * @return 知识库Optional对象
     */
    Optional<Wiki> findBySlug(String slug);

    /**
     * 检查URL标识符是否已存在
     *
     * @param slug URL标识符
     * @return 是否存在
     */
    boolean existsBySlug(String slug);

    /**
     * 查询用户拥有的知识库列表
     *
     * @param ownerId 所有者ID
     * @return 知识库列表
     */
    List<Wiki> findByOwnerId(Long ownerId);

    /**
     * 根据归档状态查询知识库（分页）
     *
     * @param isArchived 是否归档
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    Page<Wiki> findByIsArchived(Integer isArchived, Pageable pageable);

    /**
     * 根据所有者ID和归档状态查询知识库（分页）
     *
     * @param ownerId 所有者ID
     * @param isArchived 是否归档
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    Page<Wiki> findByOwnerIdAndIsArchived(Long ownerId, Integer isArchived, Pageable pageable);

    /**
     * 查询公开的知识库列表
     *
     * @return 知识库列表
     */
    List<Wiki> findByVisibilityAndIsArchivedOrderByCreatedAtDesc(Integer visibility, Integer isArchived);

    /**
     * 根据可见性和归档状态查询知识库
     *
     * @param visibility 可见性
     * @param isArchived 是否归档
     * @return 知识库列表
     */
    List<Wiki> findByVisibilityAndIsArchived(Integer visibility, Integer isArchived);

    /**
     * 搜索知识库（名称或描述包含关键词）
     *
     * @param keyword 关键词
     * @return 知识库列表
     */
    @Query("SELECT w FROM Wiki w WHERE w.isArchived = 0 AND (w.name LIKE %:keyword% OR w.description LIKE %:keyword%)")
    List<Wiki> searchByKeyword(@Param("keyword") String keyword);
}
