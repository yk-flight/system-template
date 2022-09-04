import router from "@/router";
import store from "@/store";
import NProgress from "nprogress";

// 调整进度条的速度
NProgress.configure({ easing: "ease", speed: 500 });

// 白名单 => 用户未登录也可以进入的页面
const whiteList = ["/login", "/404", "/401", "/dashboard", "/profile"];

// ======================= 路由导航守卫 =======================
// to：当前位置，from：从哪来
router.beforeEach(async (to, from, next) => {
  // 开启页面进度条
  NProgress.start();
  // 修改网站的标题
  window.document.title = "后台管理系统 - " + to.meta.title;

  // 如果本地已经缓存token
  if (store.getters.token) {
    // 1. 如果用户已登录，则不允许进入 login
    if (to.path === "/login") {
      // 跳回首页
      next("/dashboard");
    } else {
      // 判断用户资料是否存在，如果不存在则获取用户信息
      if (!store.getters.hasUserInfo) {
        // 获取当前登录用户的设备
        store.dispatch("user/getUserAgent");
        // 获取用户的个人信息
        store.dispatch("user/getUserInfoById");
        // 获取用户可访问的页面
        store.dispatch("user/getAccessPath");

        const { permission } = await store.dispatch("user/getUserInfo");
        // 处理用户权限，筛选出需要添加的路由
        const filterRoutes = await store.dispatch(
          "permission/filterRoutes",
          permission
        );

        // 循环添加动态路由
        filterRoutes.forEach((item) => {
          router.options.routes[1].children.push(item);
        });

        //重新加载路由
        router.addRoutes(router.options.routes);
        // 动态路由添加完成后，需要进行一次主动跳转
        return next(to.path);
      }

      // 查看当前用户访问的路径是否为白名单当中的路径
      const res = whiteList.includes(to.path);
      // 如果用户访问的路径不是白名单的路径
      if (!res) {
        // 判断用户是否具有该页面的权限
        const accessPath = store.getters.accessPath;
        for (let i in accessPath) {
          if (to.path === accessPath[i]) {
            next();
            return;
          }
        }
        // 如果用户不具备该权限，则定义到401页面
        next("/401");
      }
      next();
    }
  } else {
    // 放行登录的请求和验证码请求
    if (to.path === "/login" || to.path === "/kaptcha") {
      next();
    } else {
      // 否则在没有登录之前的一切路由都重定向到登录页面
      next("/login");
    }
  }
});

router.afterEach((to, from) => {
  // 关闭进度条
  NProgress.done();
});
