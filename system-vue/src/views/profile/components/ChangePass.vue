<template>
  <div>
    <el-form ref="passForm" :rules="passwordRules" :model="passwordForm">
      <el-form-item label="原密码" label-width="80px">
        <el-input v-model="passwordForm.oldPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码" label-width="80px" prop="newPassword">
        <el-input
          v-model="passwordForm.newPassword"
          type="password"
          maxlength="20"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <div class="button-container">
          <el-button type="primary" @click="changePassword('passForm')">
            修改
          </el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "ChangePass",

  data() {
    return {
      newPassword: "",
      passwordForm: {
        newPassword: "",
        oldPassword: this.$store.getters.userInfo.password,
      },
      passwordRules: {
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "长度在 6 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },

  mounted() {},

  methods: {
    // 提交更新密码请求到后端
    changePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 调用后端接口
          this.$store
            .dispatch("user/updatePassword", this.passwordForm)
            .then(() => {
              // 3 秒后退出登录
              let _this = this;
              setTimeout(function () {
                _this.$store.dispatch("user/logout");
              }, 3000);
            });
        } else {
          this.$message.error("请输入新密码");
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.button-container {
  margin-top: 10px;
  text-align: center;
}
</style>
