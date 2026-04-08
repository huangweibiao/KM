/**
 * 认证相关API
 * 包含登录、注册、登出等接口
 */
import request from './request';

/**
 * 登录请求参数
 */
export interface LoginParams {
  username: string;
  password: string;
}

/**
 * 注册请求参数
 */
export interface RegisterParams {
  username: string;
  email: string;
  password: string;
  nickname?: string;
}

/**
 * 登录响应数据
 */
export interface LoginResponse {
  token: string;
  user: {
    id: number;
    username: string;
    email: string;
    nickname: string;
    avatarUrl: string;
  };
}

/**
 * 用户登录
 * @param params 登录参数
 * @returns 登录响应
 */
export const login = (params: LoginParams): Promise<any> => {
  return request.post('/api/auth/login', params);
};

/**
 * 用户注册
 * @param params 注册参数
 * @returns 注册响应
 */
export const register = (params: RegisterParams): Promise<any> => {
  return request.post('/api/auth/register', params);
};

/**
 * 用户登出
 * @returns 登出响应
 */
export const logout = (): Promise<any> => {
  return request.post('/api/auth/logout');
};

/**
 * 获取当前用户信息
 * @returns 用户信息
 */
export const getCurrentUser = (): Promise<any> => {
  return request.get('/api/auth/me');
};
