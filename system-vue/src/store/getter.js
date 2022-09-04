import { getItem } from "@/utils/storage";

// 快捷访问
const getters = {
  token: (state) => state.user.token,
  isCollapse: (state) => state.app.isCollapse,
  tagsViewList: (state) => state.app.tagsViewList,
  // true：表示用户信息已存在
  hasUserInfo: (state) => {
    // 如果 user 模块中的 userInfo 不是空对象
    return JSON.stringify(state.user.userInfo) !== "{}";
  },
  userInfo: (state) => state.user.userInfo,
  // 用户登录设备
  userAgent: (state) => state.user.userAgent,
  // 用户个人信息
  userOtherInfo: (state) => state.user.userOtherInfo,
  // 用户可以访问的页面
  accessPath: (state) => state.user.accessPath,
};

export default getters;
