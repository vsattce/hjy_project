/**
 * 认证相关 API
 * 对应后端登录接口
 */
import request from '@/utils/request'

/**
 * 用户登录
 * @param {Object} data - 登录信息 { username, password }
 * @returns {Promise} 返回 token 等信息
 */
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

/**
 * 用户登出
 * @returns {Promise}
 */
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

/**
 * 获取当前用户信息
 * @returns {Promise}
 */
export function getUserInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}
