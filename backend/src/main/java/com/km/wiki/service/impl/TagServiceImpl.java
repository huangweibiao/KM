package com.km.wiki.service.impl;

import com.km.wiki.dto.TagDTO;
import com.km.wiki.entity.PageTag;
import com.km.wiki.entity.Tag;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.repository.PageTagRepository;
import com.km.wiki.repository.TagRepository;
import com.km.wiki.repository.WikiRepository;
import com.km.wiki.service.TagService;
import com.km.wiki.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Tag existingTag = tagRepository.findByWikiIdAndName(wikiId, tagName).orElse(null);
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
     * 根据ID获取标签
     */
    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    /**
     * 更新标签
     */
    @Override
    @Transactional
    public Tag updateTag(Long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("标签不存在"));
        if (tagDTO.getName() != null) {
            tag.setName(tagDTO.getName());
        }
        return tagRepository.save(tag);
    }

    /**
     * 获取知识库的标签列表（分页）
     */
    @Override
    public Page<TagVO> getWikiTags(Long wikiId, Pageable pageable) {
        return tagRepository.findByWikiId(wikiId).stream()
                .map(this::convertToVO)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                ));
    }

    /**
     * 获取所有标签列表（分页）
     */
    @Override
    public Page<TagVO> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable).map(this::convertToVO);
    }

    /**
     * 根据名称查找或创建标签
     */
    @Override
    @Transactional
    public Tag findOrCreateTag(Long wikiId, String name) {
        return tagRepository.findByWikiIdAndName(wikiId, name).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setWikiId(wikiId);
            tag.setName(name);
            tag.setCreatedAt(LocalDateTime.now());
            return tagRepository.save(tag);
        });
    }

    /**
     * 获取热门标签
     */
    @Override
    public List<TagVO> getPopularTags(Long wikiId, Integer limit) {
        List<Tag> tags;
        if (wikiId != null) {
            tags = tagRepository.findByWikiId(wikiId);
        } else {
            tags = tagRepository.findByWikiIdIsNull();
        }
        return tags.stream().limit(limit).map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 根据标签名称搜索
     */
    @Override
    public List<TagVO> searchTags(String keyword, Long wikiId) {
        List<Tag> tags = wikiId != null ? tagRepository.findByWikiId(wikiId) : tagRepository.findAll();
        return tags.stream()
                .filter(t -> t.getName().contains(keyword))
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 批量创建标签
     */
    @Override
    @Transactional
    public List<Tag> batchCreateTags(Long wikiId, List<String> tagNames) {
        List<Tag> result = new ArrayList<>();
        for (String name : tagNames) {
            Tag tag = findOrCreateTag(wikiId, name);
            result.add(tag);
        }
        return result;
    }

    /**
     * 获取标签使用详情
     */
    @Override
    public TagVO getTagDetail(Long tagId, Long wikiId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("标签不存在"));
        TagVO vo = convertToVO(tag);
        // 设置使用数量
        List<PageTag> pageTags = pageTagRepository.findByTagId(tagId);
        vo.setUsageCount(pageTags.size());
        return vo;
    }

    /**
     * 合并标签（将源标签的关联迁移到目标标签，然后删除源标签）
     *
     * @param sourceTagId 源标签ID
     * @param targetTagId 目标标签ID
     */
    @Override
    @Transactional
    public void mergeTags(Long sourceTagId, Long targetTagId) {
        // 获取源标签的所有页面关联
        List<PageTag> sourceRelations = pageTagRepository.findByTagId(sourceTagId);
        
        for (PageTag relation : sourceRelations) {
            // 检查目标标签是否已经关联了该页面
            if (!pageTagRepository.existsByPageIdAndTagId(relation.getPageId(), targetTagId)) {
                // 创建新的关联
                PageTag newRelation = new PageTag();
                newRelation.setPageId(relation.getPageId());
                newRelation.setTagId(targetTagId);
                newRelation.setCreatedAt(LocalDateTime.now());
                pageTagRepository.save(newRelation);
            }
        }
        
        // 删除源标签的所有关联
        pageTagRepository.deleteByTagId(sourceTagId);
        
        // 删除源标签
        tagRepository.deleteById(sourceTagId);
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
