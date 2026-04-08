package com.km.wiki.service.impl;

import com.km.wiki.dto.WikiDTO;
import com.km.wiki.entity.*;
import com.km.wiki.repository.*;
import com.km.wiki.service.WikiService;
import com.km.wiki.vo.WikiMemberVO;
import com.km.wiki.vo.WikiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识库服务实现类
 * 实现知识库相关的业务逻辑
 */
@Service
public class WikiServiceImpl implements WikiService {

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private WikiMemberRepository wikiMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 创建知识库
     *
     * @param wikiDTO 知识库创建信息
     * @param ownerId 创建者ID
     * @return 创建的知识库
     */
    @Override
    @Transactional
    public WikiVO createWiki(WikiDTO.CreateRequest wikiDTO, Long ownerId) {
        // 检查slug是否已存在
        if (wikiRepository.existsBySlug(wikiDTO.getSlug())) {
            throw new RuntimeException("知识库标识符已存在");
        }

        // 创建知识库
        Wiki wiki = new Wiki();
        wiki.setName(wikiDTO.getName());
        wiki.setSlug(wikiDTO.getSlug());
        wiki.setDescription(wikiDTO.getDescription());
        wiki.setLogoUrl(wikiDTO.getLogoUrl());
        wiki.setOwnerId(ownerId);
        wiki.setVisibility(wikiDTO.getVisibility() != null ? wikiDTO.getVisibility() : 1);
        wiki.setIsArchived(0);
        wiki.setPageCount(0);
        wiki.setCreatedAt(LocalDateTime.now());
        wiki.setUpdatedAt(LocalDateTime.now());

        Wiki savedWiki = wikiRepository.save(wiki);

        // 创建者自动成为OWNER成员
        WikiMember member = new WikiMember();
        member.setWikiId(savedWiki.getId());
        member.setUserId(ownerId);
        member.setRoleInWiki("OWNER");
        member.setCreatedAt(LocalDateTime.now());
        wikiMemberRepository.save(member);

        return convertToVO(savedWiki);
    }

    /**
     * 获取知识库详情
     *
     * @param id 知识库ID
     * @return 知识库视图对象
     */
    @Override
    public WikiVO getWikiById(Long id) {
        Wiki wiki = wikiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));
        return convertToVO(wiki);
    }

    /**
     * 根据slug获取知识库
     *
     * @param slug 知识库标识符
     * @return 知识库视图对象
     */
    @Override
    public WikiVO getWikiBySlug(String slug) {
        Wiki wiki = wikiRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));
        return convertToVO(wiki);
    }

    /**
     * 获取知识库列表
     *
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    @Override
    public Page Page<WikiVO> getWikiList(Pageable pageable) {
        return wikiRepository.findByIsArchived(0, pageable).map(this::convertToVO);
    }

    /**
     * 获取用户的知识库列表
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    @Override
    public Page Page<WikiVO> getUserWikis(Long userId, Pageable pageable) {
        return wikiRepository.findByOwnerIdAndIsArchived(userId, 0, pageable).map(this::convertToVO);
    }

    /**
     * 更新知识库
     *
     * @param id      知识库ID
     * @param wikiDTO 知识库更新信息
     * @return 更新后的知识库
     */
    @Override
    @Transactional
    public WikiVO updateWiki(Long id, WikiDTO.UpdateRequest wikiDTO) {
        Wiki wiki = wikiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));

        if (wikiDTO.getName() != null) {
            wiki.setName(wikiDTO.getName());
        }
        if (wikiDTO.getDescription() != null) {
            wiki.setDescription(wikiDTO.getDescription());
        }
        if (wikiDTO.getLogoUrl() != null) {
            wiki.setLogoUrl(wikiDTO.getLogoUrl());
        }
        if (wikiDTO.getVisibility() != null) {
            wiki.setVisibility(wikiDTO.getVisibility());
        }

        wiki.setUpdatedAt(LocalDateTime.now());
        Wiki updatedWiki = wikiRepository.save(wiki);
        return convertToVO(updatedWiki);
    }

    /**
     * 归档知识库
     *
     * @param id 知识库ID
     */
    @Override
    @Transactional
    public void archiveWiki(Long id) {
        Wiki wiki = wikiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));
        wiki.setIsArchived(1);
        wiki.setUpdatedAt(LocalDateTime.now());
        wikiRepository.save(wiki);
    }

    /**
     * 删除知识库
     *
     * @param id 知识库ID
     */
    @Override
    @Transactional
    public void deleteWiki(Long id) {
        Wiki wiki = wikiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));

        // 删除关联的成员
        wikiMemberRepository.deleteByWikiId(id);

        // 删除知识库
        wikiRepository.delete(wiki);
    }

    /**
     * 添加知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @param role   角色
     */
    @Override
    @Transactional
    public void addMember(Long wikiId, Long userId, String role) {
        // 检查知识库是否存在
        if (!wikiRepository.existsById(wikiId)) {
            throw new RuntimeException("知识库不存在");
        }

        // 检查用户是否存在
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("用户不存在");
        }

        // 检查是否已经是成员
        if (wikiMemberRepository.existsByWikiIdAndUserId(wikiId, userId)) {
            throw new RuntimeException("用户已经是知识库成员");
        }

        WikiMember member = new WikiMember();
        member.setWikiId(wikiId);
        member.setUserId(userId);
        member.setRoleInWiki(role);
        member.setCreatedAt(LocalDateTime.now());
        wikiMemberRepository.save(member);
    }

    /**
     * 移除知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     */
    @Override
    @Transactional
    public void removeMember(Long wikiId, Long userId) {
        wikiMemberRepository.deleteByWikiIdAndUserId(wikiId, userId);
    }

    /**
     * 获取知识库成员列表
     *
     * @param wikiId 知识库ID
     * @return 成员列表
     */
    @Override
    public List List<WikiMemberVO> getMembers(Long wikiId) {
        List List<WikiMember> members = wikiMemberRepository.findByWikiId(wikiId);
        return members.stream().map(this::convertToMemberVO).collect(Collectors.toList());
    }

    /**
     * 获取用户在知识库中的角色
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 角色代码
     */
    @Override
    public String getUserRoleInWiki(Long wikiId, Long userId) {
        return wikiMemberRepository.findRoleByWikiIdAndUserId(wikiId, userId);
    }

    /**
     * 检查用户是否是知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 是否是成员
     */
    @Override
    public boolean isMember(Long wikiId, Long userId) {
        return wikiMemberRepository.existsByWikiIdAndUserId(wikiId, userId);
    }

    /**
     * 更新页面数量
     *
     * @param wikiId 知识库ID
     */
    @Override
    @Transactional
    public void updatePageCount(Long wikiId) {
        int count = pageRepository.countByWikiIdAndIsDeleted(wikiId, 0);
        Wiki wiki = wikiRepository.findById(wikiId)
                .orElseThrow(() -> new RuntimeException("知识库不存在"));
        wiki.setPageCount(count);
        wiki.setUpdatedAt(LocalDateTime.now());
        wikiRepository.save(wiki);
    }

    /**
     * 将知识库实体转换为视图对象
     *
     * @param wiki 知识库实体
     * @return 知识库视图对象
     */
    private WikiVO convertToVO(Wiki wiki) {
        WikiVO vo = new WikiVO();
        vo.setId(wiki.getId());
        vo.setName(wiki.getName());
        vo.setSlug(wiki.getSlug());
        vo.setDescription(wiki.getDescription());
        vo.setLogoUrl(wiki.getLogoUrl());
        vo.setOwnerId(wiki.getOwnerId());
        vo.setVisibility(wiki.getVisibility());
        vo.setIsArchived(wiki.getIsArchived());
        vo.setPageCount(wiki.getPageCount());
        vo.setCreatedAt(wiki.getCreatedAt());
        vo.setUpdatedAt(wiki.getUpdatedAt());
        return vo;
    }

    /**
     * 将成员实体转换为视图对象
     *
     * @param member 成员实体
     * @return 成员视图对象
     */
    private WikiMemberVO convertToMemberVO(WikiMember member) {
        WikiMemberVO vo = new WikiMemberVO();
        vo.setId(member.getId());
        vo.setWikiId(member.getWikiId());
        vo.setUserId(member.getUserId());
        vo.setRoleInWiki(member.getRoleInWiki());
        vo.setCreatedAt(member.getCreatedAt());

        // 获取用户信息
        userRepository.findById(member.getUserId()).ifPresent(user -> {
            vo.setUsername(user.getUsername());
            vo.setNickname(user.getNickname());
            vo.setAvatarUrl(user.getAvatarUrl());
        });

        return vo;
    }
}
