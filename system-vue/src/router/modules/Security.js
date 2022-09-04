import Main from "@/layout/Main.vue";

export default {
  path: "/security",
  component: Main,
  redirect: "/security/menus",
  name: "security",
  meta: {
    title: "权限管理",
    icon: "security",
  },
  children: [
    {
      path: "/security/menus",
      name: "menus",
      component: () => import("@/views/security/Menus.vue"),
      meta: {
        title: "菜单列表",
        icon: "menu",
      },
    },
    {
      path: "/security/interface",
      name: "swagger ",
      component: () => import("@/views/security/Interface.vue"),
      meta: {
        title: "接口文档",
        icon: "swagger",
      },
    },
    {
      path: "/security/roles",
      name: "roles ",
      component: () => import("@/views/security/Roles.vue"),
      meta: {
        title: "角色管理",
        icon: "role",
      },
    },
  ],
};
