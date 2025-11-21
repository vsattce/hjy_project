/**
 * 角色管理 API
 * 对应后端：SysRoleController (继承 BaseController)
 * 基础路径：/system/role
 */
import request from '@/utils/request'

/**
 * 获取角色列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回角色列表数组
 */
export function getRoleList(params) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询角色列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getRolePage(params) {
  return request({
    url: '/system/role/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 获取角色详情
 * @param {Number} id - 角色ID
 * @returns {Promise} 返回角色详细信息
 */
export function getRoleInfo(id) {
  return request({
    url: `/system/role/${id}`,
    method: 'get'
  })
}

/**
 * 新增角色
 * @param {Object} data - 角色信息
 * @returns {Promise}
 */
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data
  })
}

/**
 * 修改角色信息
 * @param {Object} data - 角色信息（必须包含 roleId）
 * @returns {Promise}
 */
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data
  })
}

/**
 * 批量删除角色
 * @param {String|Array} ids - 角色ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteRole(ids) {
  return request({
    url: `/system/role/${ids}`,
    method: 'delete'
  })
}
