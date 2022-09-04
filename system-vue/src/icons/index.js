// 1. 导入所有的 svg 图标
// 2. 完成 SvgIcon 的全局注册
import SvgIcon from '@/components/SvgIcon'

// 1. 导入所有的 svg 图标
// 扫描 svg 文件夹，不含有子目录，要匹配所有以 .svg 结尾的文件
const svgRequire = require.context('./svg', false, /\.svg$/)
// 返回了一个 Require 函数，可以接收一个 request 的参数，用于 require 导入
// 该函数提供了三个属性，可以通过 svgRequire.keys() 获得所有 svg 图标
// 遍历图标，把图标作为 request 参数导入到 svgRequire 导入函数中，完成本地 svg 图标的导入
svgRequire.keys().forEach((svgIcon) => svgRequire(svgIcon))

// 2. 完成 SvgIcon 的全局注册
export default (app) => {
  app.component('svg-icon', SvgIcon)
}
