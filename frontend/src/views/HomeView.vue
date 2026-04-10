<template>
  <div class="home-container">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <div class="nav-content">
        <div class="nav-logo">
          <span>KM Wiki</span>
        </div>
        <div class="nav-actions">
          <template v-if="!isLoggedIn">
            <el-button type="primary" @click="goToLogin">登录</el-button>
            <el-button @click="goToRegister">注册</el-button>
          </template>
          <template v-else>
            <el-dropdown @command="handleUserCommand">
              <span class="user-dropdown">
                <el-icon><User /></el-icon>
                {{ username }}
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </div>
      </div>
    </div>
    
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>欢迎使用 KM Wiki</span>
            </div>
          </template>
          <div class="welcome-content">
            <h3>知识管理系统</h3>
            <p>统一知识沉淀，高效检索，支持协作</p>
            <el-button type="primary" @click="goToWikis">浏览知识库</el-button>
          </div>
        </el-card>
        
        <el-card style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>最近更新</span>
            </div>
          </template>
          <el-empty v-if="recentPages.length === 0" description="暂无更新" />
          <el-timeline v-else>
            <el-timeline-item
              v-for="page in recentPages"
              :key="page.id"
              :timestamp="formatDate(page.updatedAt)"
            >
              <el-link @click="goToPage(page.id)">{{ page.title }}</el-link>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>我的收藏</span>
            </div>
          </template>
          <el-empty v-if="favorites.length === 0" description="暂无收藏" />
          <el-list v-else>
            <el-list-item v-for="item in favorites" :key="item.id">
              <el-link @click="goToPage(item.pageId)">{{ item.pageTitle }}</el-link>
            </el-list-item>
          </el-list>
        </el-card>
        
        <el-card style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>快捷操作</span>
            </div>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="goToWikis" style="width: 100%; margin-bottom: 10px">
              浏览知识库
            </el-button>
            <el-button @click="goToSearch" style="width: 100%">
              搜索知识
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const recentPages = ref<any[]>([])
const favorites = ref<any[]>([])

// 简单判断登录状态，从 localStorage 获取
const isLoggedIn = computed(() => !!localStorage.getItem('token'))
const username = computed(() => localStorage.getItem('username') || '用户')

onMounted(() => {
  // 这里可以加载最近更新和收藏数据
  loadRecentPages()
  loadFavorites()
})

const loadRecentPages = async () => {
  // 实现加载最近页面逻辑
}

const loadFavorites = async () => {
  // 实现加载收藏逻辑
}

const goToWikis = () => {
  router.push('/wikis')
}

const goToSearch = () => {
  router.push('/search')
}

const goToLogin = () => {
  router.push('/login')
}

const goToRegister = () => {
  router.push('/register')
}

const handleUserCommand = (command: string) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    localStorage.removeItem('userId')
    ElMessage.success('已退出登录')
    location.reload()
  }
}

const goToPage = (pageId: number) => {
  router.push(`/pages/${pageId}`)
}

const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: #f5f5f5;
}

.nav-bar {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
}

.nav-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-logo {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-dropdown {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 4px;
}

.user-dropdown:hover {
  background: #f5f5f5;
}

.home-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-content {
  text-align: center;
  padding: 20px;
}

.welcome-content h3 {
  margin-bottom: 10px;
}

.welcome-content p {
  color: #666;
  margin-bottom: 20px;
}

.quick-actions {
  display: flex;
  flex-direction: column;
}
</style>
