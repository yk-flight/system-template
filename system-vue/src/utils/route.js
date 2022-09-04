import path from "path";

/**
 * 所有的子集路由
 */
const getChildrenRoutes = (routes) => {
  const result = [];
  routes.forEach((route) => {
    // 如果当前 route 包含子集并且子集的长度大于0
    if (route.children && route.children.length > 0) {
      result.push(...route.children);
    }
  });
  return result;
};

/**
 * 处理脱离层级的路由
 */
export const filterRoutes = (routes) => {
  // 1. 获取到所有的子集路由
  const childrenRoutes = getChildrenRoutes(routes);
  // 2. 根据子集路由进行查重操作
  return routes.filter((route) => {
    // 根据 route 在 childrenRoutes 中进行查重，把所有重复的路由表剔除
    return !childrenRoutes.find((childrenRoute) => {
      return childrenRoute.path === route.path;
    });
  });
};

/**
 * 判断当前数据是否为空对象
 *
 * @param {*} data
 * @returns
 */
function isNull(data) {
  if (!data) return true;
  if (JSON.stringify(data) === "{}") return true;
  if (JSON.stringify(data) === "[]") return true;
  return false;
}

/**
 * 根据 routes 数据，返回对应的 menu 规则的数据
 */
export const generateMenus = (routes, basePath = "") => {
  const result = [];
  // 在该方法中传来的数据应当是被过滤后的数据，将不满足 meta && meta.title && meta.icon 的数据剔除掉
  routes.forEach((item) => {
    // 对于不存在 children 和不存在 meta 的数据直接return
    if (isNull(item.children) && isNull(item.meta)) return;
    // 存在 children ，不存在 meta ， 迭代 generateMenus
    if (isNull(item.meta) && !isNull(item.children)) {
      result.push(...generateMenus(item.children));
      return;
    }
    // 不存在 children ，存在 meta 或者存在 children 也存在 meta
    // 因为最终的 menu 需要进行跳转，此时需要合并 path
    const routePath = path.resolve(basePath, item.path);

    // 路由分离之后，可能存在同名父路由的情况
    let route = result.find((item) => item.path === routePath);

    // 当前路由尚未加入到 result
    if (!route) {
      route = {
        ...item,
        path: routePath,
        children: [],
      };
      // icon 和 title 必须同时存在才可以进行添加路由
      if (route.meta.icon && route.meta.title) {
        result.push(route);
      }
    }

    // 存在 children 并且存在 meta
    if (!isNull(item.children)) {
      route.children.push(...generateMenus(item.children, route.path));
    }
  });
  return result;
};
