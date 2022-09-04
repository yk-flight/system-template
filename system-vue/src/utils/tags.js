// 不保存到标签页中的内容
const blackList = ['/login', '/404', '/401']

/**
 * 查看当前 path 是否为保存到标签页中的路由
 *
 * @param {*} path
 * @returns
 */
export function isTags(path) {
  return !blackList.includes(path)
}
