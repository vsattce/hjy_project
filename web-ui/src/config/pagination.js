/**
 * 分页配置
 */

// 分页大小选项
export const PAGE_SIZES = [10, 20, 50, 100]

// 默认分页大小
export const DEFAULT_PAGE_SIZE = 10

// 默认分页布局
export const PAGINATION_LAYOUT = 'total, sizes, prev, pager, next, jumper'

// 创建默认分页对象
export function createPagination(pageSize = DEFAULT_PAGE_SIZE) {
  return {
    page: 1,
    pageSize,
    total: 0
  }
}
