/**
 * 字典管理 API
 * 对应后端：SysDictTypeController 和 SysDictDataController
 * 基础路径：/system/dict/type 和 /system/dict/data
 */
import request from '@/utils/request'

// ==================== 字典类型 ====================

/**
 * 获取字典类型列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回字典类型列表数组
 */
export function getDictTypeList(params) {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询字典类型列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getDictTypePage(params) {
  return request({
    url: '/system/dict/type/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 获取字典类型详情
 * @param {Number} id - 字典类型ID
 * @returns {Promise} 返回字典类型详细信息
 */
export function getDictTypeInfo(id) {
  return request({
    url: `/system/dict/type/${id}`,
    method: 'get'
  })
}

/**
 * 新增字典类型
 * @param {Object} data - 字典类型信息
 * @returns {Promise}
 */
export function addDictType(data) {
  return request({
    url: '/system/dict/type',
    method: 'post',
    data
  })
}

/**
 * 修改字典类型信息
 * @param {Object} data - 字典类型信息（必须包含 dictId）
 * @returns {Promise}
 */
export function updateDictType(data) {
  return request({
    url: '/system/dict/type',
    method: 'put',
    data
  })
}

/**
 * 批量删除字典类型
 * @param {String|Array} ids - 字典类型ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteDictType(ids) {
  return request({
    url: `/system/dict/type/${ids}`,
    method: 'delete'
  })
}

// ==================== 字典数据 ====================

/**
 * 获取字典数据列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回字典数据列表数组
 */
export function getDictDataList(params) {
  return request({
    url: '/system/dict/data/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询字典数据列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getDictDataPage(params) {
  return request({
    url: '/system/dict/data/page',
    method: 'get',
    params
  })
}

/**
 * 根据字典类型获取字典数据
 * @param {String} dictType - 字典类型
 * @returns {Promise} 返回字典数据列表
 */
export function getDictDataByType(dictType) {
  return request({
    url: `/system/dict/data/type/${dictType}`,
    method: 'get'
  })
}

/**
 * 根据 ID 获取字典数据详情
 * @param {Number} id - 字典数据ID
 * @returns {Promise} 返回字典数据详细信息
 */
export function getDictDataInfo(id) {
  return request({
    url: `/system/dict/data/${id}`,
    method: 'get'
  })
}

/**
 * 新增字典数据
 * @param {Object} data - 字典数据信息
 * @returns {Promise}
 */
export function addDictData(data) {
  return request({
    url: '/system/dict/data',
    method: 'post',
    data
  })
}

/**
 * 修改字典数据信息
 * @param {Object} data - 字典数据信息（必须包含 dictCode）
 * @returns {Promise}
 */
export function updateDictData(data) {
  return request({
    url: '/system/dict/data',
    method: 'put',
    data
  })
}

/**
 * 批量删除字典数据
 * @param {String|Array} ids - 字典数据ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deleteDictData(ids) {
  return request({
    url: `/system/dict/data/${ids}`,
    method: 'delete'
  })
}
