/**
 * 知识库状态管理
 * 使用Pinia管理当前知识库状态
 */
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Wiki } from '@/api/wiki'

/**
 * 知识库状态存储
 */
export const useWikiStore = defineStore('wiki', () => {
  // State
  const currentWiki = ref ref<Wiki | null>(null)
  const wikiList = ref ref<Wiki[]>([])

  // Getters
  const currentWikiId = computed(() => currentWiki.value?.id)
  const hasCurrentWiki = computed(() => !!currentWiki.value)

  // Actions

  /**
   * 设置当前知识库
   * @param wiki 知识库对象
   */
  const setCurrentWiki = (wiki: Wiki | null) => {
    currentWiki.value = wiki
    if (wiki) {
      localStorage.setItem('currentWikiId', wiki.id.toString())
    } else {
      localStorage.removeItem('currentWikiId')
    }
  }

  /**
   * 设置知识库列表
   * @param list 知识库列表
   */
  const setWikiList = (list: Wiki[]) => {
    wikiList.value = list
  }

  /**
   * 从本地存储恢复当前知识库
   * @param list 知识库列表（用于查找）
   */
  const restoreCurrentWiki = (list: Wiki[]) => {
    const savedId = localStorage.getItem('currentWikiId')
    if (savedId) {
      const wiki = list.find(w => w.id === parseInt(savedId))
      if (wiki) {
        currentWiki.value = wiki
      }
    }
  }

  /**
   * 清除当前知识库
   */
  const clearCurrentWiki = () => {
    currentWiki.value = null
    localStorage.removeItem('currentWikiId')
  }

  return {
    currentWiki,
    wikiList,
    currentWikiId,
    hasCurrentWiki,
    setCurrentWiki,
    setWikiList,
    restoreCurrentWiki,
    clearCurrentWiki
  }
})
