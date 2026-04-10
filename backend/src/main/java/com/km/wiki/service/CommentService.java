package com.km.wiki.service;

import com.km.wiki.dto.CommentDTO;
import com.km.wiki.entity.Comment;
import com.km.wiki.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 评论服务接口
 * 定义评论相关的业务逻辑操作
 */
public interface CommentService {

    /**
     * 获取页面的评论列表（树形结构）
     *
     * @param pageId   页面ID
     * @param pageable 分页参数
     * @return 评论树形列表
     */
    List<CommentVO> getCommentsByPageId(Long pageId, Pageable pageable);

    /**
     * 获取页面的评论列表（兼容旧调用）
     *
     * @param pageId 页面ID
     * @return 评论列表
     */
    List<CommentVO> getCommentsByPageId(Long pageId);

    /**
     * 发表评论
     *
     * @param pageId     页面ID
     * @param userId     用户ID
     * @param commentDTO 评论信息
     * @return 发表的评论视图
     */
    CommentVO createComment(Long pageId, Long userId, CommentDTO.CreateRequest commentDTO);

    /**
     * 编辑评论
     *
     * @param commentId  评论ID
     * @param userId     用户ID
     * @param commentDTO 评论更新信息
     * @return 更新后的评论视图
     */
    CommentVO updateComment(Long commentId, Long userId, CommentDTO.UpdateRequest commentDTO);

    /**
     * 删除评论（逻辑删除）
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     */
    void deleteComment(Long commentId, Long userId);

    /**
     * 根据ID获取评论
     *
     * @param id 评论ID
     * @return 评论实体
     */
    Comment findById(Long id);

    /**
     * 获取页面的评论列表（分页）
     *
     * @param pageId   页面ID
     * @param pageable 分页参数
     * @return 评论分页数据
     */
    Page<CommentVO> getPageComments(Long pageId, Pageable pageable);

    /**
     * 获取页面的评论树形列表
     *
     * @param pageId 页面ID
     * @return 评论树形列表
     */
    List<CommentVO> getPageCommentTree(Long pageId);

    /**
     * 获取评论详情
     *
     * @param id 评论ID
     * @return 评论视图对象
     */
    CommentVO getCommentDetail(Long id);

    /**
     * 点赞评论
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     * @return 点赞数
     */
    Integer likeComment(Long commentId, Long userId);

    /**
     * 取消点赞评论
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     * @return 点赞数
     */
    Integer unlikeComment(Long commentId, Long userId);

    /**
     * 回复评论
     *
     * @param pageId     页面ID
     * @param parentId   父评论ID
     * @param commentDTO 回复信息
     * @return 回复的评论视图
     */
    CommentVO replyComment(Long pageId, Long parentId, CommentDTO.CreateRequest commentDTO);

    /**
     * 获取评论的子评论列表
     *
     * @param parentId 父评论ID
     * @param pageable 分页参数
     * @return 子评论分页数据
     */
    Page<CommentVO> getChildComments(Long parentId, Pageable pageable);

    /**
     * 获取用户的评论列表
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 评论分页数据
     */
    Page<CommentVO> getUserComments(Long userId, Pageable pageable);

    /**
     * 批量删除评论
     *
     * @param ids     评论ID列表
     * @param userId  操作用户ID
     */
    void batchDeleteComments(List<Long> ids, Long userId);

    /**
     * 检查用户是否可以评论
     *
     * @param pageId 页面ID
     * @param userId 用户ID
     * @return 是否可以评论
     */
    boolean canComment(Long pageId, Long userId);

    /**
     * 获取页面评论统计信息
     *
     * @param pageId 页面ID
     * @return 统计信息（评论数量等）
     */
    Map<String, Object> getCommentStats(Long pageId);

    /**
     * 隐藏评论（管理员操作）
     *
     * @param id 评论ID
     */
    void hideComment(Long id);

    /**
     * 显示评论（管理员操作）
     *
     * @param id 评论ID
     */
    void showComment(Long id);

    /**
     * 获取所有评论（管理员）
     *
     * @param pageable 分页参数
     * @return 评论分页数据
     */
    Page<CommentVO> getAllComments(Pageable pageable);
}
