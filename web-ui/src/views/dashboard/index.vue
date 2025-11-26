<template>
  <div class="dashboard">
    <!-- 欢迎卡片 -->
    <el-card class="welcome-card" shadow="never">
      <div class="welcome-content">
        <div class="welcome-text">
          <h2>欢迎回来，{{ username }}！</h2>
          <p>今天是 {{ currentDate }}，祝您工作愉快</p>
        </div>
        <div class="welcome-icon">
          <el-icon :size="80" color="#409eff"><Sunny /></el-icon>
        </div>
      </div>
    </el-card>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon user-icon">
              <el-icon :size="40"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon role-icon">
              <el-icon :size="40"><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.roleCount }}</div>
              <div class="stat-label">角色总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon dept-icon">
              <el-icon :size="40"><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.deptCount }}</div>
              <div class="stat-label">部门总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon menu-icon">
              <el-icon :size="40"><Menu /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.menuCount }}</div>
              <div class="stat-label">菜单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷入口 -->
    <el-card class="quick-links" shadow="never">
      <template #header>
        <div class="card-header">
          <span>快捷入口</span>
        </div>
      </template>
      <el-row :gutter="20">
        <el-col :span="4" v-for="link in quickLinks" :key="link.path">
          <div class="quick-link-item" @click="goTo(link.path)">
            <el-icon :size="32" :color="link.color">
              <component :is="link.icon" />
            </el-icon>
            <div class="link-name">{{ link.name }}</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 系统信息 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>系统信息</span>
            </div>
          </template>
          <div class="info-list">
            <div class="info-item">
              <span class="info-label">系统名称：</span>
              <span class="info-value">系统管理平台</span>
            </div>
            <div class="info-item">
              <span class="info-label">系统版本：</span>
              <span class="info-value">v1.0.0</span>
            </div>
            <div class="info-item">
              <span class="info-label">服务器时间：</span>
              <span class="info-value">{{ serverTime }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">运行环境：</span>
              <span class="info-value">Vue 3 + Element Plus</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>最近登录</span>
            </div>
          </template>
          <div class="info-list">
            <div class="info-item">
              <span class="info-label">登录时间：</span>
              <span class="info-value">{{ loginTime }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">登录IP：</span>
              <span class="info-value">{{ loginIp }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">浏览器：</span>
              <span class="info-value">{{ browser }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">操作系统：</span>
              <span class="info-value">{{ os }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
// import request from '@/utils/request'

const router = useRouter()
const username = ref(localStorage.getItem('username') || '管理员')
const currentDate = ref('')
const serverTime = ref('')
const loginTime = ref('')
const loginIp = ref('127.0.0.1')
const browser = ref('Chrome')
const os = ref('Windows 10')

const stats = reactive({
  userCount: 0,
  roleCount: 0,
  deptCount: 0,
  menuCount: 0
})

const quickLinks = [
  { name: '用户管理', path: '/system/user', icon: 'User', color: '#409eff' },
  { name: '角色管理', path: '/system/role', icon: 'UserFilled', color: '#67c23a' },
  { name: '部门管理', path: '/system/dept', icon: 'OfficeBuilding', color: '#e6a23c' },
  { name: '岗位管理', path: '/system/post', icon: 'Briefcase', color: '#f56c6c' },
  { name: '菜单管理', path: '/system/menu', icon: 'Menu', color: '#909399' },
  { name: '配置管理', path: '/system/config', icon: 'Setting', color: '#409eff' }
]

const updateTime = () => {
  const now = new Date()
  currentDate.value = now.toLocaleDateString('zh-CN', { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric',
    weekday: 'long'
  })
  serverTime.value = now.toLocaleString('zh-CN')
}

const loadStats = async () => {
  try {
    // 这里可以调用实际的统计接口
    // 暂时使用模拟数据
    stats.userCount = 156
    stats.roleCount = 8
    stats.deptCount = 12
    stats.menuCount = 45
  } catch (error) {
    console.error('加载统计数据失败', error)
  }
}

const goTo = (path) => {
  router.push(path)
}

onMounted(() => {
  updateTime()
  setInterval(updateTime, 1000)
  loadStats()
  
  // 设置登录时间
  loginTime.value = new Date().toLocaleString('zh-CN')
  
  // 获取浏览器信息
  const ua = navigator.userAgent
  if (ua.indexOf('Chrome') > -1) browser.value = 'Chrome'
  else if (ua.indexOf('Firefox') > -1) browser.value = 'Firefox'
  else if (ua.indexOf('Safari') > -1) browser.value = 'Safari'
  else if (ua.indexOf('Edge') > -1) browser.value = 'Edge'
  
  // 获取操作系统
  if (ua.indexOf('Windows') > -1) os.value = 'Windows'
  else if (ua.indexOf('Mac') > -1) os.value = 'MacOS'
  else if (ua.indexOf('Linux') > -1) os.value = 'Linux'
})
</script>

<style lang="less" scoped>
.dashboard {
  .welcome-card {
    margin-bottom: 20px;

    .welcome-content {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .welcome-text {
        h2 {
          margin: 0 0 10px 0;
          color: #303133;
          font-size: 24px;
        }

        p {
          margin: 0;
          color: #909399;
          font-size: 14px;
        }
      }

      .welcome-icon {
        opacity: 0.8;
      }
    }
  }

  .stats-row {
    margin-bottom: 20px;

    .stat-card {
      cursor: pointer;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-5px);
      }

      .stat-content {
        display: flex;
        align-items: center;
        gap: 20px;

        .stat-icon {
          width: 70px;
          height: 70px;
          border-radius: 10px;
          display: flex;
          align-items: center;
          justify-content: center;
          color: white;

          &.user-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          }

          &.role-icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
          }

          &.dept-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
          }

          &.menu-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
          }
        }

        .stat-info {
          flex: 1;

          .stat-value {
            font-size: 28px;
            font-weight: bold;
            color: #303133;
            margin-bottom: 5px;
          }

          .stat-label {
            font-size: 14px;
            color: #909399;
          }
        }
      }
    }
  }

  .quick-links {
    margin-bottom: 20px;

    .quick-link-item {
      text-align: center;
      padding: 20px;
      cursor: pointer;
      border-radius: 8px;
      transition: all 0.3s;

      &:hover {
        background: #f5f7fa;
        transform: translateY(-3px);
      }

      .link-name {
        margin-top: 10px;
        font-size: 14px;
        color: #606266;
      }
    }
  }

  .card-header {
    font-weight: bold;
    color: #303133;
  }

  .info-list {
    .info-item {
      display: flex;
      padding: 12px 0;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .info-label {
        width: 120px;
        color: #909399;
        font-size: 14px;
      }

      .info-value {
        flex: 1;
        color: #606266;
        font-size: 14px;
      }
    }
  }
}
</style>
