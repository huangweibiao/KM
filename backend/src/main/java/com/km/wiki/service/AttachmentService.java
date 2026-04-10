package com.km.wiki.service;

import com.km.wiki.entity.Attachment;
import com.km.wiki.vo.AttachmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 附件服务接口
 * 定义附件相关的业务逻辑操作
 */
public interface AttachmentService {

    /**
     * 上传附件
     *
     * @param pageId   页面ID
     * @param file     上传的文件
     * @param uploaderId 上传者ID
     * @return 创建后的附件实体
     */
    Attachment uploadAttachment(Long pageId, MultipartFile file, Long uploaderId) throws IOException;

    /**
     * 根据ID获取附件
     *
     * @param id 附件ID
     * @return 附件实体
     */
    Attachment findById(Long id);

    /**
     * 获取页面的附件列表
     *
     * @param pageId 页面ID
     * @return 附件列表
     */
    List<Attachment> getAttachmentsByPageId(Long pageId);

    /**
     * 获取附件详情
     *
     * @param attachmentId 附件ID
     * @return 附件实体
     */
    Attachment getAttachmentById(Long attachmentId);

    /**
     * 删除附件
     *
     * @param attachmentId 附件ID
     * @param userId       用户ID
     */
    void deleteAttachment(Long attachmentId, Long userId);

    /**
     * 获取附件统计信息
     *
     * @param pageId 页面ID
     * @return 附件视图统计信息
     */
    AttachmentVO getAttachmentStats(Long pageId);

    /**
     * 获取页面的附件列表（分页）
     *
     * @param pageId   页面ID
     * @param pageable 分页参数
     * @return 附件分页数据
     */
    Page<AttachmentVO> getPageAttachments(Long pageId, Pageable pageable);

    /**
     * 获取用户上传的附件列表（分页）
     *
     * @param uploaderId 上传者ID
     * @param pageable   分页参数
     * @return 附件分页数据
     */
    Page<AttachmentVO> getUserAttachments(Long uploaderId, Pageable pageable);

    /**
     * 批量删除附件
     *
     * @param ids 附件ID列表
     */
    void batchDeleteAttachments(List<Long> ids);

    /**
     * 获取附件下载URL
     *
     * @param id 附件ID
     * @return 下载URL
     */
    String getDownloadUrl(Long id);

    /**
     * 获取附件预览URL
     *
     * @param id 附件ID
     * @return 预览URL
     */
    String getPreviewUrl(Long id);

    /**
     * 检查文件类型是否允许上传
     *
     * @param contentType 文件MIME类型
     * @return 是否允许
     */
    boolean isAllowedFileType(String contentType);

    /**
     * 检查文件大小是否在允许范围内
     *
     * @param fileSize 文件大小（字节）
     * @return 是否允许
     */
    boolean isAllowedFileSize(long fileSize);

    /**
     * 获取允许的文件类型列表
     *
     * @return 文件类型列表
     */
    List<String> getAllowedFileTypes();

    /**
     * 获取最大允许文件大小
     *
     * @return 最大文件大小（字节）
     */
    long getMaxFileSize();

    /**
     * 重命名附件
     *
     * @param id      附件ID
     * @param newName 新文件名
     * @return 更新后的附件实体
     */
    Attachment renameAttachment(Long id, String newName);

    /**
     * 移动附件到其他页面
     *
     * @param id      附件ID
     * @param newPageId 新页面ID
     * @return 更新后的附件实体
     */
    Attachment moveAttachment(Long id, Long newPageId);
}
