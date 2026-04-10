package com.km.wiki.service;

import com.km.wiki.vo.SearchResultVO;
import com.km.wiki.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 搜索服务接口
 * 定义搜索相关的业务逻辑操作
 */
public interface SearchService {

    /**
     * 关键词搜索
     *
     * @param keyword  搜索关键词
     * @param wikiId   知识库ID（可选）
     * @param pageable 分页参数
     * @return 搜索结果
     */
    Page<PageVO> search(String keyword, Long wikiId, Pageable pageable);

    /**
     * 搜索建议
     *
     * @param keyword 搜索关键词
     * @return 建议列表
     */
    List<String> suggest(String keyword);

    /**
     * 全文搜索页面
     *
     * @param keyword  搜索关键词
     * @param wikiId   知识库ID（可选，为空则搜索所有知识库）
     * @param pageable 分页参数
     * @return 搜索结果分页数据
     */
    Page<SearchResultVO> searchPages(String keyword, Long wikiId, Pageable pageable);

    /**
     * 搜索知识库
     *
     * @param keyword  搜索关键词
     * @param pageable 分页参数
     * @return 搜索结果分页数据
     */
    Page<SearchResultVO> searchWikis(String keyword, Pageable pageable);

    /**
     * 搜索标签
     *
     * @param keyword  搜索关键词
     * @param wikiId   知识库ID（可选，为空则搜索所有知识库）
     * @param pageable 分页参数
     * @return 搜索结果分页数据
     */
    Page<SearchResultVO> searchTags(String keyword, Long wikiId, Pageable pageable);

    /**
     * 获取热门搜索关键词
     *
     * @param limit 返回数量限制
     * @return 热门关键词列表
     */
    List<String> getHotKeywords(Integer limit);

    /**
     * 保存搜索历史
     *
     * @param userId  用户ID
     * @param keyword 搜索关键词
     */
    void saveSearchHistory(Long userId, String keyword);

    /**
     * 获取用户搜索历史
     *
     * @param userId 用户ID
     * @param limit  返回数量限制
     * @return 搜索历史列表
     */
    List<String> getUserSearchHistory(Long userId, Integer limit);

    /**
     * 清除用户搜索历史
     *
     * @param userId  用户ID
     * @param keyword 搜索关键词（可选，为空则清除所有）
     */
    void clearSearchHistory(Long userId, String keyword);

    /**
     * 获取相关推荐页面
     *
     * @param pageId   当前页面ID
     * @param wikiId   知识库ID
     * @param limit    推荐数量限制
     * @return 相关页面列表
     */
    List<SearchResultVO> getRelatedPages(Long pageId, Long wikiId, Integer limit);

    /**
     * 索引页面到搜索引擎
     *
     * @param pageId 页面ID
     */
    void indexPage(Long pageId);

    /**
     * 从搜索引擎中删除页面索引
     *
     * @param pageId 页面ID
     */
    void deletePageIndex(Long pageId);

    /**
     * 批量重建索引
     *
     * @param wikiId 知识库ID（可选，为空则重建所有）
     */
    void rebuildIndex(Long wikiId);
}
