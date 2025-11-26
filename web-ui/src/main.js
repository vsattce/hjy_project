/**
 * 应用入口文件
 * 创建 Vue 应用实例并挂载到 DOM
 */
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 1. 引入所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import { createPinia } from 'pinia'

import DictTag from '@/components/DictTag'

// 2. 引入权限控制脚本 (必须！)
import './permission'

const app = createApp(App)

// 1. 先注册 Pinia (必须第一位！确保所有 Store 都能被正常初始化)
app.use(createPinia())

// 2. 再注册路由 (路由守卫可能会用到 Store)
app.use(router)

// 3. 最后注册 UI 组件库
app.use(ElementPlus, { locale: zhCn })

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.component('DictTag',DictTag)

// 创建应用实例，注册路由和 Element Plus，挂载到 #app 元素
app.mount('#app')
