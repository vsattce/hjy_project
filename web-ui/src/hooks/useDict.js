import { ref, toRefs } from 'vue'
import { useDictStore } from '@/store/modules/dict'

/**
 * 字典 Hook
 * @param  {...String} args 字典类型，如 'sys_sex', 'sys_notice_type'
 */
export function useDict(...args) {
  const res = ref({})
  const dictStore = useDictStore()

  args.forEach((dictType) => {
    // 1. 先初始化一个空数组，保证页面渲染不报错
    res.value[dictType] = []

    // 2. 尝试从 Store 获取
    const existingDict = dictStore.dictMap[dictType]
    
    if (existingDict) {
      // 如果 Store 已经有了，直接给
      res.value[dictType] = existingDict
    } else {
      // 如果没有，调用 Store 去加载 (异步更新)
      dictStore.getDict(dictType).then((data) => {
        res.value[dictType] = data
      })
    }
  })

  // 3. 返回响应式对象
  // toRefs 保证解构出去的变量依然是响应式的
  return toRefs(res.value)
}