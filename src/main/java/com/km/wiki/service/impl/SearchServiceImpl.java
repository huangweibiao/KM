package com.km.wiki.service.impl;

import com.km.wiki.entity.Page;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.service.SearchService;
import com.km.wiki.vo.PageVO;
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
    public org.springframework.data.domain.Page.Page<PageVO> search(String keyword, Long wikiId, Pageable pageable) {
        org.springframework.data.domain.Page.Page<Page> result;

        if (wikiId != null) {
            // 在指定知识库中搜索
            result = pageRepository.searchByWikiIdAndKeyword(wikiId, keyword, pageable);
        } else {
            // 全局搜索
            result = pageRepository.searchByKeyword(keyword, pageable);
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
        List List<Page> pages = pageRepository.findTop10ByTitleContainingAndStatusAndIsDeleted(
                keyword, 1, 0);

        return pages.stream()
                .map(Page::getTitle)
                .collect(Collectors.toList());
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
