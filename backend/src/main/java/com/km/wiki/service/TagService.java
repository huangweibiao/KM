package com.km.wiki.service;

import com.km.wiki.dto.TagDTO;
import com.km.wiki.entity.Tag;
import com.km.wiki.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 标签服务接口
 * 定义标签相关的业务逻辑操作
 */
public interface TagService {

    /**
     * 创建标签
     *
     * @param wikiId  知识库ID（可为null表示全局标签）
     * @param tagName 标签名称
     * @return 创建的标签视图
     */
    TagVO createTag(Long wikiId, String tagName);

    /**
     * 获取标签详情
     *
     * @param id 标签ID
     * @return 标签视图对象
     */
    TagVO getTagById(Long id);

    /**
     * 获取知识库的标签列表
     *
     * @param wikiId 知识库ID
     * @return 标签列表
     */
    List<TagVO> getTagsByWikiId(Long wikiId);

    /**
     * 获取全局标签列表
     *
     * @return 标签列表
     */
    List<TagVO> getGlobalTags();

    /**
     * 获取页面的标签列表
     *
     * @param pageId 页面ID
     * @return 标签列表
     */
    List<TagVO> getTagsByPageId(Long pageId);

    /**
     * 为页面添加标签
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     */
    void addTagToPage(Long pageId, Long tagId);

    /**
     * 为页面移除标签
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     */
    void removeTagFromPage(Long pageId, Long tagId);

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    void deleteTag(Long id);

    /**
     * 根据ID获取标签
     *
     * @param id 标签ID
     * @return 标签实体
     */
    Tag findById(Long id);

    /**
     * 更新标签
     *
     * @param id     标签ID
     * @param tagDTO 标签数据传输对象
     * @return 更新后的标签实体
     */
    Tag updateTag(Long id, TagDTO tagDTO);

    /**
     * 获取知识库的标签列表（分页）
     *
     * @param wikiId   知识库ID
     * @param pageable 分页参数
     * @return 标签分页数据
     */
    Page<TagVO> getWikiTags(Long wikiId, Pageable pageable);

    /**
     * 获取所有标签列表（分页）
     *
     * @param pageable 分页参数
     * @return 标签分页数据
     */
    Page<TagVO> getAllTags(Pageable pageable);

    /**
     * 根据名称查找或创建标签
     *
     * @param wikiId 知识库ID
     * @param name   标签名称
     * @return 标签实体
     */
    Tag findOrCreateTag(Long wikiId, String name);

    /**
     * 获取热门标签
     *
     * @param wikiId 知识库ID（可选，为空则获取全局热门标签）
     * @param limit  返回数量限制
     * @return 热门标签列表
     */
    List<TagVO> getPopularTags(Long wikiId, Integer limit);

    /**
     * 根据标签名称搜索
     *
     * @param keyword 搜索关键词
     * @param wikiId  知识库ID（可选）
     * @return 标签列表
     */
    List<TagVO> searchTags(String keyword, Long wikiId);

    /**
     * 批量创建标签
     *
     * @param wikiId   知识库ID
     * @param tagNames 标签名称列表
     * @return 创建的标签列表
     */
    List<Tag> batchCreateTags(Long wikiId, List<String> tagNames);

    /**
     * 获取标签使用详情
     *
     * @param tagId  标签ID
     * @param wikiId 知识库ID
     * @return 标签视图对象
     */
    TagVO getTagDetail(Long tagId, Long wikiId);

    /**
     * 合并标签
     *
     * @param sourceTagId  源标签ID
     * @param targetTagId   目标标签ID
     */
    void mergeTags(Long sourceTagId, Long targetTagId);
}
