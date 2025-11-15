<template>
  <div class="home">
    <h1>首页</h1>
    <p>欢迎来到首页</p>
    <div class="config-info">
      <h3>当前环境配置：</h3>
      <p>环境：{{ config.isProduction ? '生产环境' : '开发环境' }}</p>
      <p>API 地址：{{ config.apiBaseUrl }}</p>
      <p>标题：{{ config.title }}</p>
      <p>版本：{{ config.version }}</p>
      <p>Mock 数据：{{ config.enableMock ? '启用' : '禁用' }}</p>
    </div>
    <div class="user-section">
      <button @click="fetchUserList" :disabled="loading">
        {{ loading ? '加载中...' : '查询用户列表' }}
      </button>
      
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
      
      <div v-if="userList.length > 0" class="user-list">
        <h3>用户列表：</h3>
        <table>
          <thead>
            <tr>
              <th>用户ID</th>
              <th>用户账号</th>
              <th>用户昵称</th>
              <th>邮箱</th>
              <th>手机号</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in userList" :key="user.userId">
              <td>{{ user.userId }}</td>
              <td>{{ user.userName }}</td>
              <td>{{ user.nickName }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.phonenumber }}</td>
              <td>
                <span :class="user.status === '0' ? 'status-normal' : 'status-disabled'">
                  {{ user.status === '0' ? '正常' : '停用' }}
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <router-link to="/about">关于页面</router-link>
  </div>
</template>

<script>
import config from '@/config'
import { getUserList } from '@/api/user'

export default {
  name: 'Home',
  data() {
    return {
      config,        // 全局配置对象
      userList: [],  // 用户列表数据
      loading: false, // 加载状态
      error: null    // 错误信息
    }
  },
  methods: {
    /**
     * 获取用户列表
     * 调用后端接口：GET /system/user/list
     */
    async fetchUserList() {
      this.loading = true
      this.error = null
      
      try {
        // 调用 API，后端返回格式：{ code: 200, msg: '操作成功', data: [...] }
        const response = await getUserList()
        
        if (response.code === 200) {
          this.userList = response.data || []
        } else {
          this.error = response.msg || '获取用户列表失败'
        }
      } catch (err) {
        this.error = err.message || '获取用户列表失败'
        console.error('获取用户列表失败:', err)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
}

.config-info {
  margin: 20px 0;
  padding: 15px;
  background: #f5f5f5;
  border-radius: 5px;
  text-align: left;
  max-width: 500px;
  margin-left: auto;
  margin-right: auto;
}

.config-info h3 {
  margin-top: 0;
}

.config-info p {
  margin: 8px 0;
}

.user-section {
  margin: 30px 0;
}

.user-section button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.user-section button:hover:not(:disabled) {
  background-color: #35a372;
}

.user-section button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.error-message {
  margin-top: 15px;
  padding: 10px;
  background-color: #fee;
  color: #c33;
  border-radius: 5px;
  border: 1px solid #fcc;
}

.user-list {
  margin-top: 20px;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.user-list table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.user-list th,
.user-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.user-list th {
  background-color: #42b983;
  color: white;
  font-weight: bold;
}

.user-list tbody tr:hover {
  background-color: #f5f5f5;
}

.status-normal {
  color: #67c23a;
  font-weight: bold;
}

.status-disabled {
  color: #f56c6c;
  font-weight: bold;
}
</style>
