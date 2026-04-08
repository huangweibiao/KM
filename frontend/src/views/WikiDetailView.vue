<template>
  <div class="wiki-detail">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="280px" class="wiki-sidebar">
        <div class="wiki-info">
          <h2>{{ wiki?.name }}</h2>
          <p class="description">{{ wiki?.description }}</p>
        </div>
        <el-divider />
        <div class="page-tree-header">
          <h3>页面目录</h3>
          <el-button
            v-if="canEdit"
            type="primary"
            size="small"
            @click="showCreatePageDialog"
          >
            <el-icon><Plus /></el-icon>
            新建页面
          </el-button>
        </div>
        <el-tree
          :data="pageTree"
          :props="{ label: 'title', children: 'children' }"
          @node-click="handleNodeClick"
          highlight-current
          default-expand-all
        />
      </el-aside>

      <!-- 主内容区 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>

    <!-- 创建页面对话框 -->
    <el-dialog
      v-model="createPageDialogVisible"
      title="新建页面"
      width="500px"
    >
      <el-form :model="pageForm" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="pageForm.title" placeholder="请输入页面标题" />
        </el-form-item>
        <el-form-item label="父页面">
          <el-tree-select
            v-model="pageForm.parentPageId"
            :data="pageTree"
            :props="{ label: 'title', value: 'id' }"
            placeholder="选择父页面（可选）"
            clearable
          />
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="pageForm.type">
            <el-radio label="doc">文档</el-radio>
            <el-radio label="wiki">Wiki</el-radio>
            <el-radio label="faq">FAQ</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createPageDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="createPage">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getWikiById } from '../api/wiki'
import { getPageTree, createPage } from '../api/page'
import type { Wiki } from '../api/wiki'
import type { PageTreeNode } from '../api/page'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const wiki = ref ref<Wiki | null>(null)
const pageTree = ref ref<PageTreeNode[]>([])
const createPageDialogVisible = ref(false)
const pageForm = ref({
  title: '',
  parentPageId: null as number | null,
  type: 'doc'
})

const wikiId = computed(() => Number(route.params.id))
const canEdit = computed(() => userStore.isAuthenticated)

// 加载知识库详情
const loadWiki = async () => {
  try {
    const res = await getWikiById(wikiId.value)
    wiki.value = res.data
  } catch (error) {
    ElMessage.error('加载知识库失败')
  }
}

// 加载页面树
const loadPageTree = async () => {
  try {
    const res = await getPageTree(wikiId.value)
    pageTree.value = res.data
  } catch (error) {
    ElMessage.error('加载页面列表失败')
  }
}

// 处理节点点击
const handleNodeClick = (data: PageTreeNode) => {
  router.push(`/wikis/${wikiId.value}/pages/${data.id}`)
}

// 显示创建页面对话框
const showCreatePageDialog = () => {
  pageForm.value = {
    title: '',
    parentPageId: null,
    type: 'doc'
  }
  createPageDialogVisible.value = true
}

// 创建页面
const createPageHandler = async () => {
  if (!pageForm.value.title.trim()) {
    ElMessage.warning('请输入页面标题')
    return
  }

  try {
    const res = await createPage(wikiId.value, pageForm.value)
    ElMessage.success('创建成功')
    createPageDialogVisible.value = false
    loadPageTree()
    router.push(`/wikis/${wikiId.value}/pages/${res.data.id}`)
  } catch (error) {
    ElMessage.error('创建失败')
  }
}

onMounted(() => {
  loadWiki()
  loadPageTree()
})
</script>

<style scoped>
.wiki-detail {
  height: 100%;
}

.wiki-sidebar {
  background: #fff;
  border-right: 1px solid #e4e7ed;
  padding: 20px;
}

.wiki-info h2 {
  margin: 0 0 10px;
  font-size: 20px;
}

.wiki-info .description {
  color: #666;
  font-size: 14px;
  margin: 0;
}

.page-tree-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.page-tree-header h3 {
  margin: 0;
  font-size: 16px;
}

:deep(.el-tree-node__content) {
  height: 36px;
}
</style>
