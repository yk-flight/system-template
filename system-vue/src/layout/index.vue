<template>
  <div
    class="app-wrapper"
    :class="this.$store.getters.isCollapse ? 'hideSidebar' : 'openSidebar'"
  >
    <!-- 侧边菜单栏 -->
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <div class="fixed-header">
        <!-- 顶部导航栏 -->
        <navbar />
        <!-- 标签页 -->
        <tags-view></tags-view>
      </div>
      <!-- 主体内容区 -->
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import AppMain from "./AppMain.vue";
import Sidebar from "./Sidebar/index";
import Navbar from "./Navbar/index";
import TagsView from "@/components/TagsView/index.vue";

export default {
  name: "Layout",
  components: { Sidebar, Navbar, AppMain, TagsView },
  computed: {},
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}

// 吸顶导航栏
.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - 210px);
  transition: width 0.28s;
}

.main-container {
  background-color: #f0f2f5;
}

.hideSidebar .fixed-header {
  width: calc(100% - 50px);
}
</style>
