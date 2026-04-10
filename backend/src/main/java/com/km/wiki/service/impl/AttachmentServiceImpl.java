package com.km.wiki.service.impl;

import com.km.wiki.dto.AttachmentDTO;
import com.km.wiki.entity.Attachment;
import com.km.wiki.entity.Page;
import com.km.wiki.repository.AttachmentRepository;
import com.km.wiki.repository.PageRepository;
import com.km.wiki.service.AttachmentService;
import com.km.wiki.vo.AttachmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 附件服务实现类
 * 实现附件上传、下载、管理功能
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private PageRepository pageRepository;

    @Value("${app.upload.path:uploads}")
    private String uploadPath;

    @Value("${app.upload.base-url:http://localhost:8080/uploads}")
    private String uploadBaseUrl;

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp",
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "text/plain", "text/html", "text/markdown"
    );

    /**
     * 上传附件
     *
     * @param pageId   页面ID
     * @param file     文件
     * @param userId   上传者ID
     * @return 附件信息
     */
    @Override
    @Transactional
    public Attachment uploadAttachment(Long pageId, MultipartFile file, Long userId) throws IOException {
        // 检查页面是否存在
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;

        // 按日期创建目录
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Path targetDir = Paths.get(uploadPath, dateDir);

        // 创建目录
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        // 保存文件
        Path targetPath = targetDir.resolve(filename);
        Files.copy(file.getInputStream(), targetPath);

        // 保存附件信息
        Attachment attachment = new Attachment();
        attachment.setPageId(pageId);
        attachment.setName(filename);
        attachment.setOriginalName(originalFilename);
        attachment.setFileSize(file.getSize());
        attachment.setFileType(file.getContentType());
        attachment.setStoragePath(targetPath.toString());
        attachment.setUrl(uploadBaseUrl + "/" + dateDir + "/" + filename);
        attachment.setUploaderId(userId);
        attachment.setIsDeleted(0);
        attachment.setCreatedAt(LocalDateTime.now());

        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment findById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("附件不存在"));
    }

    /**
     * 获取页面的附件列表
     *
     * @param pageId 页面ID
     * @return 附件列表
     */
    @Override
    public List<Attachment> getAttachmentsByPageId(Long pageId) {
        return attachmentRepository.findByPageIdAndIsDeletedOrderByCreatedAtDesc(pageId, 0);
    }

    /**
     * 删除附件
     *
     * @param attachmentId 附件ID
     * @param userId       用户ID
     */
    @Override
    @Transactional
    public void deleteAttachment(Long attachmentId, Long userId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("附件不存在"));

        // 检查是否是上传者或页面作者
        Page page = pageRepository.findById(attachment.getPageId())
                .orElseThrow(() -> new RuntimeException("页面不存在"));

        if (!attachment.getUploaderId().equals(userId) && !page.getAuthorId().equals(userId)) {
            throw new RuntimeException("无权删除此附件");
        }

        // 逻辑删除
        attachment.setIsDeleted(1);
        attachmentRepository.save(attachment);
    }

    /**
     * 获取附件详情
     *
     * @param attachmentId 附件ID
     * @return 附件信息
     */
    @Override
    public Attachment getAttachmentById(Long attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("附件不存在"));
    }

    @Override
    public AttachmentVO getAttachmentStats(Long pageId) {
        AttachmentVO vo = new AttachmentVO();
        vo.setPageId(pageId);
        List<Attachment> attachments = getAttachmentsByPageId(pageId);
        vo.setTotalCount(attachments.size());
        long totalSize = attachments.stream().mapToLong(Attachment::getFileSize).sum();
        vo.setTotalSize(totalSize);
        return vo;
    }

    @Override
    public org.springframework.data.domain.Page<AttachmentVO> getPageAttachments(Long pageId, Pageable pageable) {
        // 简化实现
        return org.springframework.data.domain.Page.empty(pageable);
    }

    @Override
    public org.springframework.data.domain.Page<AttachmentVO> getUserAttachments(Long uploaderId, Pageable pageable) {
        // 简化实现
        return org.springframework.data.domain.Page.empty(pageable);
    }

    @Override
    @Transactional
    public void batchDeleteAttachments(List<Long> ids) {
        // 简化实现
    }

    @Override
    public String getDownloadUrl(Long id) {
        Attachment attachment = findById(id);
        return attachment.getUrl();
    }

    @Override
    public String getPreviewUrl(Long id) {
        return getDownloadUrl(id);
    }

    @Override
    public boolean isAllowedFileType(String contentType) {
        return ALLOWED_FILE_TYPES.contains(contentType);
    }

    @Override
    public boolean isAllowedFileSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }

    @Override
    public List<String> getAllowedFileTypes() {
        return ALLOWED_FILE_TYPES;
    }

    @Override
    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }

    @Override
    @Transactional
    public Attachment renameAttachment(Long id, String newName) {
        Attachment attachment = findById(id);
        attachment.setOriginalName(newName);
        return attachmentRepository.save(attachment);
    }

    @Override
    @Transactional
    public Attachment moveAttachment(Long id, Long newPageId) {
        Attachment attachment = findById(id);
        attachment.setPageId(newPageId);
        return attachmentRepository.save(attachment);
    }
}
