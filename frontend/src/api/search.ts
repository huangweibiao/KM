/**
 * 搜索相关API
 * 包含全文搜索、搜索建议等接口
 */
import request from './request';

/**
 * 搜索结果
 */
export interface SearchResult {
  id: number;
  title: string;
  content: string;
  wikiId: number;
  authorId: number;
  createdAt: string;
  updatedAt: string;
}

/**
 * 搜索页面
 * @param keyword 搜索关键词
 * @param wikiId 知识库ID（可选）
 * @returns 搜索结果列表
 */
export const searchPages = (keyword: string, wikiId?: number): Promise<any> => {
  const params: any = { q: keyword };
  if (wikiId) {
    params.wikiId = wikiId;
  }
  return request.get('/search', { params });
};

/**
 * 获取搜索建议
 * @param keyword 搜索关键词
 * @returns 建议列表
 */
export const getSearchSuggestions = (keyword: string): Promise<any> => {
  return request.get('/search/suggest', {
    params: { q: keyword }
  });
};
