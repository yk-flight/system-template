/**
 * 判断是否为外部资源
 * @param {*} path
 */
export function isExternal(path) {
  // 只要是以 https、mailto、tel开头的则认为是外部资源
  return /^(https?:|mailto:|tel:)/.test(path)
}
