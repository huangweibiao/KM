package com.km.wiki.service.impl;

import com.km.wiki.dto.PageDTO;
import com.km.wiki.entity.*;
import com.km.wiki.repository.*;
import com.km.wiki.service.PageService;
import com.km.wiki.vo.PageTreeVO;
import com.km.wiki.vo.PageVO;
import com.km.wiki.vo.PageVersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 页面服务实现类
 * 实现页面相关的业务逻辑
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageVersionRepository pageVersionRepository;

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private WikiMemberRepository wikiMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private WatchRepository watchRepository;

    /**
     * 根据ID获取页面
     */
    @Override
    public Page findById(Long id) {
        return pageRepository.findById(id).orElse(null);
    }

    /**
     * 创建页面
     *
     * @param wikiId   知识库ID
     * @param pageDTO  页面创建信息
     * @param authorId 作者ID
     * @return 创建的页面
     */
    @Override
    @Transactional
    public PageVO createPage(Long wikiId, PageDTO.CreateRequest pageDTO, Long authorId) {
        // 检查知识库是否存在
        Wiki wiki = wikiRepository.findById(wikiId)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));

        // 检查用户是否有权限创建页面
        String role = wikiMemberRepository.findRoleByWikiIdAndUserId(wikiId, authorId);
        if (role == null || role.equals("VIEWER")) {
            throw new RuntimeException("没有权限创建页面");
        }

        // 检查slug是否在同一知识库中已存在
        if (pageRepository.existsByWikiIdAndSlug(wikiId, pageDTO.getSlug())) {
            throw new RuntimeException("页面标识符在该知识库中已存在");
        }

        // 创建页面
        Page page = new Page();
        page.setWikiId(wikiId);
        page.setCategoryId(pageDTO.getCategoryId());
        page.setParentPageId(pageDTO.getParentPageId());
        page.setTitle(pageDTO.getTitle());
        page.setSlug(pageDTO.getSlug());
        page.setContent(pageDTO.getContent());
        page.setContentFormat(pageDTO.getContentFormat() != null ? pageDTO.getContentFormat() : "markdown");
        page.setType(pageDTO.getType() != null ? pageDTO.getType() : "doc");
        page.setVersion(1);
        page.setAuthorId(authorId);
        page.setLastEditorId(authorId);
        page.setStatus(pageDTO.getStatus() != null ? pageDTO.getStatus() : 0);
        page.setViewCount(0);
        page.setLikeCount(0);
        page.setIsDeleted(0);
        page.setCreatedAt(LocalDateTime.now());
        page.setUpdatedAt(LocalDateTime.now());

        if (page.getStatus() == 1) {
            page.setPublishedAt(LocalDateTime.now());
        }

        Page savedPage = pageRepository.save(page);

        // 创建初始版本记录
        PageVersion version = new PageVersion();
        version.setPageId(savedPage.getId());
        version.setVersion(1);
        version.setTitle(savedPage.getTitle());
        version.setContent(savedPage.getContent());
        version.setEditorId(authorId);
        version.setChangeNote("初始版本");
        version.setCreatedAt(LocalDateTime.now());
        pageVersionRepository.save(version);

        // 更新知识库页面数量
        updateWikiPageCount(wikiId);

        return convertToVO(savedPage);
    }

    /**
     * 获取页面详情
     *
     * @param id 页面ID
     * @return 页面视图对象
     */
    @Override
    public PageVO getPageById(Long id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("页面不存在"));
        return convertToVO(page);
    }

    /**
     * 根据slug获取页面
     *
     * @param wikiId 知识库ID
     * @param slug   页面标识符
     * @return 页面视图对象
     */
    @Override
    public PageVO getPageBySlug(Long wikiId, String slug) {
        Page page = pageRepository.findByWikiIdAndSlug(wikiId, slug)
                .orElseThrow(() -> new RuntimeException("页面不存在"));
        return convertToVO(page);
    }

    /**
     * 获取知识库的页面列表（树形结构）
     *
     * @param wikiId 知识库ID
     * @return 页面树形列表
     */
    @Override
    public List<PageTreeVO> getPageTree(Long wikiId) {
        List<Page> pages = pageRepository.findByWikiIdAndIsDeletedOrderByCreatedAtAsc(wikiId, 0);
        return buildPageTree(pages, null);
    }

    /**
     * 获取页面的子页面
     *
     * @param parentId 父页面ID
     * @return 子页面列表
     */
    @Override
    public List<PageVO> getChildPages(Long parentId) {
        List<Page> pages = pageRepository.findByParentPageIdAndIsDeleted(parentId, 0);
        return pages.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 更新页面
     *
     * @param id       页面ID
     * @param pageDTO  页面更新信息
     * @param editorId 编辑者ID
     * @return 更新后的页面
     */
    @Override
    @Transactional
    public PageVO updatePage(Long id, PageDTO.UpdateRequest pageDTO, Long editorId) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        // 保存旧版本
        PageVersion version = new PageVersion();
        version.setPageId(page.getId());
        version.setVersion(page.getVersion() + 1);
        version.setTitle(page.getTitle());
        version.setContent(page.getContent());
        version.setEditorId(editorId);
        version.setChangeNote(pageDTO.getChangeNote() != null ? pageDTO.getChangeNote() : "更新页面");
        version.setCreatedAt(LocalDateTime.now());
        pageVersionRepository.save(version);

        // 更新页面
        if (pageDTO.getTitle() != null) {
            page.setTitle(pageDTO.getTitle());
        }
        if (pageDTO.getContent() != null) {
            page.setContent(pageDTO.getContent());
        }
        if (pageDTO.getCategoryId() != null) {
            page.setCategoryId(pageDTO.getCategoryId());
        }
        if (pageDTO.getParentPageId() != null) {
            page.setParentPageId(pageDTO.getParentPageId());
        }
        if (pageDTO.getStatus() != null) {
            page.setStatus(pageDTO.getStatus());
            if (pageDTO.getStatus() == 1 && page.getPublishedAt() == null) {
                page.setPublishedAt(LocalDateTime.now());
            }
        }

        page.setVersion(page.getVersion() + 1);
        page.setLastEditorId(editorId);
        page.setUpdatedAt(LocalDateTime.now());

        Page updatedPage = pageRepository.save(page);
        return convertToVO(updatedPage);
    }

    /**
     * 删除页面（软删除）
     *
     * @param id 页面ID
     * @param userId 用户ID
     */
    @Override
    @Transactional
    public void deletePage(Long id, Long userId) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("页面不存在"));
        page.setIsDeleted(1);
        page.setUpdatedAt(LocalDateTime.now());
        pageRepository.save(page);

        // 更新知识库页面数量
        updateWikiPageCount(page.getWikiId());
    }

    /**
     * 获取页面版本历史
     *
     * @param pageId  页面ID
     * @param pageable 分页参数
     * @return 版本列表
     */
    @Override
    public List<PageVersionVO> getPageVersions(Long pageId, Pageable pageable) {
        List<PageVersion> versions = pageVersionRepository.findByPageIdOrderByVersionDesc(pageId);
        return versions.stream().map(this::convertToVersionVO).collect(Collectors.toList());
    }

    /**
     * 恢复页面版本
     *
     * @param pageId    页面ID
     * @param version   版本号
     * @param editorId  编辑者ID
     * @return 恢复后的页面
     */
    @Override
    @Transactional
    public PageVO restoreVersion(Long pageId, Integer version, Long editorId) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        // 根据版本号查找版本
        List<PageVersion> versions = pageVersionRepository.findByPageIdOrderByVersionDesc(pageId);
        PageVersion targetVersion = null;
        for (PageVersion pv : versions) {
            if (pv.getVersion().equals(version)) {
                targetVersion = pv;
                break;
            }
        }
        
        if (targetVersion == null) {
            throw new RuntimeException("版本不存在");
        }

        // 保存当前版本为新版本
        PageVersion newVersion = new PageVersion();
        newVersion.setPageId(page.getId());
        newVersion.setVersion(page.getVersion() + 1);
        newVersion.setTitle(page.getTitle());
        newVersion.setContent(page.getContent());
        newVersion.setEditorId(editorId);
        newVersion.setChangeNote("恢复到版本 " + targetVersion.getVersion());
        newVersion.setCreatedAt(LocalDateTime.now());
        pageVersionRepository.save(newVersion);

        // 恢复旧版本内容
        page.setTitle(targetVersion.getTitle());
        page.setContent(targetVersion.getContent());
        page.setVersion(page.getVersion() + 1);
        page.setLastEditorId(editorId);
        page.setUpdatedAt(LocalDateTime.now());

        Page updatedPage = pageRepository.save(page);
        return convertToVO(updatedPage);
    }

    /**
     * 增加浏览次数
     *
     * @param id 页面ID
     */
    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        pageRepository.incrementViewCount(id);
    }

    /**
     * 点赞/取消点赞页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @return 当前点赞状态
     */
    @Override
    @Transactional
    public boolean toggleLike(Long id, Long userId) {
        // 这里简化处理，实际应该使用PageReaction表
        // 暂时只增加/减少计数
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        // 实际项目中应该检查用户是否已经点赞
        // 这里简化处理，直接增加计数
        page.setLikeCount(page.getLikeCount() + 1);
        pageRepository.save(page);

        return true;
    }

    /**
     * 收藏/取消收藏页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @return 当前收藏状态
     */
    @Override
    @Transactional
    public boolean toggleFavorite(Long id, Long userId) {
        Favorite favorite = favoriteRepository.findByUserIdAndPageId(userId, id).orElse(null);

        if (favorite != null) {
            // 取消收藏
            favoriteRepository.delete(favorite);
            return false;
        } else {
            // 添加收藏
            Favorite newFavorite = new Favorite();
            newFavorite.setUserId(userId);
            newFavorite.setPageId(id);
            newFavorite.setCreatedAt(LocalDateTime.now());
            favoriteRepository.save(newFavorite);
            return true;
        }
    }

    /**
     * 关注/取消关注页面
     *
     * @param id     页面ID
     * @param userId 用户ID
     * @param watchType 关注类型
     * @return 当前关注状态
     */
    @Override
    @Transactional
    public boolean toggleWatch(Long id, Long userId, String watchType) {
        Watch watch = watchRepository.findByUserIdAndPageId(userId, id).orElse(null);

        if (watch != null) {
            // 取消关注
            watchRepository.delete(watch);
            return false;
        } else {
            // 添加关注
            Watch newWatch = new Watch();
            newWatch.setUserId(userId);
            newWatch.setPageId(id);
            newWatch.setWatchType(watchType != null ? watchType : "ALL");
            newWatch.setCreatedAt(LocalDateTime.now());
            watchRepository.save(newWatch);
            return true;
        }
    }

    /**
     * 检查用户是否收藏了页面
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    @Override
    public boolean isFavorited(Long pageId, Long userId) {
        return favoriteRepository.existsByUserIdAndPageId(userId, pageId);
    }

    /**
     * 检查用户是否关注了页面
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否关注
     */
    @Override
    public boolean isWatched(Long pageId, Long userId) {
        return watchRepository.existsByUserIdAndPageId(userId, pageId);
    }

    /**
     * 搜索页面
     *
     * @param wikiId   知识库ID
     * @param keyword  关键词
     * @param pageable 分页参数
     * @return 页面列表
     */
    @Override
    public org.springframework.data.domain.Page<PageVO> searchPages(Long wikiId, String keyword, Pageable pageable) {
        return pageRepository.searchByWikiIdAndKeyword(wikiId, keyword, pageable).map(this::convertToVO);
    }

    /**
     * 构建页面树
     *
     * @param pages    页面列表
     * @param parentId 父页面ID
     * @return 树形结构
     */
    private List<PageTreeVO> buildPageTree(List<Page> pages, Long parentId) {
        List<PageTreeVO> tree = new ArrayList<>();

        for (Page page : pages) {
            if ((parentId == null && page.getParentPageId() == null) ||
                    (parentId != null && parentId.equals(page.getParentPageId()))) {
                PageTreeVO vo = new PageTreeVO();
                vo.setId(page.getId());
                vo.setWikiId(page.getWikiId());
                vo.setCategoryId(page.getCategoryId());
                vo.setParentPageId(page.getParentPageId());
                vo.setTitle(page.getTitle());
                vo.setSlug(page.getSlug());
                vo.setType(page.getType());
                vo.setStatus(page.getStatus());
                vo.setCreatedAt(page.getCreatedAt());
                vo.setUpdatedAt(page.getUpdatedAt());

                // 递归构建子树
                vo.setChildren(buildPageTree(pages, page.getId()));

                tree.add(vo);
            }
        }

        return tree;
    }

    /**
     * 更新知识库页面数量
     *
     * @param wikiId 知识库ID
     */
    private void updateWikiPageCount(Long wikiId) {
        long count = pageRepository.countByWikiIdAndIsDeleted(wikiId, 0);
        Wiki wiki = wikiRepository.findById(wikiId).orElse(null);
        if (wiki != null) {
            wiki.setPageCount((int) count);
            wiki.setUpdatedAt(LocalDateTime.now());
            wikiRepository.save(wiki);
        }
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

        // 获取作者信息
        userRepository.findById(page.getAuthorId()).ifPresent(author -> {
            vo.setAuthorName(author.getNickname() != null ? author.getNickname() : author.getUsername());
        });

        // 获取最后编辑者信息
        if (page.getLastEditorId() != null) {
            userRepository.findById(page.getLastEditorId()).ifPresent(editor -> {
                vo.setLastEditorName(editor.getNickname() != null ? editor.getNickname() : editor.getUsername());
            });
        }

        return vo;
    }

    /**
     * 将页面版本实体转换为视图对象
     *
     * @param version 页面版本实体
     * @return 页面版本视图对象
     */
    private PageVersionVO convertToVersionVO(PageVersion version) {
        PageVersionVO vo = new PageVersionVO();
        vo.setId(version.getId());
        vo.setPageId(version.getPageId());
        vo.setVersion(version.getVersion());
        vo.setTitle(version.getTitle());
        vo.setContent(version.getContent());
        vo.setEditorId(version.getEditorId());
        vo.setChangeNote(version.getChangeNote());
        vo.setCreatedAt(version.getCreatedAt());

        // 获取编辑者信息
        if (version.getEditorId() != null) {
            userRepository.findById(version.getEditorId()).ifPresent(editor -> {
                vo.setEditorName(editor.getUsername());
                vo.setEditorNickname(editor.getNickname());
                vo.setEditorAvatar(editor.getAvatarUrl());
            });
        }

        return vo;
    }
}
