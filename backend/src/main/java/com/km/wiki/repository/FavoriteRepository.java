package com.km.wiki.repository;

import com.km.wiki.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 收藏数据访问接口
 * 提供收藏相关的数据库操作方法
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * 根据用户ID和页面ID查询收藏记录
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 收藏记录Optional
     */
    Optional<Favorite> findByUserIdAndPageId(Long userId, Long pageId);

    /**
     * 检查用户是否已收藏页面
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return true表示已收藏
     */
    boolean existsByUserIdAndPageId(Long userId, Long pageId);

    /**
     * 根据用户ID查询收藏列表（分页）
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 收藏分页列表
     */
    Page<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 统计页面的收藏数量
     *
     * @param pageId 页面ID
     * @return 收藏数量
     */
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.pageId = :pageId")
    Long countByPageId(@Param("pageId") Long pageId);

    /**
     * 删除用户的收藏记录
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     */
    void deleteByUserIdAndPageId(Long userId, Long pageId);
}
