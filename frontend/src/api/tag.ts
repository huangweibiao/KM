/**
 * 标签API
 * 提供标签的增删改查接口
 */
import request from './request'
import type { Tag } from '@/types'

/**
 * 获取知识库的标签列表
 * @param wikiId 知识库ID
 * @returns 标签列表
 */
export function getTagList(wikiId: number) {
  return request.get<Tag[]>(`/wikis/${wikiId}/tags`)
}

/**
 * 创建标签
 * @param wikiId 知识库ID
 * @param name 标签名称
 * @returns 创建的标签
 */
export function createTag(wikiId: number, name: string) {
  return request.post<Tag>(`/wikis/${wikiId}/tags`, { name })
}

/**
 * 删除标签
 * @param id 标签ID
 */
export function deleteTag(id: number) {
  return request.delete(`/tags/${id}`)
}

/**
 * 为页面添加标签
 * @param pageId 页面ID
 * @param tagId 标签ID
 */
export function addTagToPage(pageId: number, tagId: number) {
  return request.post(`/pages/${pageId}/tags`, { tagId })
}

/**
 * 从页面移除标签
 * @param pageId 页面ID
 * @param tagId 标签ID
 */
export function removeTagFromPage(pageId: number, tagId: number) {
  return request.delete(`/pages/${pageId}/tags/${tagId}`)
}

/**
 * 获取页面的标签列表
 * @param pageId 页面ID
 * @returns 标签列表
 */
export function getTagsByPageId(pageId: number) {
  return request.get<Tag[]>(`/pages/${pageId}/tags`)
}
