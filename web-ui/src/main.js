/**
 * 应用入口文件
 * 创建 Vue 应用实例并挂载到 DOM
 */
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 创建应用实例，注册路由和 Element Plus，挂载到 #app 元素
app.use(router).use(ElementPlus, { locale: zhCn }).mount('#app')
