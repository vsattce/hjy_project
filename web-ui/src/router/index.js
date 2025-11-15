/**
 * 路由配置文件
 * 定义应用的所有路由规则
 */
import { createRouter, createWebHistory } from 'vue-router'

// 路由配置数组
const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue') // 懒加载，提升首屏加载速度
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/About.vue')
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // 使用 HTML5 History 模式
  routes
})

export default router
