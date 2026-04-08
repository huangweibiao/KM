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
    public List List<CommentVO> getCommentsByPageId(Long pageId, Pageable pageable) {
        // 获取所有非删除的评论
        List List<Comment> comments = commentRepository.findByPageIdAndIsDeletedOrderByCreatedAtDesc(pageId, 0);

        // 构建树形结构
        return buildCommentTree(comments);
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
     * 构建评论树形结构
     *
     * @param comments 评论列表
     * @return 树形结构的评论列表
     */
    private List List<CommentVO> buildCommentTree(List(List<Comment> comments) {
        // 按ID分组
        Map<Long, CommentVO> commentMap = new HashMap<>();
        List List<CommentVO> rootComments = new ArrayList<>();

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
