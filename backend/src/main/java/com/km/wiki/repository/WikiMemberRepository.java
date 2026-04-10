package com.km.wiki.repository;

import com.km.wiki.entity.WikiMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 知识库成员数据访问接口
 * 提供知识库成员实体的CRUD操作和自定义查询方法
 */
@Repository
public interface WikiMemberRepository extends JpaRepository<WikiMember, Long> {

    /**
     * 根据知识库ID查询成员列表
     *
     * @param wikiId 知识库ID
     * @return 成员列表
     */
    List<WikiMember> findByWikiId(Long wikiId);

    /**
     * 根据用户ID查询成员列表
     *
     * @param userId 用户ID
     * @return 成员列表
     */
    List<WikiMember> findByUserId(Long userId);

    /**
     * 根据知识库ID和用户ID查询成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 成员Optional对象
     */
    Optional<WikiMember> findByWikiIdAndUserId(Long wikiId, Long userId);

    /**
     * 检查用户是否为知识库成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 是否存在
     */
    boolean existsByWikiIdAndUserId(Long wikiId, Long userId);

    /**
     * 根据知识库ID和角色查询成员列表
     *
     * @param wikiId 知识库ID
     * @param roleInWiki 角色
     * @return 成员列表
     */
    List<WikiMember> findByWikiIdAndRoleInWiki(Long wikiId, String roleInWiki);

    /**
     * 根据用户ID和角色查询成员列表
     *
     * @param userId 用户ID
     * @param roleInWiki 角色
     * @return 成员列表
     */
    List<WikiMember> findByUserIdAndRoleInWiki(Long userId, String roleInWiki);

    /**
     * 根据知识库ID和用户ID删除成员
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     */
    void deleteByWikiIdAndUserId(Long wikiId, Long userId);

    /**
     * 统计知识库的成员数量
     *
     * @param wikiId 知识库ID
     * @return 成员数量
     */
    long countByWikiId(Long wikiId);

    /**
     * 统计用户参与的知识库数量
     *
     * @param userId 用户ID
     * @return 知识库数量
     */
    long countByUserId(Long userId);

    /**
     * 根据知识库ID查询成员并按创建时间排序
     *
     * @param wikiId 知识库ID
     * @return 成员列表
     */
    List<WikiMember> findByWikiIdOrderByCreatedAtDesc(Long wikiId);

    /**
     * 根据知识库ID和用户ID查询成员角色
     *
     * @param wikiId 知识库ID
     * @param userId 用户ID
     * @return 角色字符串
     */
    @Query("SELECT wm.roleInWiki FROM WikiMember wm WHERE wm.wikiId = :wikiId AND wm.userId = :userId")
    String findRoleByWikiIdAndUserId(@Param("wikiId") Long wikiId, @Param("userId") Long userId);

    /**
     * 根据知识库ID删除所有成员
     *
     * @param wikiId 知识库ID
     */
    void deleteByWikiId(Long wikiId);
}
