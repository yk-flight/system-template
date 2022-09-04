import { routes, privateRoutes } from "@/router";

export default {
  namespaced: true,
  state: () => ({
    // 路由表，初始时所拥有的路由表
    routes: routes,
  }),
  mutations: {
    // 增加路由
    setRoutes(state, newRoutes) {
      state.routes = [...routes, ...newRoutes];
    },
  },
  actions: {
    /**
     * 根据权限数据进行路由的筛选
     *
     * @param {*} context
     * @param {*} menus 权限数据
     */
    filterRoutes(context, menus) {
      // 筛选之后，获取到需要通过 addRoute 进行添加的路由表数组
      const routes = [];

      menus.forEach((key) => {
        routes.push(...privateRoutes.filter((item) => item.name === key));
      });

      // 所有不匹配的路由，全部进入 404 路由配置页面
      // 注意：该配置必须在所有路由指定之后
      routes.push({
        path: "/:catchAll(.*)",
        redirect: "/404",
      });
      context.commit("setRoutes", routes);
      return routes;
    },
  },
};
