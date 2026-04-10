package com.km.wiki.repository;

import com.km.wiki.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论数据访问接口
 * 提供评论相关的数据库操作方法
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据页面ID查询评论列表（分页）
     * 只查询未删除的评论
     *
     * @param pageId 页面ID
     * @param pageable 分页参数
     * @return 评论分页列表
     */
    Page<Comment> findByPageIdAndIsDeleted(Long pageId, Integer isDeleted, Pageable pageable);

    /**
     * 根据页面ID查询顶级评论（parentId为null）
     *
     * @param pageId 页面ID
     * @return 顶级评论列表
     */
    List<Comment> findByPageIdAndParentIdIsNullAndIsDeletedOrderByCreatedAtDesc(Long pageId, Integer isDeleted);

    /**
     * 根据父评论ID查询子评论
     *
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    List<Comment> findByParentIdAndIsDeletedOrderByCreatedAtAsc(Long parentId, Integer isDeleted);

    /**
     * 统计页面的评论数量
     *
     * @param pageId 页面ID
     * @return 评论数量
     */
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.pageId = :pageId AND c.isDeleted = 0")
    Long countByPageId(@Param("pageId") Long pageId);

    /**
     * 根据用户ID查询评论列表
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 评论分页列表
     */
    Page<Comment> findByUserIdAndIsDeletedOrderByCreatedAtDesc(Long userId, Integer isDeleted, Pageable pageable);

    /**
     * 根据页面ID查询评论列表（按创建时间倒序）
     *
     * @param pageId 页面ID
     * @param isDeleted 是否删除
     * @return 评论列表
     */
    List<Comment> findByPageIdAndIsDeletedOrderByCreatedAtDesc(Long pageId, Integer isDeleted);
}
