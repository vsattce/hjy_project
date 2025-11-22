<template>
  <div>
    <template v-for="(item) in options">
      <template v-if="values.includes(String(item.dictValue))">
        
        <span
          v-if="item.listClass || item.cssClass"
          :key="item.dictValue"
          :class="item.cssClass"
        >
          <el-tag :type="item.listClass || 'info'">{{ item.dictLabel }}</el-tag>
        </span>
        
        <span v-else :key="item.dictValue + '_text'">{{ item.dictLabel }}</span>
        
      </template>
    </template>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  // 字典数据列表 (DictData 数组)
  options: {
    type: Array,
    default: () => []
  },
  // 当前的值 (可能是 String '0', Number 0, 或者是多选 Array ['0', '1'])
  value: [Number, String, Array]
})

// 统一把传入的值转成 String 数组，方便比对
const values = computed(() => {
  if (props.value === null || typeof props.value === 'undefined' || props.value === '') {
    return []
  }
  // 无论传进来是单值还是数组，统一转成 array<string>
  return Array.isArray(props.value) 
    ? props.value.map(item => String(item)) 
    : [String(props.value)]
})
</script>