import request from "@/utils/request";

/**
 * 获取用户信息
 *
 * return Promise
 */
export const getCurrentUserInfo = () => {
  return request({
    url: "/admin/getCurrentUserInfo",
    method: "GET",
  });
};

/**
 *  更新用户个人信息
 *
 * @param {*} data promise
 * @returns
 */
export const updateUserInfo = (data) => {
  return request({
    url: "/admin/updateUserInfo",
    method: "POST",
    data,
  });
};

/**
 * 获取用户可以访问的页面
 *
 * @returns 用户可以访问的页面集合
 */
export const getAccessPath = () => {
  return request({
    url: "/role/admin/getAccessPath",
    method: "GET",
  });
};

/**
 * 用户更新密码操作
 *
 * @param {*} data 密码表单
 * @returns
 */
export const updatePassword = (data) => {
  return request({
    url: "/user/admin/updatePassword",
    method: "PUT",
    data,
  });
};

/**
 * 获取用户列表
 *
 * @returns
 */
export const getUserList = (data) => {
  return request({
    url: "/user/admin/getUserList",
    method: "GET",
    params: data,
  });
};

/**
 * 通过ID获取指定的用户信息
 *
 * @param {*} id
 * @returns
 */
export const getUserInfoById = (id) => {
  return request({
    url: `/admin/getUserInfoById/${id}`,
    method: "GET",
  });
};

/**
 * 更新用户启用状态
 *
 * @param {*} id
 * @returns
 */
export const changeUserEnabled = (id) => {
  return request({
    url: `/admin/changeUserEnabled/${id}`,
    method: "PUT",
  });
};

/**
 * 新增用户
 *
 * @param {*} data
 */
export const addUser = (data) => {
  return request({
    url: "/user/admin/addUser",
    method: "POST",
    data: data,
  });
};

/**
 * 重置指定用户的密码88
 *
 * @param {*} params
 */
export const updatePasswordById = (params) => {
  return request({
    url: "/user/admin/updatePasswordById",
    method: "PUT",
    params: params,
  });
};

/**
 * 根据用户ID删除用户
 *
 * @param {*} id
 * @returns
 */
export const deleteUserById = (id) => {
  return request({
    url: `/user/admin/deleteUserById/${id}`,
    method: "DELETE",
  });
};

/**
 * 上传用户头像
 */
export const uploadAvatar = (data) => {
  return request({
    url: "/user/admin/uploadAvatar",
    method: "POST",
    data: data,
  });
};

/**
 * 更新用户头像路径
 *
 * @param {*} data
 * @returns
 */
export const updateAvatarById = (data) => {
  return request({
    url: "/user/admin/updateAvatarById",
    method: "POST",
    data: data,
  });
};
