package com.km.wiki.service;

import com.km.wiki.dto.PageDTO;
import com.km.wiki.entity.Page;
import com.km.wiki.vo.PageTreeVO;
import com.km.wiki.vo.PageVO;
import com.km.wiki.vo.PageVersionVO;
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
     * @param wikiId   知识库ID
     * @param pageDTO  页面创建信息
     * @param authorId 作者ID
     * @return 创建的页面视图
     */
    PageVO createPage(Long wikiId, PageDTO.CreateRequest pageDTO, Long authorId);

    /**
     * 获取页面详情
     *
     * @param id 页面ID
     * @return 页面视图对象
     */
    PageVO getPageById(Long id);

    /**
     * 根据slug获取页面
     *
     * @param wikiId 知识库ID
     * @param slug   页面标识符
     * @return 页面视图对象
     */
    PageVO getPageBySlug(Long wikiId, String slug);

    /**
     * 获取知识库的页面树形列表
     *
     * @param wikiId 知识库ID
     * @return 页面树形列表
     */
    List<PageTreeVO> getPageTree(Long wikiId);

    /**
     * 获取页面的子页面
     *
     * @param parentId 父页面ID
     * @return 子页面列表
     */
    List<PageVO> getChildPages(Long parentId);

    /**
     * 更新页面
     *
     * @param id       页面ID
     * @param pageDTO  页面更新信息
     * @param editorId 编辑者ID
     * @return 更新后的页面视图
     */
    PageVO updatePage(Long id, PageDTO.UpdateRequest pageDTO, Long editorId);

    /**
     * 删除页面（软删除）
     *
     * @param id 页面ID
     * @param userId 用户ID
     */
    void deletePage(Long id, Long userId);

    /**
     * 获取页面版本历史
     *
     * @param pageId  页面ID
     * @param pageable 分页参数
     * @return 版本列表
     */
    List<PageVersionVO> getPageVersions(Long pageId, Pageable pageable);

    /**
     * 恢复页面版本
     *
     * @param pageId    页面ID
     * @param version   版本号
     * @param editorId  编辑者ID
     * @return 恢复后的页面视图
     */
    PageVO restoreVersion(Long pageId, Integer version, Long editorId);

    /**
     * 增加浏览次数
     *
     * @param id 页面ID
     */
    void incrementViewCount(Long id);

    /**
     * 点赞/取消点赞页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @return 当前点赞状态
     */
    boolean toggleLike(Long id, Long userId);

    /**
     * 收藏/取消收藏页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @return 当前收藏状态
     */
    boolean toggleFavorite(Long id, Long userId);

    /**
     * 关注/取消关注页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @param watchType 关注类型
     * @return 当前关注状态
     */
    boolean toggleWatch(Long id, Long userId, String watchType);

    /**
     * 检查用户是否收藏了页面
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    boolean isFavorited(Long pageId, Long userId);

    /**
     * 检查用户是否关注了页面
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否关注
     */
    boolean isWatched(Long pageId, Long userId);

    /**
     * 搜索页面
     *
     * @param wikiId   知识库ID
     * @param keyword  关键词
     * @param pageable 分页参数
     * @return 页面分页列表
     */
    org.springframework.data.domain.Page<PageVO> searchPages(Long wikiId, String keyword, Pageable pageable);

    /**
     * 根据ID获取页面实体
     *
     * @param id 页面ID
     * @return 页面实体
     */
    Page findById(Long id);
}
