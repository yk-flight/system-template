import axios from "axios";
import store from "@/store";
import { Message } from "element-ui";
import { isCheckTimeout } from "@/utils/auth";

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时时间 => 5s
  timeout: 5000,
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 统一注入 token
    if (store.getters.token) {
      // 当 token 存在时判断是否已经过期
      // if (isCheckTimeout()) {
      //   // 超时执行退出操作
      //   store.dispatch("user/logout");
      //   return Promise.reject(new Error("当前 token 已失效"));
      // }
      config.headers.Authorization = `Bearer ${store.getters.token}`;
    }
    return config;
  },
  (error) => {
    return Promise(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  // 请求成功的处理
  (response) => {
    // 从服务端返回的数据中提取到需要的数据
    const { message, data, code } = response.data;
    if (response.status && response.status === 200) {
      // 判断是否为业务逻辑错误的情况
      if (code == 500 || code == 401 || code == 403) {
        Message.error({ message: message });
        // 业务逻辑错误，直接返回空
        return;
      }
      // 设置提示信息
      if (message) {
        Message.success({ message: message });
      }
      // 将后端传输的数据进行返回
      if (data) {
        return data;
      }
      return response.data;
    } else {
      // 失败（请求成功，业务失败），消息提示
      Message.error(message);
      return Promise.reject(new Error(message));
    }
  },
  // 请求应失败的处理
  (error) => {
    // 如果服务端返回的数据中包含内容、并且响应中有数据、数据的状态码为401，说明 token 过期
    if (
      error.response &&
      error.response.data &&
      error.response.data.code === 401
    ) {
      store.dispatch("user/logout");
    }
    Message.error(error.message);
    return Promise.reject(new Error(error));
  }
);

export default service;
