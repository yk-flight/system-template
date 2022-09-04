import router from "@/router";
import { TOKEN } from "@/constant";
import { setTimeStamp } from "@/utils/auth";
import { setItem, getItem, removeAllItem } from "@/utils/storage";
import { login, getUserInfo, getUserAgent } from "../../api/sys";
import {
  getCurrentUserInfo,
  updateUserInfo,
  getAccessPath,
  updatePassword,
} from "../../api/user";

import { Notification } from "element-ui";

export default {
  // 表示模块为单独的模块
  namespaced: true,
  state: () => ({
    // token
    token: getItem(TOKEN) || "",
    // 用户登录信息
    userInfo: {},
    // 用户个人信息
    userOtherInfo: {},
    // 用户登录设备
    userAgent: "",
    // 用户可以访问的页面
    accessPath: [],
  }),
  mutations: {
    setToken(state, token) {
      state.token = token;
      setItem(TOKEN, token);
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    setUserAgent(state, userAgent) {
      state.userAgent = userAgent;
    },
    setUserOtherInfo(state, userOtherInfo) {
      state.userOtherInfo = userOtherInfo;
    },
    setAccessPath(state, accessPath) {
      state.accessPath = accessPath;
    },
  },
  actions: {
    // 登录请求动作
    login(context, userInfo) {
      // 从用户信息中得到用户名和密码
      const { username, password, code } = userInfo;
      return new Promise((resolve, reject) => {
        login({
          username,
          password: password,
          code: code,
        })
          .then((data) => {
            // 将 token 进行存储
            this.commit("user/setToken", data.token);
            // 保存登录时间
            // setTimeStamp();
            resolve();
          })
          // 如果出现错误
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 获取用户信息
    async getUserInfo(context) {
      // 获取到服务端的返回数据
      const res = await getUserInfo();
      this.commit("user/setUserInfo", res);
      return res;
    },

    // 获取用户登录设备
    async getUserAgent(context) {
      // 获取服务器端返回的数据
      const res = await getUserAgent();
      this.commit("user/setUserAgent", res);
      return res;
    },

    // 获取用户个人信息
    async getUserInfoById(context) {
      // 获取服务器端返回的数据
      const res = await getCurrentUserInfo();
      this.commit("user/setUserOtherInfo", res);
      return res;
    },

    // 更新用户个人信息
    updateUserInfo(context, data) {
      // 将需要返回到后端的数据进行提取
      const {
        id,
        roleId,
        username,
        nickName,
        email,
        csdn,
        description,
        gitee,
        github,
        leetcode,
        phone,
        qq,
      } = data.userInfo;
      return new Promise((resolve, reject) => {
        updateUserInfo({
          id: id,
          roleId: roleId,
          username: username,
          nickName: nickName,
          email: email,
          csdn: csdn,
          description: description,
          gitee: gitee,
          github: github,
          leetcode: leetcode,
          phone: phone,
          qq: qq,
        })
          .then(() => {
            resolve();
          })
          // 如果出现错误
          .catch((error) => {
            reject(error);
          });
      });
    },
    // 获取用户可访问的页面路径
    async getAccessPath() {
      const res = await getAccessPath();
      this.commit("user/setAccessPath", res);
    },

    // 用户更新密码
    updatePassword(context, data) {
      // 将需要返回到后端的数据进行提取
      const { oldPassword, newPassword } = data;
      return new Promise((resolve, reject) => {
        updatePassword({
          oldPassword: oldPassword,
          newPassword: newPassword,
        })
          .then((data) => {
            if (data.code === 200) {
              // 输出消息提示
              Notification.success({
                title: "密码更新成功",
                message: "3 秒后系统将自动退出，请重新登录",
              });
            }
            resolve();
          })
          // 如果出现错误
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 退出登录
    logout() {
      // 1. 清除当前用户的令牌
      this.commit("user/setToken", "");
      // 2. 清除当前用户的信息
      this.commit("user/setUserInfo", {});
      this.commit("user/setUserAgent", "");
      this.commit("user/setAccessPath", []);
      // 3. 清除本地缓存
      removeAllItem();
      // 4. 删除缓存信息
      window.sessionStorage.removeItem("vuex");
      // 5. 跳转到登录页
      router.push("/login");
      // 刷新当前页面
      router.go(0);
    },
  },
};
