/**
 * 页面相关API
 * 包含页面的增删改查、版本管理等接口
 */
import request from './request';

/**
 * 创建页面参数
 */
export interface CreatePageParams {
  title: string;
  content?: string;
  contentFormat?: string;
  categoryId?: number;
  parentPageId?: number;
  type?: string;
}

/**
 * 更新页面参数
 */
export interface UpdatePageParams {
  title?: string;
  content?: string;
  categoryId?: number;
  changeNote?: string;
}

/**
 * 页面对象
 */
export interface Page {
  id: number;
  wikiId: number;
  categoryId: number;
  parentPageId: number;
  title: string;
  slug: string;
  content: string;
  contentFormat: string;
  type: string;
  version: number;
  authorId: number;
  lastEditorId: number;
  status: number;
  publishedAt: string;
  viewCount: number;
  likeCount: number;
  createdAt: string;
  updatedAt: string;
}

/**
 * 页面树节点
 */
export interface PageTreeNode {
  id: number;
  title: string;
  slug: string;
  type: string;
  children?: PageTreeNode[];
}

/**
 * 获取知识库的页面树
 * @param wikiId 知识库ID
 * @returns 页面树列表
 */
export const getPageTree = (wikiId: number): Promise<any> => {
  return request.get(`/api/wikis/${wikiId}/pages`);
};

/**
 * 获取页面详情
 * @param id 页面ID
 * @returns 页面详情
 */
export const getPageById = (id: number): Promise<any> => {
  return request.get(`/api/pages/${id}`);
};

/**
 * 创建页面
 * @param wikiId 知识库ID
 * @param params 创建参数
 * @returns 创建的页面
 */
export const createPage = (wikiId: number, params: CreatePageParams): Promise<any> => {
  return request.post(`/api/wikis/${wikiId}/pages`, params);
};

/**
 * 更新页面
 * @param id 页面ID
 * @param params 更新参数
 * @returns 更新后的页面
 */
export const updatePage = (id: number, params: UpdatePageParams): Promise<any> => {
  return request.put(`/api/pages/${id}`, params);
};

/**
 * 删除页面
 * @param id 页面ID
 * @returns 删除结果
 */
export const deletePage = (id: number): Promise<any> => {
  return request.delete(`/api/pages/${id}`);
};

/**
 * 获取页面版本历史
 * @param id 页面ID
 * @returns 版本列表
 */
export const getPageVersions = (id: number): Promise<any> => {
  return request.get(`/api/pages/${id}/versions`);
};

/**
 * 恢复页面版本
 * @param id 页面ID
 * @param version 版本号
 * @returns 恢复结果
 */
export const restorePageVersion = (id: number, version: number): Promise<any> => {
  return request.post(`/api/pages/${id}/versions/${version}/restore`);
};

/**
 * 收藏/取消收藏页面
 * @param id 页面ID
 * @returns 操作结果
 */
export const toggleFavorite = (id: number): Promise<any> => {
  return request.post(`/api/pages/${id}/favorite`);
};

/**
 * 关注/取消关注页面
 * @param id 页面ID
 * @returns 操作结果
 */
export const toggleWatch = (id: number): Promise<any> => {
  return request.post(`/api/pages/${id}/watch`);
};
