package com.km.wiki.service;

import com.km.wiki.dto.WikiDTO;
import com.km.wiki.entity.Wiki;
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
     * @param wikiDTO 知识库数据传输对象
     * @return 创建后的知识库实体
     */
    Wiki createWiki(WikiDTO wikiDTO);

    /**
     * 更新知识库
     *
     * @param id      知识库ID
     * @param wikiDTO 知识库数据传输对象
     * @return 更新后的知识库实体
     */
    Wiki updateWiki(Long id, WikiDTO wikiDTO);

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
     * 获取知识库列表（分页）
     *
     * @param pageable 分页参数
     * @return 知识库分页数据
     */
    Page Page<WikiVO> getWikiList(Pageable pageable);

    /**
     * 获取用户参与的知识库列表
     *
     * @param userId   用户ID
     * @param pageable 分页参数
     * @return 知识库分页数据
     */
    Page Page<WikiVO> getUserWikis(Long userId, Pageable pageable);

    /**
     * 归档知识库
     *
     * @param id 知识库ID
     */
    void archiveWiki(Long id);

    /**
     * 添加知识库成员
     *
     * @param wikiId     知识库ID
     * @param userId     用户ID
     * @param roleInWiki 角色类型
     */
    void addMember(Long wikiId, Long userId, String roleInWiki);

    /**
     * 移除知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     */
    void removeMember(Long wikiId, Long userId);

    /**
     * 获取用户在知识库中的角色
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 角色类型
     */
    String getUserRoleInWiki(Long wikiId, Long userId);

    /**
     * 检查用户是否有权限操作知识库
     *
     * @param wikiId    知识库ID
     * @param userId    用户ID
     * @param minRole   最低要求角色
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
