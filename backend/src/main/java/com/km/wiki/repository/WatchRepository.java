package com.km.wiki.repository;

import com.km.wiki.entity.Watch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 关注数据访问接口
 * 提供关注相关的数据库操作方法
 */
@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {

    /**
     * 根据用户ID和页面ID查询关注记录
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 关注记录Optional
     */
    Optional<Watch> findByUserIdAndPageId(Long userId, Long pageId);

    /**
     * 检查用户是否已关注页面
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return true表示已关注
     */
    boolean existsByUserIdAndPageId(Long userId, Long pageId);

    /**
     * 根据用户ID查询关注列表（分页）
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 关注分页列表
     */
    Page<Watch> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 根据页面ID查询所有关注者
     *
     * @param pageId 页面ID
     * @return 关注列表
     */
    List<Watch> findByPageId(Long pageId);

    /**
     * 统计页面的关注数量
     *
     * @param pageId 页面ID
     * @return 关注数量
     */
    @Query("SELECT COUNT(w) FROM Watch w WHERE w.pageId = :pageId")
    Long countByPageId(@Param("pageId") Long pageId);

    /**
     * 删除用户的关注记录
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     */
    void deleteByUserIdAndPageId(Long userId, Long pageId);
}
