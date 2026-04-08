package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.entity.Attachment;
import com.km.wiki.service.AttachmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 附件控制器
 * 处理文件上传、下载、管理等接口
 */
@RestController
@RequestMapping("/api")
public class AttachmentController {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 上传附件
     *
     * @param pageId         页面ID
     * @param file           文件
     * @param authentication 当前认证信息
     * @return 上传结果
     */
    @PostMapping("/pages/{pageId}/attachments")
    public Result Result<Attachment> uploadAttachment(
            @PathVariable Long pageId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        try {
            Long userId = getCurrentUserId(authentication);
            Attachment attachment = attachmentService.uploadAttachment(pageId, file, userId);
            logger.info("用户 {} 上传附件到页面 {}: {}", userId, pageId, file.getOriginalFilename());
            return Result.success(attachment);
        } catch (IOException e) {
            logger.error("上传附件失败", e);
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取页面的附件列表
     *
     * @param pageId 页面ID
     * @return 附件列表
     */
    @GetMapping("/pages/{pageId}/attachments")
    public Result<List<List<Attachment>> getAttachments(@PathVariable Long pageId) {
        List List<Attachment> attachments = attachmentService.getAttachmentsByPageId(pageId);
        return Result.success(attachments);
    }

    /**
     * 删除附件
     *
     * @param attachmentId   附件ID
     * @param authentication 当前认证信息
     * @return 操作结果
     */
    @DeleteMapping("/attachments/{attachmentId}")
    public Result<Void> deleteAttachment(
            @PathVariable Long attachmentId,
            Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        attachmentService.deleteAttachment(attachmentId, userId);
        logger.info("用户 {} 删除附件 {}", userId, attachmentId);
        return Result.success();
    }

    /**
     * 下载附件
     *
     * @param attachmentId 附件ID
     * @param request      HTTP请求
     * @return 文件资源
     */
    @GetMapping("/attachments/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long attachmentId,
            HttpServletRequest request) {
        try {
            Attachment attachment = attachmentService.getAttachmentById(attachmentId);
            Path filePath = Paths.get(attachment.getStoragePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 确定内容类型
            String contentType = attachment.getFileType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + attachment.getOriginalName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("下载附件失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取当前用户ID
     *
     * @param authentication 认证信息
     * @return 用户ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("用户未登录");
        }
        return Long.valueOf(authentication.getName());
    }
}
