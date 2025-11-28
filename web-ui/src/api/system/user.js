/**
 * 用户管理 API
 * 对应后端：SysUserController (继承 BaseController)
 * 基础路径：/system/user
 */
import request from '@/utils/request'
import { parseStrEmpty } from '@/utils/hjy'

/**
 * 获取用户列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回用户列表数组
 */
export function getUserList(params) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询用户列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getUserPage(params) {
  return request({
    url: '/system/user/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 获取用户详情
 * @param {Number} id - 用户ID
 * @returns {Promise} 返回用户详细信息
 */
// export function getUserInfo(id) {
//   return request({
//     url: `/system/user/${id}`,
//     method: 'get'
//   })
// }
/**
 * 根据 ID 获取用户详情
 * @param {Number} id - 用户ID
 * @returns {Promise} 返回用户详细信息
 */
export function getUserInfo(id) {
  return request({
    url: `/system/user/${parseStrEmpty(id)}`,
    method: 'get'
  })
}

/**
 * 新增用户
 * @param {Object} data - 用户信息
 * @returns {Promise}
 */
export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

/**
 * 修改用户信息
 * @param {Object} data - 用户信息（必须包含 userId）
 * @returns {Promise}
 */
export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data
  })
}

/**
 * 批量删除用户
 * @param {String|Array} ids - 用户ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteUser(ids) {
  return request({
    url: `/system/user/${ids}`,
    method: 'delete'
  })
}
