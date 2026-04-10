package com.km.wiki.repository;

import com.km.wiki.entity.PageVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 页面版本数据访问接口
 * 提供页面版本相关的数据库操作方法
 */
@Repository
public interface PageVersionRepository extends JpaRepository<PageVersion, Long> {

    /**
     * 根据页面ID查询版本历史
     *
     * @param pageId 页面ID
     * @param pageable 分页参数
     * @return 版本列表
     */
    List<PageVersion> findByPageIdOrderByVersionDesc(Long pageId, Pageable pageable);

    /**
     * 根据页面ID查询所有版本
     *
     * @param pageId 页面ID
     * @return 版本列表
     */
    List<PageVersion> findByPageIdOrderByVersionDesc(Long pageId);

    /**
     * 根据页面ID和版本号查询特定版本
     *
     * @param pageId 页面ID
     * @param version 版本号
     * @return 版本对象
     */
    Optional<PageVersion> findByPageIdAndVersion(Long pageId, Integer version);

    /**
     * 查询页面的最大版本号
     *
     * @param pageId 页面ID
     * @return 最大版本号
     */
    @Query("SELECT MAX(pv.version) FROM PageVersion pv WHERE pv.pageId = :pageId")
    Integer findMaxVersionByPageId(@Param("pageId") Long pageId);

    /**
     * 根据页面ID删除所有版本记录
     *
     * @param pageId 页面ID
     */
    @Modifying
    @Query("DELETE FROM PageVersion pv WHERE pv.pageId = :pageId")
    void deleteByPageId(@Param("pageId") Long pageId);

    /**
     * 统计页面的版本数量
     *
     * @param pageId 页面ID
     * @return 版本数量
     */
    long countByPageId(Long pageId);

    /**
     * 删除指定版本之前的所有旧版本（保留最近N个版本）
     *
     * @param pageId 页面ID
     * @param keepVersion 保留的最低版本号
     */
    @Modifying
    @Query("DELETE FROM PageVersion pv WHERE pv.pageId = :pageId AND pv.version < :keepVersion")
    void deleteOldVersions(@Param("pageId") Long pageId, @Param("keepVersion") Integer keepVersion);
}
