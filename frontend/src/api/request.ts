/**
 * HTTP请求工具
 * 基于axios封装，处理请求拦截、响应拦截、错误处理等
 */
import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus';
import { useUserStore } from '../stores/user';

// 创建axios实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
request.interceptors.request.use(
  (config: any) => {
    const userStore = useUserStore();
    const token = userStore.token;
    
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;
    
    // 如果返回的状态码不是200，说明接口出错
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败');
      
      // 401: 未登录或token过期
      if (res.code === 401) {
        const userStore = useUserStore();
        userStore.logout();
        window.location.href = '/login';
      }
      
      return Promise.reject(new Error(res.message || '请求失败'));
    }
    
    return res;
  },
  (error) => {
    const message = error.response?.data?.message || error.message || '网络错误';
    ElMessage.error(message);
    return Promise.reject(error);
  }
);

export default request;
