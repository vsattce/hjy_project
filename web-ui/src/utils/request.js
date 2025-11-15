import axios from 'axios'
import config from '@/config'

// 创建 axios 实例
const service = axios.create({
  baseURL: config.apiBaseUrl,
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    // 例如：添加 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // 开发环境打印请求信息
    if (process.env.NODE_ENV === 'development') {
      console.log(`[${config.method.toUpperCase()}] ${config.url}`, config)
    }
    
    return config
  },
  error => {
    // 对请求错误做些什么
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    const res = response.data
    
    // 根据后端返回的状态码进行处理
    // 后端返回格式为 { code: 200, msg: '', data: {} }
    if (res.code !== undefined && res.code !== 200) {
      console.error('业务错误:', res.msg || '请求失败')
      return Promise.reject(new Error(res.msg || '请求失败'))
    }
    
    return res
  },
  error => {
    // 对响应错误做点什么
    console.error('响应错误:', error.message)
    
    if (error.response) {
      // 请求已发出，但服务器响应的状态码不在 2xx 范围内
      switch (error.response.status) {
        case 401:
          console.error('未授权，请重新登录')
          // 可以跳转到登录页
          break
        case 403:
          console.error('拒绝访问')
          break
        case 404:
          console.error('请求的资源不存在')
          break
        case 500:
          console.error('服务器内部错误')
          break
        default:
          console.error(`连接错误 ${error.response.status}`)
      }
    } else {
      console.error('网络连接异常，请检查网络')
    }
    
    return Promise.reject(error)
  }
)

export default service
