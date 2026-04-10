package com.km.wiki.repository;

import com.km.wiki.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 附件数据访问接口
 * 提供Attachment实体的数据库操作方法
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * 根据页面ID查询附件列表（未删除）
     *
     * @param pageId 页面ID
     * @param isDeleted 是否删除
     * @return 附件列表
     */
    List<Attachment> findByPageIdAndIsDeletedOrderByCreatedAtDesc(Long pageId, Integer isDeleted);

    /**
     * 根据上传者ID查询附件列表
     *
     * @param uploaderId 上传者ID
     * @return 附件列表
     */
    List<Attachment> findByUploaderIdOrderByCreatedAtDesc(Long uploaderId);

    /**
     * 统计页面的附件数量（未删除）
     *
     * @param pageId 页面ID
     * @param isDeleted 是否删除
     * @return 附件数量
     */
    long countByPageIdAndIsDeleted(Long pageId, Integer isDeleted);
}
