<template>
  <div class="login-container">
    <div class="login-content">
      <!-- 左侧装饰区域 -->
      <div class="login-banner">
        <div class="banner-content">
          <h1 class="system-title">后台管理系统</h1>
          <p class="system-desc">欢迎使用配置管理平台</p>
          <div class="feature-list">
            <div class="feature-item">
              <el-icon :size="24"><CircleCheck /></el-icon>
              <span>安全可靠</span>
            </div>
            <div class="feature-item">
              <el-icon :size="24"><Lightning /></el-icon>
              <span>高效便捷</span>
            </div>
            <div class="feature-item">
              <el-icon :size="24"><MostlyCloudy /></el-icon>
              <span>云端同步</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧登录表单 -->
      <div class="login-form-wrapper">
        <el-card class="login-box" shadow="never">
          <div class="login-header">
            <div class="logo-wrapper">
              <el-icon :size="48" color="#409EFF">
                <Lock />
              </el-icon>
            </div>
            <h2>系统登录</h2>
            <p class="welcome-text">欢迎回来，请登录您的账户</p>
          </div>
          
          <el-form :model="loginForm" ref="loginFormRef" class="login-form">
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                size="large"
                clearable
                @keyup.enter="handleLogin"
              >
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                show-password
                @keyup.enter="handleLogin"
              >
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            
            <el-form-item>
              <div class="form-footer">
                <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
                <a href="#" class="forgot-link">忘记密码？</a>
              </div>
            </el-form-item>
            
            <el-form-item>
              <el-button
                type="primary"
                size="large"
                style="width: 100%"
                :loading="loading"
                @click="handleLogin"
              >
                {{ loading ? '登录中...' : '立即登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="login-footer">
            <el-divider>其他登录方式</el-divider>
            <div class="social-login">
              <el-button circle>
                <el-icon :size="20"><ChatDotRound /></el-icon>
              </el-button>
              <el-button circle>
                <el-icon :size="20"><Message /></el-icon>
              </el-button>
              <el-button circle>
                <el-icon :size="20"><Promotion /></el-icon>
              </el-button>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter,useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '@/api/auth'
import { setToken } from '@/utils/auth'

const router = useRouter()
const route = useRoute()
// const path = route.query.redirect || '/'
const loginFormRef = ref(null)

const loginForm = reactive({
  username: '',
  password: '',
  rememberMe: false
})

const loading = ref(false)

const handleLogin = async () => {
  if (!loginForm.username || !loginForm.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  loading.value = true
  
  try {
    const response = await login(loginForm)
    
    if (response.code === 200 && response.token) {
      setToken(response.token)
      
      setRememberMe(loginForm.rememberMe)
      if (loginForm.rememberMe) {
        setUsername(loginForm.username)
      } else {
        removeUsername()
      }
      
      ElMessage.success('登录成功')
      // console.log(route.query.redirect)
      router.push({path: route.query.redirect || '/'});
      // .push({ path: this.redirect || "/" }).catch(()=>{});
    } else {
      ElMessage.error(response.msg || '登录失败，请检查用户名和密码')
    }
  } catch (error) {
    ElMessage.error(error.message || '登录失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  const savedUsername = getUsername()
  if (savedUsername) {
    loginForm.username = savedUsername
    loginForm.rememberMe = getRememberMe()
  }
})
</script>

<style lang="less" scoped>
@primary-color: #409EFF;
@primary-hover: #66b1ff;
@border-radius: 16px;
@box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);

.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    width: 500px;
    height: 500px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 50%;
    top: -200px;
    right: -200px;
  }

  &::after {
    content: '';
    position: absolute;
    width: 400px;
    height: 400px;
    background: rgba(255, 255, 255, 0.08);
    border-radius: 50%;
    bottom: -150px;
    left: -150px;
  }

  .login-content {
    display: flex;
    max-width: 1100px;
    width: 100%;
    background: white;
    border-radius: @border-radius;
    box-shadow: @box-shadow;
    overflow: hidden;
    position: relative;
    z-index: 1;

    .login-banner {
      flex: 1;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 60px 50px;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;

      &::before {
        content: '';
        position: absolute;
        width: 300px;
        height: 300px;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 50%;
        top: -100px;
        left: -100px;
      }

      .banner-content {
        position: relative;
        z-index: 1;
        color: white;

        .system-title {
          font-size: 42px;
          font-weight: 700;
          margin: 0 0 16px 0;
          line-height: 1.2;
        }

        .system-desc {
          font-size: 18px;
          opacity: 0.9;
          margin: 0 0 50px 0;
        }

        .feature-list {
          .feature-item {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 24px;
            font-size: 16px;
          }
        }
      }
    }

    .login-form-wrapper {
      flex: 1;
      padding: 60px 50px;
      display: flex;
      align-items: center;
      justify-content: center;

      .login-box {
        width: 100%;
        max-width: 420px;

        .login-header {
          text-align: center;
          margin-bottom: 32px;

          .logo-wrapper {
            margin-bottom: 16px;
            display: flex;
            justify-content: center;
          }

          h2 {
            margin: 0 0 8px 0;
            color: #303133;
            font-size: 28px;
            font-weight: 600;
          }

          .welcome-text {
            margin: 0;
            color: #909399;
            font-size: 14px;
          }
        }

        .login-form {
          .form-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;

            .forgot-link {
              color: @primary-color;
              text-decoration: none;
              font-size: 14px;
              transition: color 0.3s;

              &:hover {
                color: @primary-hover;
              }
            }
          }
        }

        .login-footer {
          margin-top: 24px;

          .social-login {
            display: flex;
            justify-content: center;
            gap: 16px;
            margin-top: 16px;
          }
        }
      }
    }
  }

  @media (max-width: 768px) {
    .login-content {
      flex-direction: column;

      .login-banner {
        padding: 40px 30px;

        .banner-content {
          .system-title {
            font-size: 32px;
          }

          .system-desc {
            font-size: 16px;
            margin-bottom: 30px;
          }
        }
      }

      .login-form-wrapper {
        padding: 40px 30px;
      }
    }
  }
}
</style>
