<template>
  <div class="baseInfo-container">
    <el-form :model="userInfo" :rules="userInfoRules" ref="userInfoForm">
      <el-form-item prop="nickName" label="昵称" label-width="60px">
        <el-input
          placeholder="用户昵称"
          v-model="userInfo.nickName"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="email" label="邮箱" label-width="60px">
        <el-input
          placeholder="用户邮箱"
          v-model="userInfo.email"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="电话" label-width="60px">
        <el-input
          placeholder="联系电话"
          v-model="userInfo.phone"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="" label="QQ" label-width="60px">
        <el-input
          placeholder="QQ账号"
          v-model="userInfo.qq"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="" label="git hub" label-width="60px">
        <el-input
          placeholder="Github主页"
          v-model="userInfo.github"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="" label="gitee" label-width="60px">
        <el-input
          placeholder="Gitee主页"
          v-model="userInfo.gitee"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="" label="csdn" label-width="60px">
        <el-input
          placeholder="CSDN主页"
          v-model="userInfo.csdn"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item prop="" label="力扣" label-width="60px">
        <el-input
          placeholder="LeetCode主页"
          v-model="userInfo.leetcode"
          type="text"
        ></el-input>
      </el-form-item>
      <el-form-item label="简介" label-width="60px">
        <el-input
          type="textarea"
          placeholder="这里输入自我介绍"
          v-model="userInfo.description"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <div class="button-container">
          <el-button type="primary" @click="submitUserInfoForm('userInfoForm')">
            保存
          </el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "BaseInfo",
  data() {
    // =================== 修改手机号校验规则 ===================
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
      setTimeout(() => {
        // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
        if (!Number.isInteger(+value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (phoneReg.test(value)) {
            callback();
          } else {
            callback(new Error("电话号码格式不正确"));
          }
        }
      }, 100);
    };
    // =================== 修改邮箱校验规则 ===================
    var checkEmail = (rule, value, callback) => {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
      if (!value) {
        return callback(new Error("邮箱不能为空"));
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback();
        } else {
          callback(new Error("请输入正确的邮箱格式"));
        }
      }, 100);
    };
    return {
      // 用户其他信息
      // userInfo: this.$store.getters.userOtherInfo,
      userInfo: {
        // 当前登录用户的ID
        id: this.$store.getters.userInfo.id,
        // 角色ID
        roleId: undefined,
        // 用户名
        username: this.$store.getters.userInfo.username,
        // 用户昵称
        nickName: this.$store.getters.userInfo.nickName,
        email: this.$store.getters.userOtherInfo.email,
        phone: this.$store.getters.userOtherInfo.phone,
        qq: this.$store.getters.userOtherInfo.qq,
        github: this.$store.getters.userOtherInfo.github,
        gitee: this.$store.getters.userOtherInfo.gitee,
        csdn: this.$store.getters.userOtherInfo.csdn,
        leetcode: this.$store.getters.userOtherInfo.leetcode,
        description: this.$store.getters.userOtherInfo.description,
      },
      // 用户信息修改校验规则
      userInfoRules: {
        email: [{ validator: checkEmail, trigger: "blur" }],
        phone: [{ validator: checkPhone, trigger: "blur" }],
      },
    };
  },

  mounted() {},

  methods: {
    // 更新用户个人信息
    submitUserInfoForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 调用后端接口
          this.$store
            .dispatch("user/updateUserInfo", { userInfo: this.userInfo })
            .then(() => {
              // 重新设置用户登录信息
              this.$store.dispatch("user/getUserInfo");
              // 重新设置用户个人信息
              this.$store.dispatch("user/getUserInfoById");
            });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.baseInfo-container {
  margin-top: 10px;

  .button-container {
    margin-top: 10px;
    text-align: center;
  }
}
</style>
