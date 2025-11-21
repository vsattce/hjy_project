/**
 * 菜单管理 API
 * 对应后端：SysMenuController (继承 BaseController)
 * 基础路径：/system/menu
 */
import request from '@/utils/request'

/**
 * 获取菜单列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回菜单列表数组
 */
export function getMenuList(params) {
  return request({
    url: '/system/menu/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询菜单列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getMenuPage(params) {
  return request({
    url: '/system/menu/page',
    method: 'get',
    params
  })
}

/**
 * 根据父ID获取菜单树形结构
 * @param {Number} parentId - 父菜单ID，默认0表示根节点
 * @returns {Promise} 返回菜单树
 */
export function getMenuTreeByParentId(parentId = 0) {
  return request({
    url: '/system/menu/list4treeByParentId',
    method: 'get',
    params: { parentId }
  })
}

/**
 * 根据ID获取菜单树形结构
 * @param {Number} id - 菜单ID
 * @returns {Promise} 返回菜单树
 */
export function getMenuTreeById(id) {
  return request({
    url: '/system/menu/list4treeById',
    method: 'get',
    params: { id }
  })
}

/**
 * 根据 ID 获取菜单详情
 * @param {Number} id - 菜单ID
 * @returns {Promise} 返回菜单详细信息
 */
export function getMenuInfo(id) {
  return request({
    url: `/system/menu/${id}`,
    method: 'get'
  })
}

/**
 * 新增菜单
 * @param {Object} data - 菜单信息
 * @returns {Promise}
 */
export function addMenu(data) {
  return request({
    url: '/system/menu',
    method: 'post',
    data
  })
}

/**
 * 修改菜单信息
 * @param {Object} data - 菜单信息（必须包含 menuId）
 * @returns {Promise}
 */
export function updateMenu(data) {
  return request({
    url: '/system/menu',
    method: 'put',
    data
  })
}

/**
 * 批量删除菜单
 * @param {String|Array} ids - 菜单ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteMenu(ids) {
  return request({
    url: `/system/menu/${ids}`,
    method: 'delete'
  })
}
