/**
 * Axios 请求封装
 * 统一处理请求和响应，包括：
 * - 自动添加 token
 * - 统一错误处理
 * - 请求/响应日志
 */
import axios from 'axios'
import config from '@/config'

/**
 * 创建 axios 实例
 * baseURL: API 基础地址，从配置文件读取
 * timeout: 请求超时时间 15 秒
 */
const service = axios.create({
  baseURL: config.apiBaseUrl,
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

/**
 * 请求拦截器
 * 在请求发送前统一处理，如添加 token
 */
service.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token 并添加到请求头
    // const token = localStorage.getItem('token')
    // if (token) {
    //   config.headers.Authorization = `Bearer ${token}`
    // }
    
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 * 统一处理响应数据和错误
 * 后端返回格式：{ code: 200, msg: '操作成功', data: {...} }
 */
service.interceptors.response.use(
  response => {
    const res = response.data
    
    // 业务状态码判断，code !== 200 表示业务错误
    if (res.code !== undefined && res.code !== 200) {
      console.error('业务错误:', res.msg || '请求失败')
      return Promise.reject(new Error(res.msg || '请求失败'))
    }
    
    return res
  },
  error => {
    console.error('响应错误:', error.message)
    
    // HTTP 状态码错误处理
    if (error.response) {
      switch (error.response.status) {
        case 401:
          console.error('未授权，请重新登录')
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
      console.error('网络连接异常，请检查网络: ',error)
    }
    
    return Promise.reject(error)
  }
)

export default service
