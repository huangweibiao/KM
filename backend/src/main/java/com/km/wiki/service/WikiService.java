package com.km.wiki.service;

import com.km.wiki.dto.WikiDTO;
import com.km.wiki.entity.Wiki;
import com.km.wiki.vo.WikiMemberVO;
import com.km.wiki.vo.WikiVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 知识库服务接口
 * 定义知识库相关的业务逻辑操作
 */
public interface WikiService {

    /**
     * 创建知识库
     *
     * @param wikiDTO 知识库创建信息
     * @param ownerId 创建者ID
     * @return 创建的知识库视图
     */
    WikiVO createWiki(WikiDTO.CreateRequest wikiDTO, Long ownerId);

    /**
     * 获取知识库详情
     *
     * @param id 知识库ID
     * @return 知识库视图对象
     */
    WikiVO getWikiById(Long id);

    /**
     * 根据slug获取知识库
     *
     * @param slug 知识库标识符
     * @return 知识库视图对象
     */
    WikiVO getWikiBySlug(String slug);

    /**
     * 获取知识库列表
     *
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    Page<WikiVO> getWikiList(Pageable pageable);

    /**
     * 获取用户的知识库列表
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 知识库分页列表
     */
    Page<WikiVO> getUserWikis(Long userId, Pageable pageable);

    /**
     * 更新知识库
     *
     * @param id      知识库ID
     * @param wikiDTO 知识库更新信息
     * @return 更新后的知识库视图
     */
    WikiVO updateWiki(Long id, WikiDTO.UpdateRequest wikiDTO);

    /**
     * 归档知识库
     *
     * @param id 知识库ID
     */
    void archiveWiki(Long id);

    /**
     * 删除知识库
     *
     * @param id 知识库ID
     */
    void deleteWiki(Long id);

    /**
     * 根据ID获取知识库
     *
     * @param id 知识库ID
     * @return 知识库实体
     */
    Wiki findById(Long id);

    /**
     * 获取知识库详情
     *
     * @param id 知识库ID
     * @return 知识库视图对象
     */
    WikiVO getWikiDetail(Long id);

    /**
     * 添加知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @param role   角色
     */
    void addMember(Long wikiId, Long userId, String role);

    /**
     * 移除知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     */
    void removeMember(Long wikiId, Long userId);

    /**
     * 获取知识库成员列表
     *
     * @param wikiId 知识库ID
     * @return 成员列表
     */
    List<WikiMemberVO> getMembers(Long wikiId);

    /**
     * 添加知识库成员（兼容旧调用）
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @param role   角色
     */
    void addWikiMember(Long wikiId, Long userId, String role);

    /**
     * 移除知识库成员（兼容旧调用）
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     */
    void removeWikiMember(Long wikiId, Long userId);

    /**
     * 获取知识库成员列表（兼容旧调用）
     *
     * @param wikiId 知识库ID
     * @return 成员列表
     */
    List<WikiMemberVO> getWikiMembers(Long wikiId);

    /**
     * 获取用户在知识库中的角色
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 角色代码
     */
    String getUserRoleInWiki(Long wikiId, Long userId);

    /**
     * 检查用户是否是知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 是否是成员
     */
    boolean isMember(Long wikiId, Long userId);

    /**
     * 检查用户是否有权限操作知识库
     *
     * @param wikiId  知识库ID
     * @param userId  用户ID
     * @param minRole 最低要求角色
     * @return 是否有权限
     */
    boolean hasPermission(Long wikiId, Long userId, String minRole);

    /**
     * 更新页面数量
     *
     * @param wikiId 知识库ID
     */
    void updatePageCount(Long wikiId);
}
