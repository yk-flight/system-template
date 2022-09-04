/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : system_template

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 04/09/2022 17:38:23
*/
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE system_template;
USE system_template;
-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后端访问路径',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前端访问路径',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件',
  `permission` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `require_auth` tinyint(4) NULL DEFAULT NULL COMMENT '是否要求权限',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父组件Id',
  `enabled` tinyint(4) NULL DEFAULT NULL COMMENT '是否启用',
  `create_Time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/', '/', '/', NULL, '所有', NULL, NULL, NULL, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (2, '/', '/profile', 'Profile', NULL, '个人中心', 'user', 1, 1, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (3, '/', '/dashboard', 'Dashboard', NULL, '仪表盘', 'dashboard', 1, 1, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (4, '/', '/security', 'Main', 'security', '权限管理', 'security', 1, 1, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (5, '/', '/user', 'Main', 'userManage', '用户管理', 'personnel-manage', 1, 1, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (6, '/', '/log', 'Main', 'logManage', '日志管理', 'server', 1, 1, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (7, '/security/menus/**', '/security/menus', 'Menus', 'menus', '菜单列表', 'menu', 1, 4, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (8, '/security/interface/**', '/security/interface', 'Interface', 'swagger', '接口文档', 'swagger', 1, 4, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (9, '/security/roles/**', '/security/roles', 'Roles', 'roles', '角色管理', 'role', 1, 4, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (10, '/user/userList/**', '/user/userList', 'UserList', 'userList', '用户列表', 'peoples', 1, 5, 1, '2022-08-06 23:40:44');
INSERT INTO `menu` VALUES (11, '/log/operation/**', '/log/operation', 'Operation', 'operation', '操作日志', 'access', 1, 6, 1, '2022-08-06 23:40:44');

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统模块',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作描述',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `operate_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作方法',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人员',
  `operate_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地址',
  `operate_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '操作状态',
  `status_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态描述',
  `request_param` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `duration` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '执行时长',
  `operate_time` datetime NULL DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operate_log
-- ----------------------------
INSERT INTO `operate_log` VALUES (1, '角色模块', '更新角色', 'PUT', 'com.zrkizzy.blog.service.impl.RoleServiceImpl.updateRole', '世纪末的架构师', '127.0.0.1', '本地登录', 1, '响应成功', '[{\"id\":2,\"permission\":[4,11,12,13,14,5,15,16,6,17,18,19,7,20,8,21,9,22,23,24,25,10,26],\"roleName\":\"ROLE_test\",\"roleNameZh\":\"测试账号\"}]', 13, '2022-09-03 19:52:30');
INSERT INTO `operate_log` VALUES (2, '用户模块', '删除用户', 'DELETE', 'com.zrkizzy.blog.service.impl.UserServiceImpl.deleteUserById', '世纪末的架构师', '127.0.0.1', '本地登录', 1, '响应成功', '[4]', 23, '2022-09-03 22:46:14');
INSERT INTO `operate_log` VALUES (5, '用户信息模块', '更新用户个人信息', 'POST', 'com.zrkizzy.template.service.impl.UserInfoServiceImpl.updateUserInfo', '世纪末的架构师', '127.0.0.1', '本地登录', 1, '响应成功', '[{\"csdn\":\"https://blog.csdn.net/qq_48455576?spm=1011.2124.3001.5343\",\"description\":\"一个想进大厂的大三学生狗\",\"email\":\"1072876976@qq.com\",\"gitee\":\"https://gitee.com/dream-flight\",\"github\":\"https://github.com/Architect-Java\",\"id\":1,\"leetcode\":\"https://leetcode.cn/u/dream-flight/\",\"nickName\":\"世纪末的架构师111\",\"phone\":\"15234417033\",\"qq\":\"1072876976\",\"username\":\"admin\"}]', 44, '2022-09-04 14:18:58');
INSERT INTO `operate_log` VALUES (6, '用户信息模块', '更新用户个人信息', 'POST', 'com.zrkizzy.template.service.impl.UserInfoServiceImpl.updateUserInfo', '世纪末的架构师111', '127.0.0.1', '本地登录', 1, '响应成功', '[{\"csdn\":\"https://blog.csdn.net/qq_48455576?spm=1011.2124.3001.5343\",\"description\":\"一个想进大厂的大三学生狗\",\"email\":\"1072876976@qq.com\",\"gitee\":\"https://gitee.com/dream-flight\",\"github\":\"https://github.com/Architect-Java\",\"id\":1,\"leetcode\":\"https://leetcode.cn/u/dream-flight/\",\"nickName\":\"世纪末的架构师\",\"phone\":\"15234417033\",\"qq\":\"1072876976\",\"username\":\"admin\"}]', 29, '2022-09-04 14:42:39');
INSERT INTO `operate_log` VALUES (7, '角色模块', '更新角色', 'PUT', 'com.zrkizzy.template.service.impl.RoleServiceImpl.updateRole', '世纪末的架构师', '127.0.0.1', '本地登录', 1, '响应成功', '[{\"id\":2,\"permission\":[4,7,6,11],\"roleName\":\"ROLE_test\",\"roleNameZh\":\"测试账号\"}]', 9, '2022-09-04 15:03:08');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色',
  `role_name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色权限',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_admin', '系统管理员', '[4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26]', '2022-08-07 01:06:37', '2022-08-29 13:53:31');
INSERT INTO `role` VALUES (2, 'ROLE_test', '测试账号', '[4,7,6,11]', '2022-08-07 12:39:32', '2022-09-04 15:03:08');
INSERT INTO `role` VALUES (4, 'ROLE_user', '普通用户', '[2,3]', '2022-08-18 08:00:00', '2022-08-29 13:55:11');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
  `ip_source` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP属地',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '用户上次登录时间',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '当前用户是否启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '世纪末的架构师', 'admin', '$2a$10$Tt3NW32EBpMk9ClWUCAkOu6FIRXyhfze7xJ0bQGx8sdVgV4IqYzqO', '/images/h9BVg9XX.jpeg', '127.0.0.1', '本地登录', '2022-09-04 17:35:40', 1, '2022-08-06 23:40:44', '2022-09-04 14:42:39');
INSERT INTO `user` VALUES (2, '测试账号', 'test', '$2a$10$g5pQvid1RhUCitEfLktIGO2gVRLWlvVQgfC3t/1LhX81RghnIuEaC', '/images/5eoV1YxS.jpg', '127.0.0.1', '本地登录', '2022-09-04 17:35:21', 1, '2022-08-13 18:23:02', '2022-09-03 11:13:17');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `qq` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'QQ号码',
  `github` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'git hub地址',
  `gitee` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'git ee地址',
  `csdn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'csdn账号',
  `leetcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'leetcode主页',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '1072876976@qq.com', '15234417033', '1072876976', 'https://github.com/Architect-Java', 'https://gitee.com/dream-flight', 'https://blog.csdn.net/qq_48455576?spm=1011.2124.3001.5343', 'https://leetcode.cn/u/dream-flight/', '一个想进大厂的大三学生狗');
INSERT INTO `user_info` VALUES (2, 'test@qq.com', '15234410000', '2675525537', 'github.com', 'github.com', 'csdn111', 'leetcode', '这里是测试账号的自我描述');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
