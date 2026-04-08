package com.km.wiki.repository;

import com.km.wiki.entity.PageTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 页面标签关联数据访问接口
 * 提供页面标签关联相关的数据库操作
 */
@Repository
public interface PageTagRepository extends JpaRepositoryRepository<PageTag, Long> {

    /**
     * 根据页面ID查询所有关联
     *
     * @param pageId 页面ID
     * @return 关联列表
     */
    List List<PageTag> findByPageId(Long pageId);

    /**
     * 根据标签ID查询所有关联
     *
     * @param tagId 标签ID
     * @return 关联列表
     */
    List List<PageTag> findByTagId(Long tagId);

    /**
     * 根据页面ID和标签ID查询关联
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     * @return 关联对象
     */
    PageTag findByPageIdAndTagId(Long pageId, Long tagId);

    /**
     * 根据页面ID删除所有关联
     *
     * @param pageId 页面ID
     */
    void deleteByPageId(Long pageId);

    /**
     * 根据页面ID和标签ID删除关联
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     */
    void deleteByPageIdAndTagId(Long pageId, Long tagId);
}
