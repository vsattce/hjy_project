import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getDictDataByType } from "@/api/dict";

export const useDictStore = defineStore('dict', () => {
  // 1. 缓存池： { 'sys_user_sex': [ {dictLabel: '男', dictValue: '0'}... ], ... }
  const dictMap = ref({})

  // 2. 请求队列池：防止同一时间多个组件请求同一个字典导致并发
  const pendingPromises = {}

  /**
   * 获取字典的核心逻辑
   * @param {String} dictType 字典类型
   */
  const getDict = async (dictType) => {
    // 1. 优先查缓存 (内存里有，直接返回)
    if (dictMap.value[dictType]) {
      return dictMap.value[dictType]
    }

    // 2. 查请求队列 (如果有正在路上的请求，直接复用那个 Promise)
    if (pendingPromises[dictType]) {
      return await pendingPromises[dictType]
    }

    // 3. 既没缓存也没请求中，发起新请求
    try {
      const promise = getDictDataByType(dictType)
      pendingPromises[dictType] = promise // 占位，锁住

      const res = await promise
      
      // 4. 拿到数据，存入缓存
      // 假设后端返回的数据直接就是数组，如果包裹在 data 里，请改为 res.data
      dictMap.value[dictType] = res.data 

      // 5. 任务结束，清理锁
      delete pendingPromises[dictType]

      return dictMap.value[dictType]
    } catch (error) {
      delete pendingPromises[dictType]
      return [] // 失败保底返回空数组
    }
  }

  return {
    dictMap,
    getDict
  }
})