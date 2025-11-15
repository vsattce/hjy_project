import request from '@/utils/request'

/**
 * 用户相关 API
 */

// 获取用户列表
export function getUserList(params) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params
  })
}

// 分页查询用户列表
export function getUserPage(params) {
  return request({
    url: '/system/user/page',
    method: 'get',
    params
  })
}

// 获取用户详情
export function getUserInfo(id) {
  return request({
    url: `/system/user/${id}`,
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data
  })
}

// 删除用户
export function deleteUser(ids) {
  return request({
    url: `/system/user/${ids}`,
    method: 'delete'
  })
}

// 保存或更新用户
export function saveOrUpdateUser(data) {
  return request({
    url: '/system/user/saveOrUpdate',
    method: 'post',
    data
  })
}
