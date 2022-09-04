<template>
  <el-card class="user-container">
    <template #header>
      <div class="header">
        <span>个人信息</span>
      </div>
    </template>

    <div class="user-profile">
      <!-- 头像 -->
      <div class="box-center">
        <pan-thumb
          :image="this.$store.getters.userInfo.avatar"
          :height="'100px'"
          :width="'100px'"
          :hoverable="false"
        >
          <div>Hello,</div>
          祝福送给你
        </pan-thumb>
      </div>
      <!-- 姓名 和 角色 -->
      <div class="box-center">
        <div class="user-name text-center">
          {{ this.$store.getters.userInfo.nickName }}
        </div>
        <div class="last-login text-center text-muted">
          上次登录时间：
          {{ this.$store.getters.userInfo.lastLoginTime | dateFilter }}
        </div>
      </div>
    </div>
    <!-- 其他信息 -->
    <div class="person-bio">
      <div class="person-bio-section">
        <div class="person-bio-section-header">
          <svg-icon icon="introduce"></svg-icon>
          <span>其他信息</span>
        </div>
        <div class="person-bio-section-body">
          <ul class="ul-item">
            <li>
              <svg-icon icon="user"></svg-icon>
              <span>用户账号</span>
              <span class="li-item">
                {{ this.$store.getters.userInfo.username }}
              </span>
            </li>
            <li>
              <svg-icon icon="IP"></svg-icon>
              <span>登录IP地址</span>
              <span class="li-item">
                {{ this.$store.getters.userInfo.ipAddress }}
              </span>
            </li>
            <li>
              <svg-icon icon="computer"></svg-icon>
              <span>登录设备</span>
              <span class="li-item">
                {{ this.$store.getters.userAgent }}
              </span>
            </li>
            <li>
              <svg-icon icon="date"></svg-icon>
              <span>创建时间</span>
              <span class="li-item">
                {{ this.$store.getters.userInfo.createTime | dateFilter }}
              </span>
            </li>
            <li>
              <svg-icon icon="date-range"></svg-icon>
              <span>上次更新时间</span>
              <span class="li-item">
                {{ this.$store.getters.userInfo.updateTime | dateFilter }}
              </span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import PanThumb from "@/components/PanThumb/index";

export default {
  name: "UserInfo",
  components: { PanThumb },
  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
.user-container {
  .text-muted {
    font-size: 14px;
    color: #777;
  }

  .user-profile {
    text-align: center;
    .user-name {
      font-weight: bold;
    }

    .box-center {
      padding-top: 10px;
    }

    .last-login {
      padding-top: 10px;
      font-weight: 400;
    }
  }

  .person-bio {
    margin-top: 30px;
    color: #606266;
    span {
      padding-left: 10px;
    }

    .person-bio-section {
      margin-bottom: 36px;

      .ul-item {
        margin-left: 0;
        padding-left: 0;
      }

      .person-bio-section-header {
        border-bottom: 1px solid #dfe6ec;
        padding-bottom: 10px;
        margin-bottom: 10px;
      }
      .person-bio-section-body {
        li {
          list-style: none;
          border-bottom: 1px solid #dfe6ec;
          line-height: 35px;
          font-size: 14px;
        }

        .li-item {
          float: right;
        }
      }
    }
  }
}
</style>
