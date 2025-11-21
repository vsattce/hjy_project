/**
 * 部门管理 API
 * 对应后端：SysDeptController (继承 BaseController)
 * 基础路径：/system/dept
 */
import request from '@/utils/request'

/**
 * 获取部门列表（不分页，树形结构）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回部门列表数组
 */
export function getDeptList(params) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params
  })
}

/**
 * 获取部门树形结构（如果后端有实现）
 * @returns {Promise} 返回部门树
 */
export function getDeptTree() {
  return request({
    url: '/system/dept/tree',
    method: 'get'
  })
}

/**
 * 根据父ID获取部门树形结构
 * @param {Number} parentId - 父部门ID，默认0表示根节点
 * @returns {Promise} 返回部门树
 */
export function getDeptTreeByParentId(parentId = 0) {
  return request({
    url: '/system/dept/list4treeByParentId',
    method: 'get',
    params: { parentId }
  })
}

/**
 * 根据 ID 获取部门详情
 * @param {Number} id - 部门ID
 * @returns {Promise} 返回部门详细信息
 */
export function getDeptInfo(id) {
  return request({
    url: `/system/dept/${id}`,
    method: 'get'
  })
}

/**
 * 新增部门
 * @param {Object} data - 部门信息
 * @returns {Promise}
 */
export function addDept(data) {
  return request({
    url: '/system/dept',
    method: 'post',
    data
  })
}

/**
 * 修改部门信息
 * @param {Object} data - 部门信息（必须包含 deptId）
 * @returns {Promise}
 */
export function updateDept(data) {
  return request({
    url: '/system/dept',
    method: 'put',
    data
  })
}

/**
 * 批量删除部门
 * @param {String|Array} ids - 部门ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteDept(ids) {
  return request({
    url: `/system/dept/${ids}`,
    method: 'delete'
  })
}
