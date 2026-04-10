package com.km.wiki.repository;

import com.km.wiki.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 标签数据访问接口
 * 提供标签相关的数据库操作
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据知识库ID查询所有标签
     *
     * @param wikiId 知识库ID
     * @return 标签列表
     */
    List<Tag> findByWikiId(Long wikiId);

    /**
     * 查询不属于任何知识库的标签
     *
     * @return 标签列表
     */
    List<Tag> findByWikiIdIsNull();

    /**
     * 根据知识库ID和标签名称查询标签
     *
     * @param wikiId 知识库ID
     * @param name   标签名称
     * @return 标签对象
     */
    Optional<Tag> findByWikiIdAndName(Long wikiId, String name);

    /**
     * 根据知识库ID统计标签数量
     *
     * @param wikiId 知识库ID
     * @return 标签数量
     */
    long countByWikiId(Long wikiId);

    /**
     * 根据页面ID查询标签
     *
     * @param pageId 页面ID
     * @return 标签列表
     */
    @Query("SELECT t FROM Tag t JOIN PageTag pt ON t.id = pt.tagId WHERE pt.pageId = :pageId")
    List<Tag> findByPageId(@Param("pageId") Long pageId);
}
