/**
 * 用户状态管理
 * 使用Pinia管理用户登录状态和用户信息
 */
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, getCurrentUser, type LoginParams } from '@/api/auth'

/**
 * 用户对象
 */
export interface User {
  id: number
  username: string
  email: string
  nickname: string
  avatarUrl: string
  status: number
}

/**
 * 用户状态存储
 */
export const useUserStore = defineStore('user', () => {
  // State
  const token = ref<string>(localStorage.getItem('token') || '')
  const user = ref<User | null>(null)

  // Getters
  const isLoggedIn = computed(() => !!token.value)
  const userInfo = computed(() => user.value)

  // Actions

  /**
   * 用户登录
   * @param params 登录参数
   */
  const doLogin = async (params: LoginParams) => {
    const res = await login(params)
    token.value = res.data.token
    user.value = res.data.user
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('username', res.data.user.username)
    localStorage.setItem('userId', String(res.data.user.id))
    return res
  }

  /**
   * 获取当前用户信息
   */
  const fetchUserInfo = async () => {
    if (!token.value) return
    try {
      const res = await getCurrentUser()
      user.value = res.data
    } catch (error) {
      // Token无效，清除登录状态
      logout()
    }
  }

  /**
   * 用户登出
   */
  const logout = () => {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    localStorage.removeItem('userId')
  }

  /**
   * 设置Token
   * @param newToken 新的Token
   */
  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  return {
    token,
    user,
    isLoggedIn,
    userInfo,
    doLogin,
    fetchUserInfo,
    logout,
    setToken
  }
})
