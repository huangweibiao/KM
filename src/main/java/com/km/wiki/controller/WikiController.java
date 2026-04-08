package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.dto.WikiDTO;
import com.km.wiki.service.WikiService;
import com.km.wiki.vo.WikiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库控制器
 * 处理知识库相关的HTTP请求
 */
@RestController
@RequestMapping("/api/wikis")
public class WikiController {

    @Autowired
    private WikiService wikiService;

    /**
     * 获取知识库列表
     *
     * @param pageNum  页码，默认1
     * @param pageSize 每页数量，默认20
     * @return 知识库分页列表
     */
    @GetMapping
    public Result Result<Page<Page<WikiVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("createdAt").descending());
        Page Page<WikiVO> page = wikiService.getWikiList(pageable);
        return Result.success(page);
    }

    /**
     * 获取知识库详情
     *
     * @param id 知识库ID
     * @return 知识库详情
     */
    @GetMapping("/{id}")
    public Result Result<WikiVO> getById(@PathVariable Long id) {
        WikiVO wiki = wikiService.getWikiById(id);
        return Result.success(wiki);
    }

    /**
     * 创建知识库
     *
     * @param request 创建请求
     * @return 创建结果
     */
    @PostMapping
    public Result Result<WikiVO> create(@RequestBody WikiDTO.CreateRequest request) {
        // 获取当前用户ID
        Long userId = getCurrentUserId();
        WikiVO wiki = wikiService.createWiki(request, userId);
        return Result.success(wiki);
    }

    /**
     * 更新知识库
     *
     * @param id      知识库ID
     * @param request 更新请求
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result Result<WikiVO> update(@PathVariable Long id, @RequestBody WikiDTO.UpdateRequest request) {
        WikiVO wiki = wikiService.updateWiki(id, request);
        return Result.success(wiki);
    }

    /**
     * 归档知识库
     *
     * @param id 知识库ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> archive(@PathVariable Long id) {
        wikiService.archiveWiki(id);
        return Result.success();
    }

    /**
     * 获取知识库成员列表
     *
     * @param id 知识库ID
     * @return 成员列表
     */
    @GetMapping("/{id}/members")
    public Result<List<List<WikiVO.MemberVO>> getMembers(@PathVariable Long id) {
        List List<WikiVO.MemberVO> members = wikiService.getWikiMembers(id);
        return Result.success(members);
    }

    /**
     * 添加知识库成员
     *
     * @param id      知识库ID
     * @param request 添加成员请求
     * @return 操作结果
     */
    @PostMapping("/{id}/members")
    public Result<Void> addMember(@PathVariable Long id, @RequestBody WikiDTO.AddMemberRequest request) {
        wikiService.addWikiMember(id, request.getUserId(), request.getRole());
        return Result.success();
    }

    /**
     * 移除知识库成员
     *
     * @param id     知识库ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}/members/{userId}")
    public Result<Void> removeMember(@PathVariable Long id, @PathVariable Long userId) {
        wikiService.removeWikiMember(id, userId);
        return Result.success();
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.valueOf(authentication.getName());
        }
        return null;
    }
}
