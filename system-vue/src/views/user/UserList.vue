<template>
  <div class="user-container">
    <el-card shadow="never">
      <div class="user-header">
        <el-form :inline="true">
          <el-form-item>
            <span class="title-label">用户名</span>
            <el-input
              v-model="username"
              placeholder="请输入用户名"
              style="width: 300px"
            ></el-input>
          </el-form-item>

          <el-form-item style="margin-left: 25px">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索
            </el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">
              重置
            </el-button>
          </el-form-item>
        </el-form>

        <div class="button-container">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >
            新增
          </el-button>
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            disabled
          >
            导入
          </el-button>
          <el-button type="warning" plain icon="el-icon-download" size="mini">
            导出
          </el-button>
        </div>
      </div>

      <div class="user-body">
        <el-table
          :header-cell-style="{ background: '#f8f8f9', color: '#606266' }"
          ref="table"
          row-key="id"
          :data="userList"
          border
          style="width: 100%"
          size="medium"
        >
          <el-table-column label="序号" width="50" align="center">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            prop="username"
            label="用户名"
            align="center"
            width="90"
          >
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" align="center">
          </el-table-column>
          <el-table-column label="用户头像" width="90" align="center">
            <template slot-scope="scope">
              <el-image
                :src="scope.row.avatar"
                @click="preViewImage(scope.row.avatar)"
                :preview-src-list="srcList"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="ipAddress" label="登录IP" align="center">
          </el-table-column>
          <el-table-column prop="ipSource" label="登录地址" align="center">
          </el-table-column>
          <el-table-column label="状态" align="center" key="enabled">
            <template slot-scope="scope">
              <el-switch
                v-if="scope.row.username !== 'admin'"
                v-model="scope.row.enabled"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
              <el-switch v-else v-model="scope.row.enabled" disabled>
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="上次登录时间" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.lastLoginTime | dateFilter }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime | dateFilter }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="190">
            <!--  -->
            <template slot-scope="scope" v-if="scope.row.username != 'admin'">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >
                修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >
                删除
              </el-button>
              &nbsp;
              <el-dropdown
                szie="mini"
                @command="(command) => handleCommand(command, scope.row)"
              >
                <el-button type="text" size="mini" icon="el-icon-d-arrow-right">
                  更多
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key">
                    重置密码
                  </el-dropdown-item>
                  <el-dropdown-item
                    command="handleAuthRole"
                    icon="el-icon-circle-check"
                  >
                    分配角色
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="user-footer">
        <el-pagination
          :page-sizes="[5, 10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>
    </el-card>

    <!--  用户添加 / 更新对话框  -->
    <el-dialog
      :title="dialogTitle"
      width="700px"
      :visible="dialogShow"
      :before-close="handleClose"
    >
      <el-form :model="userInfo" ref="userInfoForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" label-width="80px">
              <el-input
                placeholder="请输入用户名"
                v-model="user.username"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" label-width="80px">
              <el-input
                placeholder="请输入用户昵称"
                v-model="user.nickName"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="user.id !== undefined">
          <el-col :span="12">
            <el-form-item label="创建时间" label-width="80px">
              <el-input
                disabled
                :value="dateFormat(user.createTime)"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间" label-width="80px">
              <el-input
                disabled
                :value="dateFormat(user.updateTime)"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户邮箱" label-width="80px" prop="email">
              <el-input
                v-model="userInfo.email"
                placeholder="请输入用户邮箱"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ号码" label-width="80px">
              <el-input
                v-model="userInfo.qq"
                placeholder="请输入QQ号码"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Git hub" label-width="80px">
              <el-input
                v-model="userInfo.github"
                placeholder="请输入Git hub主页"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Gitee" label-width="80px">
              <el-input
                v-model="userInfo.gitee"
                placeholder="请输入Gitee主页"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="CSDN" label-width="80px">
              <el-input
                v-model="userInfo.csdn"
                placeholder="请输入CSDN主页"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="力扣主页" label-width="80px">
              <el-input
                v-model="userInfo.leetcode"
                placeholder="请输入力扣主页"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电话号码" label-width="80px">
              <el-input
                placeholder="请输入电话号码"
                v-model="userInfo.phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="user.id === undefined">
            <el-form-item label="角色" label-width="80px">
              <el-select v-model="roleValue" placeholder="选择角色">
                <el-option
                  v-for="item in roles"
                  :key="item.id"
                  :value="item.id"
                  :label="item.roleNameZh"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="自我描述" label-width="80px">
          <el-input
            type="textarea"
            placeholder="请输入自我描述"
            v-model="userInfo.description"
          ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="handleClose">取消</el-button>
      </span>
    </el-dialog>

    <!--  分配权限对话框  -->
    <el-dialog
      title="分配角色"
      :visible="dialogRoleShow"
      :before-close="handleRoleClose"
    >
      <!-- 内部 -->
      <el-checkbox-group v-model="roles">
        <el-radio
          v-for="item in roles"
          :key="item.id"
          v-model="roleId"
          :label="item.id"
          >{{ item.roleNameZh }}</el-radio
        >
      </el-checkbox-group>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="sbumitRole">确定</el-button>
        <el-button @click="handleRoleClose">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getUserList,
  getUserInfoById,
  changeUserEnabled,
  addUser,
  updatePasswordById,
  deleteUserById,
} from "@/api/user.js";
import { getAllRoles, getRoleByUserId, updateUserRole } from "@/api/security";
import dayjs from "dayjs";

export default {
  name: "UserList",

  data() {
    return {
      // 用户名
      username: "",
      // 当前页数
      curPage: 1,
      // 页面大小
      size: 10,
      // 用户数据
      userList: [],
      // 用户角色
      roles: [],
      // 当前选中的角色
      roleValue: undefined,
      // 数据条数
      total: 0,
      // 对话框是否显示
      dialogShow: false,
      // 对话框标题
      dialogTitle: "",
      // 用户对象
      user: {},
      // 用户更新对象
      userInfo: {
        id: undefined,
        username: "",
        nickName: "",
        email: "",
        csdn: "",
        leetcode: "",
        github: "",
        gitee: "",
        qq: "",
        phone: "",
        description: "",
      },
      // 图片预览
      srcList: [],
      // 分配角色
      dialogRoleShow: false,
      // 用户的角色ID
      roleId: undefined,
      // 当前的用户ID
      userId: undefined,
    };
  },

  mounted() {
    this.getTableData();
    this.getUserRoles();
  },

  methods: {
    // 获取表格数据
    getTableData() {
      getUserList({
        curPage: this.curPage,
        size: this.size,
        name: this.username,
      }).then((result) => {
        this.userList = result.list;
        this.total = result.total;
      });
    },
    // 获取所有角色数据
    getUserRoles() {
      getAllRoles().then((result) => {
        this.roles = result;
      });
    },
    // 点击了查询按钮
    handleQuery() {
      this.getTableData();
    },
    // 点击了重置按钮
    resetQuery() {
      this.username = "";
    },
    // 关闭对话框
    handleClose() {
      this.dialogShow = false;
      this.reset();
    },
    // 刷新表单数据
    reset() {
      // 修改表单标题
      this.dialogTitle = "";
      // 重置表单对象
      this.user = {};
      // 重置用户信息对象
      this.userInfo = {
        id: undefined,
        username: "",
        nickName: "",
        email: "",
        csdn: "",
        leetcode: "",
        github: "",
        gitee: "",
        qq: "",
        phone: "",
        description: "",
      };
    },
    // 当页面大小发生变化时
    handleSizeChange(val) {
      this.size = val;
      // 重新获取数据
      this.getTableData();
    },

    // 当前页发生变化时
    handleCurrentChange(val) {
      this.curPage = val;
      // 重新获取数据
      this.getTableData();
    },
    // 点击修改事件
    handleUpdate(row) {
      this.dialogTitle = "修改用户";
      this.user = row;
      this.userInfo.id = row.id;
      // 获取当前用户信息
      getUserInfoById(row.id).then((result) => {
        // 将当前用户信息进行赋值
        this.userInfo.email = result.email;
        this.userInfo.csdn = result.csdn;
        this.userInfo.leetcode = result.leetcode;
        this.userInfo.github = result.github;
        this.userInfo.gitee = result.gitee;
        this.userInfo.phone = result.phone;
        this.userInfo.qq = result.qq;
        this.userInfo.description = result.description;
      });
      this.dialogShow = true;
    },
    // 点击新增事件
    handleAdd() {
      this.dialogTitle = "添加用户";
      this.dialogShow = true;
    },
    // 修改用户是否启用状态
    handleStatusChange(row) {
      changeUserEnabled(row.id).then(() => {
        // 刷新表格数据
        this.getTableData();
      });
    },
    // 点击确认修改/添加事件
    submitForm() {
      // 如果存在ID则说明为更新操作
      if (this.userInfo.id) {
        this.userInfo.username = this.user.username;
        this.userInfo.nickName = this.user.nickName;
        this.$store
          .dispatch("user/updateUserInfo", { userInfo: this.userInfo })
          .then(() => {
            // 重新获取数据
            this.getTableData();
          });
      } else {
        this.userInfo.username = this.user.username;
        this.userInfo.nickName = this.user.nickName;
        this.userInfo.roleId = this.roleValue;
        addUser(this.userInfo).then(() => {
          this.getTableData();
        });
      }
      this.handleClose();
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handlePasswordOpen(row);
          break;
        case "handleAuthRole":
          this.handleRoleOpen(row);
          break;
        default:
          break;
      }
    },
    // 打开分配角色对话框
    handleRoleOpen(row) {
      this.userId = row.id;
      // 获取当前用户对应的角色
      getRoleByUserId(row.id).then((result) => {
        this.roleId = result;
      });
      this.dialogRoleShow = true;
    },
    // 关闭分配角色对话框
    handleRoleClose() {
      this.roleId = undefined;
      this.dialogRoleShow = false;
    },
    // 开启修改密码对话框
    handlePasswordOpen(row) {
      this.$prompt('请输入"' + row.username + '"的新密码', "重置密码", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
      })
        .then(({ value }) => {
          updatePasswordById({ userId: row.id, password: value }).then(
            () => {}
          );
        })
        .catch(() => {});
    },
    // 更新用户角色
    sbumitRole() {
      updateUserRole({ userId: this.userId, roleId: this.roleId }).then(() => {
        // 关闭分配权限对话框
        this.handleRoleClose();
      });
    },
    // 点击删除按钮
    handleDelete(row) {
      this.$confirm("确定删除 " + row.username + " 吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 执行删除操作
          deleteUserById(row.id).then(() => {
            // 重新获取表格数据
            this.getTableData();
          });
        })
        .catch(() => {});
    },
    // 格式化时间
    dateFormat(date) {
      // 使用 dayjs 处理时间
      return dayjs(date).format("YYYY-MM-DD HH:mm:ss");
    },
    // 图片预览
    preViewImage(val) {
      this.srcList = [];
      this.srcList.push(val);
    },
  },
};
</script>

<style lang="scss" scoped>
.user-container {
  margin-top: 10px;

  .user-header {
    margin-top: 10px;
    padding-left: 15px;

    .title-label {
      font-size: 14px;
      font-weight: 700;
      color: #606266;
      margin-right: 12px;
    }
  }

  .user-body {
    margin: 20px 15px 30px 15px;
  }

  .user-footer {
    padding-left: 15px;
    margin-top: 40px;
  }
}
</style>
