import request from "@/utils/request";

export const getAllMenus = (data) => {
  return request({
    url: "/admin/getAllMenus",
    method: "POST",
    data,
  });
};

export const getAllRoles = () => {
  return request({
    url: "/role/admin/getAllRoles",
    method: "GET",
  });
};

export const insertRole = (data) => {
  return request({
    url: "/role/admin/insertRole",
    method: "POST",
    data,
  });
};

export const updateRole = (data) => {
  return request({
    url: "/role/admin/updateRole",
    method: "PUT",
    data,
  });
};

export const deleteRole = (id) => {
  return request({
    url: `/role/admin/deleteRole/${id}`,
    method: "DELETE",
  });
};

export const getAllPermission = () => {
  return request({
    url: "/admin/getAllPermission",
    method: "GET",
  });
};

/**
 * 根据用户ID获取用户对应的角色ID
 *
 * @param {*} id
 * @returns
 */
export const getRoleByUserId = (id) => {
  return request({
    url: `/user-role/getRoleByUserId/${id}`,
    method: "GET",
  });
};

/**
 * 更新用户角色
 *
 * @param {*} params
 * @returns
 */
export const updateUserRole = (params) => {
  return request({
    url: "/user-role/updateUserRole",
    method: "PUT",
    params: params,
  });
};
