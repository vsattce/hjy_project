/**
 * 全局配置文件
 * 根据环境变量自动加载不同的配置
 * 配置文件位置：
 * - .env (所有环境通用)
 * - .env.development (开发环境)
 * - .env.production (生产环境)
 */
export default {
  // API 基础地址，开发环境：http://localhost:8080/api，生产环境：https://api.example.com
  apiBaseUrl: process.env.VUE_APP_API_BASE_URL,
  
  // 应用标题
  title: process.env.VUE_APP_TITLE,
  
  // 版本号
  version: process.env.VUE_APP_VERSION,
  
  // 是否启用 mock 数据
  enableMock: process.env.VUE_APP_ENABLE_MOCK === 'true',
  
  // 是否为生产环境
  isProduction: process.env.NODE_ENV === 'production',
  
  // 是否为开发环境
  isDevelopment: process.env.NODE_ENV === 'development'
}
