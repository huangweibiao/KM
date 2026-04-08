<template>
  <div class="home-container">
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const recentPages = ref<any[]>([])
const favorites = ref<any[]>([])

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
