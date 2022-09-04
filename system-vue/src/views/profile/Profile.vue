<template>
  <div class="profile-container">
    <el-row>
      <el-col :span="7">
        <!-- 个人信息界面 -->
        <user-info class="user-info"></user-info>
      </el-col>
      <el-col :span="17">
        <el-card>
          <el-tabs v-model="activeName">
            <!-- 基本资料 -->
            <el-tab-pane label="基本资料" name="baseInfo">
              <base-info></base-info>
            </el-tab-pane>
            <!-- 修改密码 -->
            <el-tab-pane label="修改密码" name="changePass">
              <change-pass></change-pass>
            </el-tab-pane>
            <!-- 上传头像 -->
            <el-tab-pane label="上传头像" name="avatarUpload">
              <avatar-up></avatar-up>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import UserInfo from "./components/UserInfo.vue";
import BaseInfo from "./components/BaseInfo.vue";
import ChangePass from "./components/ChangePass.vue";
import AvatarUp from "./components/AvatarUp.vue";

export default {
  name: "Profile",
  components: { UserInfo, BaseInfo, ChangePass, AvatarUp },
  data() {
    return {
      activeName: "baseInfo",
    };
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
.profile-container {
  width: 100%;

  .user-info {
    margin-right: 20px;
  }
}
</style>
