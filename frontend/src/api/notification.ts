/**
 * 通知相关API
 * 包含通知的获取、标记已读等接口
 */
import request from './request';

/**
 * 通知对象
 */
export interface Notification {
  id: number;
  type: string;
  title: string;
  content: string;
  linkUrl: string;
  isRead: number;
  createdAt: string;
}

/**
 * 获取通知列表
 * @returns 通知列表
 */
export const getNotifications = (): Promise<any> => {
  return request.get('/api/notifications');
};

/**
 * 标记通知已读
 * @param id 通知ID
 * @returns 操作结果
 */
export const markNotificationRead = (id: number): Promise<any> => {
  return request.put(`/api/notifications/${id}/read`);
};

/**
 * 标记所有通知已读
 * @returns 操作结果
 */
export const markAllNotificationsRead = (): Promise<any> => {
  return request.put('/api/notifications/read-all');
};
