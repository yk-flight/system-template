<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :rules="loginRules"
      :model="loginForm"
      class="login-form-container"
    >
      <h2 class="login-title">系统登录</h2>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="loginForm.username"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          auto-complete="false"
          v-model="loginForm.password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          type="text"
          auto-complete="false"
          v-model="loginForm.code"
          placeholder="点击图片更换验证码"
          style="width: 210px; margin-right: 10px"
        ></el-input>
        <img :src="kaptchaUrl" @click="updateKaptcha" style="cursor: pointer" />
      </el-form-item>
      <el-button
        type="primary"
        style="width: 100%; margin-bottom: 20px"
        @click="submitLogin"
        >登录
      </el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",

  data() {
    return {
      kaptchaUrl: "/kaptcha?time=" + new Date(),
      // 用户登录表单
      loginForm: {
        username: "",
        password: "",
        code: "",
      },
      loginRules: {
        username: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur",
          },
        ],
        code: [
          {
            required: true,
            message: "请输入验证码",
            trigger: "blur",
          },
        ],
      },
    };
  },

  mounted() {},

  methods: {
    // 表单提交事件
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$store.dispatch("user/login", this.loginForm).then(() => {
            // 跳转到后台首页
            this.$router.push("/dashboard");
          });
        } else {
          this.$message.error("请输入所有内容");
          return false;
        }
      });
    },
    // 点击图片更换验证码
    updateKaptcha() {
      this.kaptchaUrl = "/kaptcha?time=" + new Date();
    },
  },
};
</script>

<style>
.login-container {
  overflow: hidden;
  background-size: 100% 100%;
  height: 100%;
  background-image: url("../../assets/image/background.jpg");
}

.login-form-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 320px;
  padding: 15px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  /* box-shadow: 0 0 25px #cac6c6; */
}

.login-title {
  margin: 20px auto 20px auto;
  text-align: center;
}

.el-form-item__content {
  display: flex;
  align-items: center;
}
</style>
