package com.km.wiki.service;

import com.km.wiki.dto.PageDTO;
import com.km.wiki.entity.Page;
import com.km.wiki.vo.PageTreeVO;
import com.km.wiki.vo.PageVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 页面服务接口
 * 定义页面相关的业务逻辑操作
 */
public interface PageService {

    /**
     * 创建页面
     *
     * @param wikiId  知识库ID
     * @param pageDTO 页面数据传输对象
     * @return 创建后的页面实体
     */
    Page createPage(Long wikiId, PageDTO pageDTO);

    /**
     * 更新页面
     *
     * @param id      页面ID
     * @param pageDTO 页面数据传输对象
     * @return 更新后的页面实体
     */
    Page updatePage(Long id, PageDTO pageDTO);

    /**
     * 删除页面（软删除）
     *
     * @param id 页面ID
     */
    void deletePage(Long id);

    /**
     * 根据ID获取页面
     *
     * @param id 页面ID
     * @return 页面实体
     */
    Page findById(Long id);

    /**
     * 获取页面详情
     *
     * @param id 页面ID
     * @return 页面视图对象
     */
    PageVO getPageDetail(Long id);

    /**
     * 获取知识库的页面树形列表
     *
     * @param wikiId 知识库ID
     * @return 页面树形列表
     */
    List List<PageTreeVO> getPageTree(Long wikiId);

    /**
     * 获取页面的版本历史
     *
     * @param pageId 页面ID
     * @return 版本列表
     */
    List List<PageVO> getPageVersions(Long pageId);

    /**
     * 恢复页面到指定版本
     *
     * @param pageId  页面ID
     * @param version 版本号
     */
    void restoreVersion(Long pageId, Integer version);

    /**
     * 发布页面
     *
     * @param id 页面ID
     */
    void publishPage(Long id);

    /**
     * 增加页面浏览次数
     *
     * @param id 页面ID
     */
    void incrementViewCount(Long id);

    /**
     * 切换页面点赞状态
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 当前点赞状态
     */
    boolean toggleLike(Long pageId, Long userId);

    /**
     * 切换页面收藏状态
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 当前收藏状态
     */
    boolean toggleFavorite(Long pageId, Long userId);

    /**
     * 切换页面关注状态
     *
     * @param pageId     页面ID
     * @param userId     用户ID
     * @param watchType  关注类型
     * @return 当前关注状态
     */
    boolean toggleWatch(Long pageId, Long userId, String watchType);

    /**
     * 检查用户是否收藏了页面
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    boolean isFavorited(Long pageId, Long userId);

    /**
     * 搜索页面
     *
     * @param keyword  关键词
     * @param wikiId   知识库ID（可选）
     * @param pageable 分页参数
     * @return 页面列表
     */
    org.springframework.data.domain.Page.Page<PageVO> searchPages(String keyword, Long wikiId, Pageable pageable);
}
