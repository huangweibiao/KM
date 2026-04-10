package com.km.wiki.service.impl;

import com.km.wiki.dto.CommentDTO;
import com.km.wiki.entity.Comment;
import com.km.wiki.entity.Page;
import com.km.wiki.repository.CommentRepository;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.service.CommentService;
import com.km.wiki.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 * 实现评论相关的业务逻辑
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 获取页面的评论列表（树形结构）
     *
     * @param pageId   页面ID
     * @param pageable 分页参数
     * @return 评论树形列表
     */
    @Override
    public List<CommentVO> getCommentsByPageId(Long pageId, Pageable pageable) {
        // 获取所有非删除的评论
        List<Comment> comments = commentRepository.findByPageIdAndIsDeletedOrderByCreatedAtDesc(pageId, 0);

        // 构建树形结构
        return buildCommentTree(comments);
    }

    /**
     * 获取页面的评论列表（兼容旧调用）
     *
     * @param pageId 页面ID
     * @return 评论列表
     */
    @Override
    public List<CommentVO> getCommentsByPageId(Long pageId) {
        return getCommentsByPageId(pageId, null);
    }

    /**
     * 发表评论
     *
     * @param pageId     页面ID
     * @param userId     用户ID
     * @param commentDTO 评论信息
     * @return 发表的评论
     */
    @Override
    @Transactional
    public CommentVO createComment(Long pageId, Long userId, CommentDTO.CreateRequest commentDTO) {
        // 检查页面是否存在
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        Comment comment = new Comment();
        comment.setPageId(pageId);
        comment.setUserId(userId);
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0);
        comment.setIsDeleted(0);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return convertToVO(savedComment);
    }

    /**
     * 编辑评论
     *
     * @param commentId  评论ID
     * @param userId     用户ID
     * @param commentDTO 评论更新信息
     * @return 更新后的评论
     */
    @Override
    @Transactional
    public CommentVO updateComment(Long commentId, Long userId, CommentDTO.UpdateRequest commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));

        // 检查是否是评论作者
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("无权编辑此评论");
        }

        comment.setContent(commentDTO.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(comment);

        return convertToVO(updatedComment);
    }

    /**
     * 删除评论（逻辑删除）
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     */
    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));

        // 检查是否是评论作者
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("无权删除此评论");
        }

        comment.setIsDeleted(1);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    /**
     * 根据ID获取评论
     */
    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    /**
     * 获取页面的评论列表（分页）
     */
    @Override
    public org.springframework.data.domain.Page<CommentVO> getPageComments(Long pageId, Pageable pageable) {
        return commentRepository.findByPageIdAndIsDeleted(pageId, 0, pageable)
                .map(this::convertToVO);
    }

    /**
     * 获取页面的评论树形列表
     */
    @Override
    public List<CommentVO> getPageCommentTree(Long pageId) {
        return getCommentsByPageId(pageId);
    }

    /**
     * 获取评论详情
     */
    @Override
    public CommentVO getCommentDetail(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("评论不存在"));
        return convertToVO(comment);
    }

    /**
     * 点赞评论
     */
    @Override
    @Transactional
    public Integer likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
        return comment.getLikeCount();
    }

    /**
     * 取消点赞评论
     */
    @Override
    @Transactional
    public Integer unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));
        comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));
        commentRepository.save(comment);
        return comment.getLikeCount();
    }

    /**
     * 回复评论
     */
    @Override
    @Transactional
    public CommentVO replyComment(Long pageId, Long parentId, CommentDTO.CreateRequest commentDTO) {
        CommentDTO.CreateRequest request = new CommentDTO.CreateRequest();
        request.setContent(commentDTO.getContent());
        request.setParentId(parentId);
        return createComment(pageId, null, request);
    }

    /**
     * 获取评论的子评论列表
     */
    @Override
    public org.springframework.data.domain.Page<CommentVO> getChildComments(Long parentId, Pageable pageable) {
        List<Comment> comments = commentRepository.findByParentIdAndIsDeletedOrderByCreatedAtAsc(parentId, 0);
        return new org.springframework.data.domain.PageImpl<>(
                comments.stream().map(this::convertToVO).collect(Collectors.toList()),
                pageable, comments.size());
    }

    /**
     * 获取用户的评论列表
     */
    @Override
    public org.springframework.data.domain.Page<CommentVO> getUserComments(Long userId, Pageable pageable) {
        return commentRepository.findByUserIdAndIsDeletedOrderByCreatedAtDesc(userId, 0, pageable)
                .map(this::convertToVO);
    }

    /**
     * 批量删除评论
     */
    @Override
    @Transactional
    public void batchDeleteComments(List<Long> ids, Long userId) {
        for (Long id : ids) {
            deleteComment(id, userId);
        }
    }

    /**
     * 检查用户是否可以评论
     */
    @Override
    public boolean canComment(Long pageId, Long userId) {
        return true; // 简化实现
    }

    /**
     * 获取页面评论统计信息
     */
    @Override
    public Map<String, Object> getCommentStats(Long pageId) {
        Map<String, Object> stats = new HashMap<>();
        List<Comment> comments = commentRepository.findByPageIdAndIsDeletedOrderByCreatedAtDesc(pageId, 0);
        stats.put("totalCount", comments.size());
        return stats;
    }

    /**
     * 隐藏评论（管理员操作）
     */
    @Override
    @Transactional
    public void hideComment(Long id) {
        // 简化实现
    }

    /**
     * 显示评论（管理员操作）
     */
    @Override
    @Transactional
    public void showComment(Long id) {
        // 简化实现
    }

    /**
     * 获取所有评论（管理员）
     */
    @Override
    public org.springframework.data.domain.Page<CommentVO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(this::convertToVO);
    }

    /**
     * 构建评论树形结构
     *
     * @param comments 评论列表
     * @return 树形结构的评论列表
     */
    private List<CommentVO> buildCommentTree(List<Comment> comments) {
        // 按ID分组
        Map<Long, CommentVO> commentMap = new HashMap<>();
        List<CommentVO> rootComments = new ArrayList<>();

        // 先转换为VO并放入Map
        for (Comment comment : comments) {
            CommentVO vo = convertToVO(comment);
            vo.setChildren(new ArrayList<>());
            commentMap.put(comment.getId(), vo);
        }

        // 构建树形结构
        for (Comment comment : comments) {
            CommentVO vo = commentMap.get(comment.getId());
            if (comment.getParentId() == null) {
                // 根评论
                rootComments.add(vo);
            } else {
                // 子评论
                CommentVO parent = commentMap.get(comment.getParentId());
                if (parent != null) {
                    parent.getChildren().add(vo);
                } else {
                    // 父评论不存在，作为根评论处理
                    rootComments.add(vo);
                }
            }
        }

        return rootComments;
    }

    /**
     * 将评论实体转换为视图对象
     *
     * @param comment 评论实体
     * @return 评论视图对象
     */
    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setPageId(comment.getPageId());
        vo.setUserId(comment.getUserId());
        vo.setParentId(comment.getParentId());
        vo.setContent(comment.getContent());
        vo.setLikeCount(comment.getLikeCount());
        vo.setCreatedAt(comment.getCreatedAt());
        vo.setUpdatedAt(comment.getUpdatedAt());
        return vo;
    }
}
