/**
 * 评论相关API
 * 包含评论的增删改查等接口
 */
import request from './request';

/**
 * 评论对象
 */
export interface Comment {
  id: number;
  pageId: number;
  userId: number;
  username: string;
  avatarUrl: string;
  parentId: number;
  content: string;
  likeCount: number;
  isDeleted: number;
  createdAt: string;
  updatedAt: string;
  children?: Comment[];
}

/**
 * 创建评论参数
 */
export interface CreateCommentParams {
  content: string;
  parentId?: number;
}

/**
 * 获取页面的评论列表
 * @param pageId 页面ID
 * @returns 评论列表
 */
export const getComments = (pageId: number): Promise<any> => {
  return request.get(`/api/pages/${pageId}/comments`);
};

/**
 * 发表评论
 * @param pageId 页面ID
 * @param params 评论参数
 * @returns 创建的评论
 */
export const createComment = (pageId: number, params: CreateCommentParams): Promise<any> => {
  return request.post(`/api/pages/${pageId}/comments`, params);
};

/**
 * 更新评论
 * @param id 评论ID
 * @param content 评论内容
 * @returns 更新后的评论
 */
export const updateComment = (id: number, content: string): Promise<any> => {
  return request.put(`/api/comments/${id}`, { content });
};

/**
 * 删除评论
 * @param id 评论ID
 * @returns 删除结果
 */
export const deleteComment = (id: number): Promise<any> => {
  return request.delete(`/api/comments/${id}`);
};
