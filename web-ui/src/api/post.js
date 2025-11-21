/**
 * 岗位管理 API
 * 对应后端：SysPostController (继承 BaseController)
 * 基础路径：/system/post
 */
import request from '@/utils/request'

/**
 * 获取岗位列表（不分页）
 * @param {Object} params - 查询参数
 * @returns {Promise} 返回岗位列表数组
 */
export function getPostList(params) {
  return request({
    url: '/system/post/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询岗位列表
 * @param {Object} params - 分页参数 { current: 页码, size: 每页数量 }
 * @returns {Promise} 返回分页数据
 */
export function getPostPage(params) {
  return request({
    url: '/system/post/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 获取岗位详情
 * @param {Number} id - 岗位ID
 * @returns {Promise} 返回岗位详细信息
 */
export function getPostInfo(id) {
  return request({
    url: `/system/post/${id}`,
    method: 'get'
  })
}

/**
 * 新增岗位
 * @param {Object} data - 岗位信息
 * @returns {Promise}
 */
export function addPost(data) {
  return request({
    url: '/system/post',
    method: 'post',
    data
  })
}

/**
 * 修改岗位信息
 * @param {Object} data - 岗位信息（必须包含 postId）
 * @returns {Promise}
 */
export function updatePost(data) {
  return request({
    url: '/system/post',
    method: 'put',
    data
  })
}

/**
 * 批量删除岗位
 * @param {String|Array} ids - 岗位ID，多个用逗号分隔或数组
 * @returns {Promise}
 */
export function deletePost(ids) {
  return request({
    url: `/system/post/${ids}`,
    method: 'delete'
  })
}
