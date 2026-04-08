package com.km.wiki.repository;

import com.km.wiki.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类数据访问接口
 * 提供分类相关的数据库操作
 */
@Repository
public interface CategoryRepository extends JpaRepositoryRepository<Category, Long> {

    /**
     * 根据知识库ID查询所有分类
     *
     * @param wikiId 知识库ID
     * @return 分类列表
     */
    List List<Category> findByWikiId(Long wikiId);

    /**
     * 根据知识库ID和父分类ID查询分类
     *
     * @param wikiId   知识库ID
     * @param parentId 父分类ID
     * @return 分类列表
     */
    List List<Category> findByWikiIdAndParentId(Long wikiId, Long parentId);

    /**
     * 根据知识库ID统计分类数量
     *
     * @param wikiId 知识库ID
     * @return 分类数量
     */
    long countByWikiId(Long wikiId);
}
