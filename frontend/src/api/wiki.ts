/**
 * 知识库相关API
 * 包含知识库的增删改查等接口
 */
import request from './request';

/**
 * 创建知识库参数
 */
export interface CreateWikiParams {
  name: string;
  slug: string;
  description?: string;
  visibility?: number;
}

/**
 * 更新知识库参数
 */
export interface UpdateWikiParams {
  name?: string;
  description?: string;
  visibility?: number;
}

/**
 * 知识库对象
 */
export interface Wiki {
  id: number;
  name: string;
  slug: string;
  description: string;
  logoUrl: string;
  ownerId: number;
  visibility: number;
  isArchived: number;
  pageCount: number;
  createdAt: string;
  updatedAt: string;
}

/**
 * 获取知识库列表
 * @returns 知识库列表
 */
export const getWikiList = (): Promise<any> => {
  return request.get('/wikis');
};

/**
 * 获取知识库详情
 * @param id 知识库ID
 * @returns 知识库详情
 */
export const getWikiById = (id: number): Promise<any> => {
  return request.get(`/wikis/${id}`);
};

/**
 * 创建知识库
 * @param params 创建参数
 * @returns 创建的知识库
 */
export const createWiki = (params: CreateWikiParams): Promise<any> => {
  return request.post('/wikis', params);
};

/**
 * 更新知识库
 * @param id 知识库ID
 * @param params 更新参数
 * @returns 更新后的知识库
 */
export const updateWiki = (id: number, params: UpdateWikiParams): Promise<any> => {
  return request.put(`/wikis/${id}`, params);
};

/**
 * 删除知识库
 * @param id 知识库ID
 * @returns 删除结果
 */
export const deleteWiki = (id: number): Promise<any> => {
  return request.delete(`/wikis/${id}`);
};

/**
 * 获取知识库成员列表
 * @param id 知识库ID
 * @returns 成员列表
 */
export const getWikiMembers = (id: number): Promise<any> => {
  return request.get(`/wikis/${id}/members`);
};

/**
 * 添加知识库成员
 * @param id 知识库ID
 * @param userId 用户ID
 * @param role 角色
 * @returns 添加结果
 */
export const addWikiMember = (id: number, userId: number, role: string): Promise<any> => {
  return request.post(`/wikis/${id}/members`, { userId, role });
};

/**
 * 移除知识库成员
 * @param id 知识库ID
 * @param userId 用户ID
 * @returns 移除结果
 */
export const removeWikiMember = (id: number, userId: number): Promise<any> => {
  return request.delete(`/wikis/${id}/members/${userId}`);
};
