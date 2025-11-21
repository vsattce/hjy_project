/**
 * 路由配置文件
 * 定义应用的所有路由规则
 */
import { createRouter, createWebHistory } from 'vue-router'

// 路由配置数组
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    redirect: '/system/dashboard'
  },
  {
    path: '/system',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/system/Dashboard.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'user',
        name: 'UserManage',
        component: () => import('@/views/system/UserManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'role',
        name: 'RoleManage',
        component: () => import('@/views/system/RoleManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'dept',
        name: 'DeptManage',
        component: () => import('@/views/system/DeptManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'post',
        name: 'PostManage',
        component: () => import('@/views/system/PostManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'menu',
        name: 'MenuManage',
        component: () => import('@/views/system/MenuManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'dict',
        name: 'DictManage',
        component: () => import('@/views/system/DictManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'config',
        name: 'ConfigManage',
        component: () => import('@/views/system/ConfigManage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'log',
        name: 'LogManage',
        component: () => import('@/views/system/LogManage.vue'),
        meta: { requiresAuth: true }
      }
    ]
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // 使用 HTML5 History 模式
  routes
})

// 路由守卫：检查登录状态
router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  
  // 如果访问登录页且已登录，跳转到首页
  if (to.path === '/login' && token) {
    next('/system/dashboard')
  }
  // 如果需要认证但未登录，跳转到登录页
  else if (to.meta.requiresAuth && !token) {
    next('/login')
  }
  // 其他情况正常访问
  else {
    next()
  }
})

export default router
