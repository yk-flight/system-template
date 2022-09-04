<template>
  <div class="navbar">
    <!-- 汉堡包图标 -->
    <hamburger class="hamburger-container"></hamburger>
    <!-- 面包屑导航 -->
    <breadcrumb class="breadcrumb-container"></breadcrumb>

    <div class="right-menu">
      <screenfull class="right-menu-item hover-effect"></screenfull>

      <!-- 头像 -->
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-avatar
            shape="square"
            :size="40"
            :src="this.$store.getters.userInfo.avatar"
          ></el-avatar>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <template #dropdown>
          <el-dropdown-menu class="user-dropdown">
            <!-- git hub -->
            <a
              target="__blank"
              href="https://github.com/Architect-Java"
              style="text-decoration: none"
            >
              <el-dropdown-item>
                <svg-icon icon="github"></svg-icon>
                &nbsp;Git hub
              </el-dropdown-item>
            </a>
            <!-- 退出登录 -->
            <el-dropdown-item icon="el-icon-user-solid">
              <router-link
                to="/profile"
                style="text-decoration: none; color: #606266; font-size: 14px"
              >
                <span>个人中心</span>
              </router-link>
            </el-dropdown-item>
            <!-- 退出登录 -->
            <el-dropdown-item
              divided
              @click.native="logout"
              icon="el-icon-switch-button"
            >
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Hamburger from "./components/Hamburger";
import Breadcrumb from "./components/Breadcrumb.vue";
import Screenfull from "@/components/Screenfull/index.vue";

export default {
  name: "Navbar",
  components: { Hamburger, Breadcrumb, Screenfull },
  data() {
    return {};
  },
  mounted() {},

  methods: {
    // 用户退出方法
    logout() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$store.dispatch("user/logout");
          // 输出消息提示
          this.$message.success("退出登录");
        })
        .catch(() => {});
    },
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    // hover 动画
    transition: background 0.5s;

    &:hover {
      background-color: rgba(0, 0, 0, 0.1);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    display: flex;
    align-items: center;
    float: right;
    padding-right: 16px;

    .right-menu-item {
      display: inline-block;
      padding: 10px 18px 0 10px;
      font-size: 24px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
      }
    }

    .avatar-container {
      cursor: pointer;
      .avatar-wrapper {
        margin-top: 5px;
        position: relative;
        .el-avatar {
          --el-avatar-background-color: none;
          margin-right: 5px;
        }
      }
    }
  }
}
</style>
