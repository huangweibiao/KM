package com.km.wiki.service.impl;

import com.km.wiki.entity.PageTag;
import com.km.wiki.entity.Tag;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.repository.PageTagRepository;
import com.km.wiki.repository.TagRepository;
import com.km.wiki.repository.WikiRepository;
import com.km.wiki.service.TagService;
import com.km.wiki.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签服务实现类
 * 实现标签相关的业务逻辑
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PageTagRepository pageTagRepository;

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 创建标签
     *
     * @param wikiId  知识库ID（可为null表示全局标签）
     * @param tagName 标签名称
     * @return 创建的标签
     */
    @Override
    @Transactional
    public TagVO createTag(Long wikiId, String tagName) {
        // 如果指定了知识库，检查知识库是否存在
        if (wikiId != null && !wikiRepository.existsById(wikiId)) {
            throw new RuntimeException("知识库不存在");
        }

        // 检查标签是否已存在
        Tag existingTag = tagRepository.findByWikiIdAndName(wikiId, tagName);
        if (existingTag != null) {
            return convertToVO(existingTag);
        }

        // 创建标签
        Tag tag = new Tag();
        tag.setWikiId(wikiId);
        tag.setName(tagName);
        tag.setCreatedAt(LocalDateTime.now());

        Tag savedTag = tagRepository.save(tag);
        return convertToVO(savedTag);
    }

    /**
     * 获取标签详情
     *
     * @param id 标签ID
     * @return 标签视图对象
     */
    @Override
    public TagVO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("标签不存在"));
        return convertToVO(tag);
    }

    /**
     * 获取知识库的标签列表
     *
     * @param wikiId 知识库ID
     * @return 标签列表
     */
    @Override
    public List<TagVO> getTagsByWikiId(Long wikiId) {
        List<Tag> tags = tagRepository.findByWikiId(wikiId);
        return tags.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 获取全局标签列表
     *
     * @return 标签列表
     */
    @Override
    public List<TagVO> getGlobalTags() {
        List<Tag> tags = tagRepository.findByWikiIdIsNull();
        return tags.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 获取页面的标签列表
     *
     * @param pageId 页面ID
     * @return 标签列表
     */
    @Override
    public List<TagVO> getTagsByPageId(Long pageId) {
        List<Tag> tags = tagRepository.findByPageId(pageId);
        return tags.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 为页面添加标签
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     */
    @Override
    @Transactional
    public void addTagToPage(Long pageId, Long tagId) {
        // 检查页面是否存在
        if (!pageRepository.existsById(pageId)) {
            throw new RuntimeException("页面不存在");
        }

        // 检查标签是否存在
        if (!tagRepository.existsById(tagId)) {
            throw new RuntimeException("标签不存在");
        }

        // 检查是否已关联
        if (pageTagRepository.existsByPageIdAndTagId(pageId, tagId)) {
            return; // 已关联，直接返回
        }

        // 创建关联
        PageTag pageTag = new PageTag();
        pageTag.setPageId(pageId);
        pageTag.setTagId(tagId);
        pageTag.setCreatedAt(LocalDateTime.now());
        pageTagRepository.save(pageTag);
    }

    /**
     * 为页面移除标签
     *
     * @param pageId 页面ID
     * @param tagId  标签ID
     */
    @Override
    @Transactional
    public void removeTagFromPage(Long pageId, Long tagId) {
        pageTagRepository.deleteByPageIdAndTagId(pageId, tagId);
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    @Override
    @Transactional
    public void deleteTag(Long id) {
        // 先删除所有关联
        pageTagRepository.deleteByTagId(id);
        // 删除标签
        tagRepository.deleteById(id);
    }

    /**
     * 将标签实体转换为视图对象
     *
     * @param tag 标签实体
     * @return 标签视图对象
     */
    private TagVO convertToVO(Tag tag) {
        TagVO vo = new TagVO();
        vo.setId(tag.getId());
        vo.setWikiId(tag.getWikiId());
        vo.setName(tag.getName());
        vo.setCreatedAt(tag.getCreatedAt());
        return vo;
    }
}
