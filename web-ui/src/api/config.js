/**
 * 配置管理 API
 * 对应后端：SysConfigController (继承 BaseController)
 * 基础路径：/system/config
 */
import request from '@/utils/request'

/**
 * 获取配置列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回配置列表数组
 */
export function getConfigList(params) {
  return request({
    url: '/system/config/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询配置列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getConfigPage(params) {
  return request({
    url: '/system/config/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 获取配置详情
 * @param {Number} id - 配置ID
 * @returns {Promise} 返回配置详细信息
 */
export function getConfigInfo(id) {
  return request({
    url: `/system/config/${id}`,
    method: 'get'
  })
}

/**
 * 新增配置
 * @param {Object} data - 配置信息
 * @returns {Promise}
 */
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data
  })
}

/**
 * 修改配置信息
 * @param {Object} data - 配置信息（必须包含 configId）
 * @returns {Promise}
 */
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data
  })
}

/**
 * 批量删除配置
 * @param {String|Array} ids - 配置ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteConfig(ids) {
  return request({
    url: `/system/config/${ids}`,
    method: 'delete'
  })
}

/**
 * 保存或更新配置（有 ID 则更新，无 ID 则新增）
 * @param {Object} data - 配置信息
 * @returns {Promise}
 */
export function saveOrUpdateConfig(data) {
  return request({
    url: '/system/config/saveOrUpdate',
    method: 'post',
    data
  })
}
