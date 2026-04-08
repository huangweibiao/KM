/**
 * 全局类型定义
 * 定义项目中使用的所有类型
 */

/**
 * 用户类型
 */
export interface User {
  id: number
  username: string
  email: string
  nickname?: string
  avatarUrl?: string
  deptId?: number
  status: number
  lastLoginAt?: string
  createdAt: string
}

/**
 * 知识库类型
 */
export interface Wiki {
  id: number
  name: string
  slug: string
  description?: string
  logoUrl?: string
  ownerId: number
  visibility: number
  isArchived: number
  pageCount: number
  createdAt: string
  updatedAt: string
}

/**
 * 分类类型
 */
export interface Category {
  id: number
  wikiId: number
  parentId?: number
  name: string
  slug: string
  sortOrder: number
  createdAt: string
  updatedAt: string
}

/**
 * 页面类型
 */
export interface Page {
  id: number
  wikiId: number
  categoryId?: number
  parentPageId?: number
  title: string
  slug: string
  content?: string
  contentFormat: string
  type: string
  version: number
  authorId: number
  lastEditorId?: number
  status: number
  publishedAt?: string
  viewCount: number
  likeCount: number
  createdAt: string
  updatedAt: string
}

/**
 * 页面树节点类型
 */
export interface PageTreeNode {
  id: number
  title: string
  slug: string
  type: string
  children?: PageTreeNode[]
}

/**
 * 页面版本类型
 */
export interface PageVersion {
  id: number
  pageId: number
  version: number
  title: string
  content: string
  editorId: number
  changeNote?: string
  createdAt: string
}

/**
 * 标签类型
 */
export interface Tag {
  id: number
  wikiId?: number
  name: string
  createdAt: string
}

/**
 * 评论类型
 */
export interface Comment {
  id: number
  pageId: number
  userId: number
  username: string
  avatarUrl?: string
  parentId?: number
  content: string
  likeCount: number
  isDeleted: number
  createdAt: string
  updatedAt: string
  children?: Comment[]
}

/**
 * 通知类型
 */
export interface Notification {
  id: number
  userId: number
  type: string
  title: string
  content?: string
  linkUrl?: string
  isRead: number
  createdAt: string
}

/**
 * 附件类型
 */
export interface Attachment {
  id: number
  pageId: number
  name: string
  originalName: string
  fileSize: number
  fileType: string
  url: string
  uploaderId: number
  createdAt: string
}

/**
 * 收藏类型
 */
export interface Favorite {
  id: number
  userId: number
  pageId: number
  createdAt: string
}

/**
 * 关注类型
 */
export interface Watch {
  id: number
  userId: number
  pageId: number
  watchType: string
  createdAt: string
}

/**
 * API响应类型
 */
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

/**
 * 分页结果类型
 */
export interface PageResult<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}
