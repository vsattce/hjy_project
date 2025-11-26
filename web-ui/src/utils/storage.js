// src/utils/storage.js

const Key = {
  USERNAME: 'admin-username', // 统一管理 Key，防止冲突
  REMEMBER_ME: 'admin-remember-me'
}

/**
 * 存储用户名
 */
export const setUsername = (username) => {
  localStorage.setItem(Key.USERNAME, username)
}

/**
 * 获取用户名
 */
export const getUsername = () => {
  return localStorage.getItem(Key.USERNAME)
}

/**
 * 移除用户名
 */
export const removeUsername = () => {
  localStorage.removeItem(Key.USERNAME)
}

/**
 * 存储/获取记住我状态 (可选，如果你想连这个状态也存下来)
 */
export const setRememberMe = (isRemember) => {
  localStorage.setItem(Key.REMEMBER_ME, isRemember)
}

export const getRememberMe = () => {
  return localStorage.getItem(Key.REMEMBER_ME) === 'true'
}

export const removeRememberMe = () => {
  localStorage.removeItem(Key.REMEMBER_ME)
}