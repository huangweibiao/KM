package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.dto.PageDTO;
import com.km.wiki.service.PageService;
import com.km.wiki.vo.PageTreeVO;
import com.km.wiki.vo.PageVO;
import com.km.wiki.vo.PageVersionVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面控制器
 * 处理页面相关的HTTP请求
 */
@RestController
@RequestMapping("/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    /**
     * 获取知识库的页面列表（树形结构）
     *
     * @param wikiId 知识库ID
     * @return 页面树形列表
     */
    @GetMapping("/wikis/{wikiId}/pages")
    public Result<List<PageTreeVO>> getPageTree(@PathVariable Long wikiId) {
        List<PageTreeVO> pages = pageService.getPageTree(wikiId);
        return Result.success(pages);
    }

    /**
     * 创建页面
     *
     * @param wikiId  知识库ID
     * @param request 页面创建请求
     * @param auth    认证信息
     * @return 创建的页面
     */
    @PostMapping("/wikis/{wikiId}/pages")
    public Result<PageVO> createPage(
            @PathVariable Long wikiId,
            @Valid @RequestBody PageDTO.CreateRequest request,
            Authentication auth) {
        Long userId = getCurrentUserId(auth);
        PageVO page = pageService.createPage(wikiId, request, userId);
        return Result.success(page);
    }

    /**
     * 获取页面详情
     *
     * @param id 页面ID
     * @return 页面详情
     */
    @GetMapping("/pages/{id}")
    public Result<PageVO> getPage(@PathVariable Long id) {
        PageVO page = pageService.getPageById(id);
        return Result.success(page);
    }

    /**
     * 更新页面
     *
     * @param id      页面ID
     * @param request 页面更新请求
     * @param auth    认证信息
     * @return 更新后的页面
     */
    @PutMapping("/pages/{id}")
    public Result<PageVO> updatePage(
            @PathVariable Long id,
            @Valid @RequestBody PageDTO.UpdateRequest request,
            Authentication auth) {
        Long userId = getCurrentUserId(auth);
        PageVO page = pageService.updatePage(id, request, userId);
        return Result.success(page);
    }

    /**
     * 删除页面（软删除）
     *
     * @param id   页面ID
     * @param auth 认证信息
     * @return 操作结果
     */
    @DeleteMapping("/pages/{id}")
    public Result<Void> deletePage(@PathVariable Long id, Authentication auth) {
        Long userId = getCurrentUserId(auth);
        pageService.deletePage(id, userId);
        return Result.success();
    }

    /**
     * 获取页面版本历史
     *
     * @param id       页面ID
     * @param page     页码
     * @param pageSize 每页大小
     * @return 版本列表
     */
    @GetMapping("/pages/{id}/versions")
    public Result<List<PageVersionVO>> getPageVersions(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("version").descending());
        List<PageVersionVO> versions = pageService.getPageVersions(id, pageable);
        return Result.success(versions);
    }

    /**
     * 恢复页面版本
     *
     * @param id      页面ID
     * @param version 版本号
     * @param auth    认证信息
     * @return 恢复后的页面
     */
    @PostMapping("/pages/{id}/versions/{version}/restore")
    public Result<PageVO> restoreVersion(
            @PathVariable Long id,
            @PathVariable Integer version,
            Authentication auth) {
        Long userId = getCurrentUserId(auth);
        PageVO page = pageService.restoreVersion(id, version, userId);
        return Result.success(page);
    }

    /**
     * 收藏/取消收藏页面
     *
     * @param id   页面ID
     * @param auth 认证信息
     * @return 操作结果
     */
    @PostMapping("/pages/{id}/favorite")
    public Result<Boolean> toggleFavorite(@PathVariable Long id, Authentication auth) {
        Long userId = getCurrentUserId(auth);
        boolean isFavorited = pageService.toggleFavorite(id, userId);
        return Result.success(isFavorited);
    }

    /**
     * 关注/取消关注页面
     *
     * @param id      页面ID
     * @param request 关注请求
     * @param auth    认证信息
     * @return 操作结果
     */
    @PostMapping("/pages/{id}/watch")
    public Result<Boolean> toggleWatch(
            @PathVariable Long id,
            @RequestBody PageDTO.WatchRequest request,
            Authentication auth) {
        Long userId = getCurrentUserId(auth);
        boolean isWatching = pageService.toggleWatch(id, userId, request.getWatchType());
        return Result.success(isWatching);
    }

    /**
     * 从认证信息中获取当前用户ID
     *
     * @param auth 认证信息
     * @return 用户ID
     */
    private Long getCurrentUserId(Authentication auth) {
        if (auth == null) {
            throw new RuntimeException("用户未登录");
        }
        return Long.valueOf(auth.getName());
    }
}
