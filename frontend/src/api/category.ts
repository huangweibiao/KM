/**
 * 分类API
 * 提供分类的增删改查接口
 */
import request from './request'
import type { Category } from '@/types'

/**
 * 获取知识库的分类列表
 * @param wikiId 知识库ID
 * @returns 分类列表
 */
export function getCategoryList(wikiId: number) {
  return request.get.get<Category[]>(`/wikis/${wikiId}/categories`)
}

/**
 * 创建分类
 * @param wikiId 知识库ID
 * @param data 分类数据
 * @returns 创建的分类
 */
export function createCategory(wikiId: number, data: Partial Partial<Category>) {
  return request.post.post<Category>(`/wikis/${wikiId}/categories`, data)
}

/**
 * 更新分类
 * @param id 分类ID
 * @param data 分类数据
 * @returns 更新后的分类
 */
export function updateCategory(id: number, data: Partial Partial<Category>) {
  return request.put.put<Category>(`/categories/${id}`, data)
}

/**
 * 删除分类
 * @param id 分类ID
 */
export function deleteCategory(id: number) {
  return request.delete(`/categories/${id}`)
}
