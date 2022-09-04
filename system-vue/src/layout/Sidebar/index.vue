<template>
  <div style="background-color: rgb(48, 65, 86)">
    <div class="logo-container">
      <el-avatar
        :size="44"
        shape="circle"
        :src="this.$store.getters.userInfo.avatar"
      ></el-avatar>
      <h1 class="logo-title" v-if="!this.$store.getters.isCollapse">
        后台管理系统
      </h1>
    </div>
    <sidebar-menu></sidebar-menu>
  </div>
</template>

<script>
import SidebarMenu from "./components/SidebarMenu";

export default {
  name: "Sidebar",
  components: { SidebarMenu },
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.logo-container {
  height: "76px";
  padding: 10px 0 22px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgb(48, 65, 86);

  .logo-title {
    margin-left: 10px;
    color: #fff;
    font-weight: 600;
    line-height: 50px;
    font-size: 16px;
    white-space: nowrap;
  }
  .el-avatar {
    --el-avatar-background-color: none;
  }
}
</style>
