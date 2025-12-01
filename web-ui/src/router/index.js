/**
 * 路由配置文件
 * 定义应用的所有路由规则
 */
import { createRouter, createWebHistory } from "vue-router";
// import { getToken } from '@/utils/auth'
/* Layout */
import Layout from "@/layout/index.vue";

// 公共路由
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect"),
      },
    ],
  },
  {
    path: "/login",
    component: () => import("@/views/login.vue"),
    hidden: true,
  },
  {
    path: "/404",
    component: () => import("@/views/error/404"),
    hidden: true,
  },
  {
    path: "",
    component: Layout,
    redirect: "/dashboard",
    children: [
      {
        path: "/dashboard",
        component: () => import("@/views/dashboard/index.vue"),
        name: "Dashboard",
        meta: { title: "首页", icon: "dashboard", affix: true },
      },
    ],
  },
];
// 路由配置数组
export const dynamicRoutes = [
  {
    path: "/system/user-auth",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "role/:userId(\\d+)",
        component: () => import("@/views/system/user/authRole"),
        name: "AuthRole",
        meta: { title: "分配角色", activeMenu: "/system/user" },
      },
    ],
  },
  {
    path: "/system/role-auth",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "user/:roleId(\\d+)",
        component: () => import("@/views/system/role/authUser"),
        name: "AuthUser",
        meta: { title: "分配用户", activeMenu: "/system/role" },
      },
    ],
  },
  // {
  //   path: '/login',
  //   name: 'Login',
  //   component: () => import('@/views/login.vue'),
  //   meta: { requiresAuth: false }
  // },
  // {
  //   path: '/',
  //   redirect: '/system/dashboard'
  // },
  // {
  //   path: '/system',
  //   component: () => import('@/layout/index.vue'),
  //   meta: { requiresAuth: true },
  //   children: [
  //     {
  //       path: 'dashboard',
  //       name: 'Dashboard',
  //       component: () => import('@/views/system/dashboard/index.vue'),
  //       meta: { requiresAuth: true }
  //     }
  //   ]
  // }
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // 使用 HTML5 History 模式
  routes: constantRoutes,
});

export default router;
