package com.km.wiki.repository;

import com.km.wiki.entity.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 页面数据访问接口
 * 提供页面实体的CRUD操作和自定义查询方法
 */
@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    /**
     * 根据知识库ID查询页面列表
     *
     * @param wikiId 知识库ID
     * @return 页面列表
     */
    List<Page> findByWikiId(Long wikiId);

    /**
     * 根据知识库ID和删除状态查询页面列表
     *
     * @param wikiId 知识库ID
     * @param isDeleted 是否删除
     * @return 页面列表
     */
    List<Page> findByWikiIdAndIsDeletedOrderByCreatedAtDesc(Long wikiId, Integer isDeleted);

    /**
     * 根据分类ID查询页面列表
     *
     * @param categoryId 分类ID
     * @return 页面列表
     */
    List<Page> findByCategoryId(Long categoryId);

    /**
     * 根据知识库ID和分类ID查询页面列表
     *
     * @param wikiId 知识库ID
     * @param categoryId 分类ID
     * @return 页面列表
     */
    List<Page> findByWikiIdAndCategoryId(Long wikiId, Long categoryId);

    /**
     * 根据父页面ID查询子页面列表
     *
     * @param parentPageId 父页面ID
     * @return 页面列表
     */
    List<Page> findByParentPageId(Long parentPageId);

    /**
     * 根据知识库ID和状态查询页面列表
     *
     * @param wikiId 知识库ID
     * @param status 状态
     * @return 页面列表
     */
    List<Page> findByWikiIdAndStatus(Long wikiId, Integer status);

    /**
     * 根据知识库ID和状态查询页面列表（排序）
     *
     * @param wikiId 知识库ID
     * @param status 状态
     * @return 页面列表
     */
    List<Page> findByWikiIdAndStatusOrderByCreatedAtDesc(Long wikiId, Integer status);

    /**
     * 根据URL标识符和知识库ID查询页面
     *
     * @param slug URL标识符
     * @param wikiId 知识库ID
     * @return 页面Optional对象
     */
    Optional<Page> findBySlugAndWikiId(String slug, Long wikiId);

    /**
     * 检查URL标识符在知识库中是否已存在
     *
     * @param slug URL标识符
     * @param wikiId 知识库ID
     * @return 是否存在
     */
    boolean existsBySlugAndWikiId(String slug, Long wikiId);

    /**
     * 根据创建者ID查询页面列表
     *
     * @param authorId 创建者ID
     * @return 页面列表
     */
    List<Page> findByAuthorId(Long authorId);

    /**
     * 搜索页面（标题或内容包含关键词）
     *
     * @param wikiId 知识库ID
     * @param keyword 关键词
     * @return 页面列表
     */
    @Query("SELECT p FROM Page p WHERE p.wikiId = :wikiId AND p.isDeleted = 0 " +
           "AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%)")
    List<Page> searchByKeyword(@Param("wikiId") Long wikiId, @Param("keyword") String keyword);

    /**
     * 统计知识库下的页面数量
     *
     * @param wikiId 知识库ID
     * @return 页面数量
     */
    long countByWikiId(Long wikiId);

    /**
     * 统计分类下的页面数量
     *
     * @param categoryId 分类ID
     * @return 页面数量
     */
    long countByCategoryId(Long categoryId);

    /**
     * 根据知识库ID和删除状态统计页面数量
     *
     * @param wikiId 知识库ID
     * @param isDeleted 是否删除
     * @return 页面数量
     */
    long countByWikiIdAndIsDeleted(Long wikiId, Integer isDeleted);

    /**
     * 根据知识库ID和删除状态查询页面列表（按创建时间升序）
     *
     * @param wikiId 知识库ID
     * @param isDeleted 是否删除
     * @return 页面列表
     */
    List<Page> findByWikiIdAndIsDeletedOrderByCreatedAtAsc(Long wikiId, Integer isDeleted);

    /**
     * 根据父页面ID和删除状态查询子页面
     *
     * @param parentPageId 父页面ID
     * @param isDeleted 是否删除
     * @return 页面列表
     */
    List<Page> findByParentPageIdAndIsDeleted(Long parentPageId, Integer isDeleted);

    /**
     * 根据知识库ID和URL标识符查询页面
     *
     * @param wikiId 知识库ID
     * @param slug URL标识符
     * @return 页面Optional对象
     */
    Optional<Page> findByWikiIdAndSlug(Long wikiId, String slug);

    /**
     * 检查知识库中是否存在指定URL标识符的页面
     *
     * @param wikiId 知识库ID
     * @param slug URL标识符
     * @return 是否存在
     */
    boolean existsByWikiIdAndSlug(Long wikiId, String slug);

    /**
     * 增加页面浏览次数
     *
     * @param id 页面ID
     */
    @Modifying
    @Query("UPDATE Page p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);

    /**
     * 搜索页面（分页）
     *
     * @param wikiId 知识库ID
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 页面分页列表
     */
    @Query("SELECT p FROM Page p WHERE p.wikiId = :wikiId AND p.isDeleted = 0 " +
           "AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%)")
    org.springframework.data.domain.Page<Page> searchByWikiIdAndKeyword(
            @Param("wikiId") Long wikiId,
            @Param("keyword") String keyword,
            Pageable pageable);

    /**
     * 根据标题模糊查询（分页）
     *
     * @param title 标题关键词
     * @param status 状态
     * @param isDeleted 是否删除
     * @param pageable 分页参数
     * @return 页面分页列表
     */
    org.springframework.data.domain.Page<Page> findTop10ByTitleContainingAndStatusAndIsDeleted(
            String title, Integer status, Integer isDeleted, Pageable pageable);
}
