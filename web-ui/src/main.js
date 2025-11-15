/**
 * 应用入口文件
 * 创建 Vue 应用实例并挂载到 DOM
 */
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// 创建应用实例，注册路由插件，挂载到 #app 元素
createApp(App).use(router).mount('#app')
