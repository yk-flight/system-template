<template>
  <div class="sidebar-container">
    <perfect-scrollbar ref="scrollbar">
      <el-menu
        :default-active="activeMenu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="rgb(64, 158, 255)"
        :collapse="this.$store.getters.isCollapse"
        router
      >
        <sidebar-item
          v-for="item in routes"
          :key="item.path"
          :route="item"
        ></sidebar-item>
      </el-menu>
    </perfect-scrollbar>
  </div>
</template>

<script>
import SidebarItem from "./SidebarItem.vue";
import { filterRoutes, generateMenus } from "@/utils/route";

export default {
  name: "SidebarMenu",
  components: { SidebarItem },
  data() {
    return {};
  },

  mounted() {},
  computed: {
    activeMenu() {
      const { path } = this.$route;
      return path;
    },
    // 监视当路由是否发生变化
    routes() {
      const filteRoutes = filterRoutes(this.$router.options.routes);
      return generateMenus(filteRoutes);
    },
  },
};
</script>

<style lang="scss" scoped>
.sidebar-container {
  margin-top: 76px;
  width: 100%;

  //perfect-scrollbar默认的类名。自定义滚动条内容区域高度
    .ps {
      // margin-top: 5%;
      height: 90%;
    }
}
</style>
