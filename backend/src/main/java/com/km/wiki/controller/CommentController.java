package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.dto.CommentDTO;
import com.km.wiki.security.CustomUserDetails;
import com.km.wiki.service.CommentService;
import com.km.wiki.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 * 处理评论相关的HTTP请求
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取页面的评论列表
     *
     * @param pageId 页面ID
     * @return 评论列表
     */
    @GetMapping("/pages/{pageId}/comments")
    public Result<List<CommentVO>> getCommentsByPageId(@PathVariable Long pageId) {
        List<CommentVO> comments = commentService.getCommentsByPageId(pageId);
        return Result.success(comments);
    }

    /**
     * 发表评论
     *
     * @param pageId      页面ID
     * @param request     评论请求
     * @param userDetails 当前登录用户
     * @return 发表的评论
     */
    @PostMapping("/pages/{pageId}/comments")
    public Result<CommentVO> createComment(
            @PathVariable Long pageId,
            @RequestBody CommentDTO.CreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentVO comment = commentService.createComment(pageId, userDetails.getId(), request);
        return Result.success(comment);
    }

    /**
     * 回复评论
     *
     * @param parentId    父评论ID
     * @param request     评论请求
     * @param userDetails 当前登录用户
     * @return 发表的评论
     */
    @PostMapping("/comments/{parentId}/reply")
    public Result<CommentVO> replyComment(
            @PathVariable Long parentId,
            @RequestBody CommentDTO.CreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentVO comment = commentService.replyComment(parentId, userDetails.getId(), request);
        return Result.success(comment);
    }

    /**
     * 更新评论
     *
     * @param id          评论ID
     * @param request     更新请求
     * @param userDetails 当前登录用户
     * @return 更新后的评论
     */
    @PutMapping("/comments/{id}")
    public Result<CommentVO> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDTO.UpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentVO comment = commentService.updateComment(id, userDetails.getId(), request);
        return Result.success(comment);
    }

    /**
     * 删除评论
     *
     * @param id          评论ID
     * @param userDetails 当前登录用户
     * @return 操作结果
     */
    @DeleteMapping("/comments/{id}")
    public Result<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.deleteComment(id, userDetails.getId());
        return Result.success();
    }
}
