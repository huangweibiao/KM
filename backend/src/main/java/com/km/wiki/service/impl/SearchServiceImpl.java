package com.km.wiki.service.impl;

import com.km.wiki.entity.Page;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.service.SearchService;
import com.km.wiki.vo.PageVO;
import com.km.wiki.vo.SearchResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索服务实现类
 * 实现基于MySQL全文索引的搜索功能
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PageRepository pageRepository;

    /**
     * 关键词搜索
     *
     * @param keyword  搜索关键词
     * @param wikiId   知识库ID（可选）
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Override
    public org.springframework.data.domain.Page<PageVO> search(String keyword, Long wikiId, Pageable pageable) {
        org.springframework.data.domain.Page<Page> result;

        if (wikiId != null) {
            // 在指定知识库中搜索
            result = pageRepository.searchByWikiIdAndKeyword(wikiId, keyword, pageable);
        } else {
            // 全局搜索 - 使用wikiId为0来获取所有页面（实际应该返回空结果）
            result = pageRepository.searchByWikiIdAndKeyword(0L, keyword, pageable);
        }

        return result.map(this::convertToVO);
    }

    /**
     * 搜索建议
     *
     * @param keyword 搜索关键词
     * @return 建议列表
     */
    @Override
    public List<String> suggest(String keyword) {
        // 搜索标题匹配的内容，返回前10个标题作为建议
        org.springframework.data.domain.Page<Page> pageResult = pageRepository.findTop10ByTitleContainingAndStatusAndIsDeleted(
                keyword, 1, 0, null);

        return pageResult.getContent().stream()
                .map(Page::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * 全文搜索页面
     */
    @Override
    public org.springframework.data.domain.Page<SearchResultVO> searchPages(String keyword, Long wikiId, Pageable pageable) {
        return search(keyword, wikiId, pageable).map(page -> {
            SearchResultVO vo = new SearchResultVO();
            vo.setId(page.getId());
            vo.setTitle(page.getTitle());
            vo.setContent(page.getContent());
            vo.setType("page");
            return vo;
        });
    }

    /**
     * 搜索知识库
     */
    @Override
    public org.springframework.data.domain.Page<SearchResultVO> searchWikis(String keyword, Pageable pageable) {
        return org.springframework.data.domain.Page.empty(pageable);
    }

    /**
     * 搜索标签
     */
    @Override
    public org.springframework.data.domain.Page<SearchResultVO> searchTags(String keyword, Long wikiId, Pageable pageable) {
        return org.springframework.data.domain.Page.empty(pageable);
    }

    /**
     * 获取热门搜索关键词
     */
    @Override
    public List<String> getHotKeywords(Integer limit) {
        return List.of();
    }

    /**
     * 保存搜索历史
     */
    @Override
    public void saveSearchHistory(Long userId, String keyword) {
        // 简化实现
    }

    /**
     * 获取用户搜索历史
     */
    @Override
    public List<String> getUserSearchHistory(Long userId, Integer limit) {
        return List.of();
    }

    /**
     * 清除用户搜索历史
     */
    @Override
    public void clearSearchHistory(Long userId, String keyword) {
        // 简化实现
    }

    /**
     * 获取相关推荐页面
     */
    @Override
    public List<SearchResultVO> getRelatedPages(Long pageId, Long wikiId, Integer limit) {
        return List.of();
    }

    /**
     * 索引页面到搜索引擎
     */
    @Override
    public void indexPage(Long pageId) {
        // 简化实现
    }

    /**
     * 从搜索引擎中删除页面索引
     */
    @Override
    public void deletePageIndex(Long pageId) {
        // 简化实现
    }

    /**
     * 重建搜索索引
     *
     * @param wikiId 知识库ID
     */
    @Override
    public void rebuildIndex(Long wikiId) {
        // 简单实现，实际项目中可以使用Elasticsearch等全文搜索引擎
        // 这里只记录日志
    }

    /**
     * 将页面实体转换为视图对象
     *
     * @param page 页面实体
     * @return 页面视图对象
     */
    private PageVO convertToVO(Page page) {
        PageVO vo = new PageVO();
        vo.setId(page.getId());
        vo.setWikiId(page.getWikiId());
        vo.setCategoryId(page.getCategoryId());
        vo.setParentPageId(page.getParentPageId());
        vo.setTitle(page.getTitle());
        vo.setSlug(page.getSlug());
        vo.setContent(page.getContent());
        vo.setContentFormat(page.getContentFormat());
        vo.setType(page.getType());
        vo.setVersion(page.getVersion());
        vo.setAuthorId(page.getAuthorId());
        vo.setLastEditorId(page.getLastEditorId());
        vo.setStatus(page.getStatus());
        vo.setPublishedAt(page.getPublishedAt());
        vo.setViewCount(page.getViewCount());
        vo.setLikeCount(page.getLikeCount());
        vo.setCreatedAt(page.getCreatedAt());
        vo.setUpdatedAt(page.getUpdatedAt());
        return vo;
    }
}
