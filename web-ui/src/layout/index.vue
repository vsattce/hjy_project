<template>
  <div class="main-layout">
    <el-container direction="vertical">
      <!-- 顶部导航栏 -->
      <el-header class="header">
        <div class="header-left">
          <h2>系统管理平台</h2>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-icon>
                <User />
              </el-icon>
              <span>{{ username }}</span>
              <el-icon>
                <ArrowDown />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">
                  <el-icon>
                    <SwitchButton />
                  </el-icon>
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主体容器 -->
      <el-container class="main-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="sidebar">
          <el-menu :default-active="activeMenu" class="menu" router unique-opened background-color="#fff"
            text-color="#303133" active-text-color="#409eff">
            <sidebar-item v-for="route in permissionStore.routes" 
              :key="route.path" 
              :item="route"
              :base-path="route.path">
            </sidebar-item>
          </el-menu>
        </el-aside>

        <!-- 右侧内容区 -->
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { logout } from '@/api/auth'
// import Sidebar from './components/Sidebar/index.vue'
import SidebarItem from './components/Sidebar/SidebarItem.vue';
import {usePermissionStore} from '@/store/modules/permission';

const router = useRouter()
const route = useRoute()

const permissionStore = usePermissionStore()
const activeMenu = computed(() => route.path)

const username = ref(localStorage.getItem('username') || '管理员')

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    try {
      await logout()
    } catch (error) {
      console.error('退出登录失败:', error)
    }
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    ElMessage.success('退出成功')
    router.push('/login')
  } catch (error) {
    // 用户取消
  }
}
</script>

<style lang="less" scoped>
.main-layout {
  height: 100vh;
  width: 100vw;
  overflow: hidden;

  >.el-container {
    height: 100%;
  }

  .header {
    background: #409eff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 0 30px;
    height: 60px !important;

    .header-left {
      h2 {
        margin: 0;
        color: white;
        font-size: 20px;
      }
    }

    .header-right {
      .user-info {
        display: flex;
        align-items: center;
        gap: 8px;
        color: white;
        cursor: pointer;
        padding: 8px 16px;
        border-radius: 4px;
        transition: background 0.3s;

        &:hover {
          background: rgba(255, 255, 255, 0.1);
        }

        .el-icon {
          font-size: 16px;
        }
      }
    }
  }

  .main-container {
    height: calc(100vh - 60px);
    background: #f0f2f5;

    .sidebar {
      background: white;
      box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
      height: 100%;
      overflow-y: auto;

      .menu {
        border-right: none;
        height: 100%;
      }
    }

    .main-content {
      padding: 20px;
      overflow-y: auto;
      height: 100%;
    }
  }
}
</style>
