/**
 * 附件相关API
 * 包含文件上传、下载、管理等接口
 */
import request from './request';

/**
 * 附件对象
 */
export interface Attachment {
  id: number;
  pageId: number;
  name: string;
  originalName: string;
  fileSize: number;
  fileType: string;
  url: string;
  uploaderId: number;
  createdAt: string;
}

/**
 * 获取页面的附件列表
 * @param pageId 页面ID
 * @returns 附件列表
 */
export const getAttachments = (pageId: number): Promise<any> => {
  return request.get(`/api/pages/${pageId}/attachments`);
};

/**
 * 上传附件
 * @param pageId 页面ID
 * @param file 文件对象
 * @returns 上传结果
 */
export const uploadAttachment = (pageId: number, file: File): Promise<any> => {
  const formData = new FormData();
  formData.append('file', file);
  return request.post(`/api/pages/${pageId}/attachments`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

/**
 * 删除附件
 * @param attachmentId 附件ID
 * @returns 删除结果
 */
export const deleteAttachment = (attachmentId: number): Promise<any> => {
  return request.delete(`/api/attachments/${attachmentId}`);
};

/**
 * 下载附件URL
 * @param attachmentId 附件ID
 * @returns 下载URL
 */
export const getDownloadUrl = (attachmentId: number): string => {
  return `/api/attachments/${attachmentId}/download`;
};
