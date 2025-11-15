// 配置文件
export default {
  // API 基础地址
  apiBaseUrl: process.env.VUE_APP_API_BASE_URL,
  
  // 应用标题
  title: process.env.VUE_APP_TITLE,
  
  // 版本号
  version: process.env.VUE_APP_VERSION,
  
  // 是否启用 mock
  enableMock: process.env.VUE_APP_ENABLE_MOCK === 'true',
  
  // 是否为生产环境
  isProduction: process.env.NODE_ENV === 'production',
  
  // 是否为开发环境
  isDevelopment: process.env.NODE_ENV === 'development'
}
