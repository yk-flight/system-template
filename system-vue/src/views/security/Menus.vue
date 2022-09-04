<template>
  <div class="menu-container">
    <el-card shadow="never">
      <div class="menu-header">
        <el-form :inline="true" :model="queryParams">
          <el-form-item>
            <span class="title-label">菜单名称</span>
            <el-input
              v-model="queryParams.name"
              placeholder="请输入菜单名称"
              style="width: 220px"
              clearable
            ></el-input>
          </el-form-item>

          <el-form-item>
            <span class="title-label">状态</span>
            <el-select
              v-model="queryParams.enabled"
              placeholder="菜单状态"
              style="width: 220px"
            >
              <el-option label="正常" :value="true"></el-option>
              <el-option label="停用" :value="false"></el-option>
            </el-select>
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
            icon="el-icon-sort"
            size="mini"
            @click="toggleExpandAll"
          >
            展开/折叠
          </el-button>
        </div>
      </div>

      <div class="menu-body">
        <el-table
          v-if="refreshTable"
          :data="menuList"
          :header-cell-style="{ background: '#f8f8f9', color: '#606266' }"
          ref="table"
          row-key="id"
          style="width: 100%"
          size="medium"
          :default-expand-all="isExpandAll"
        >
          <el-table-column prop="name" label="菜单名称" align="center">
          </el-table-column>
          <el-table-column label="图标" align="center">
            <template slot-scope="scope">
              <svg-icon :icon="scope.row.icon"></svg-icon>
            </template>
          </el-table-column>
          <el-table-column prop="permission" label="权限标识" align="center">
          </el-table-column>
          <el-table-column prop="component" label="组件" align="center">
          </el-table-column>
          <el-table-column prop="path" label="组件路径" align="center">
          </el-table-column>
          <el-table-column prop="enabled" label="状态" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.enabled" type="success">正常</el-tag>
              <el-tag v-else type="danger">停用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime | dateFilter }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getAllMenus } from "@/api/security";

export default {
  name: "Menus",

  data() {
    return {
      // 查询参数
      queryParams: {
        // 菜单名称
        name: "",
        // 菜单状态
        enabled: true,
      },
      // 菜单表格数据
      menuList: [],
      // 是否展开全部菜单
      isExpandAll: false,
      // 是否重新渲染表格状态
      refreshTable: true,
    };
  },

  mounted() {
    this.getTableData();
  },

  methods: {
    // 获取表格数据
    getTableData() {
      getAllMenus(this.queryParams).then((result) => {
        this.menuList = result;
      });
    },
    handleAdd() {
      this.$message.warning("当前功能正在开发中");
    },
    handleQuery() {
      this.getTableData();
    },
    // 点击了重置按钮
    resetQuery() {
      // 重新设置查询参数
      this.queryParams.name = "";
      // 菜单状态
      this.queryParams.enabled = true;
    },

    // 展开/折叠操作
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.menu-container {
  margin-top: 10px;

  .menu-header {
    margin-top: 10px;
    padding-left: 15px;

    .title-label {
      font-size: 14px;
      font-weight: 700;
      color: #606266;
      margin-right: 12px;
    }
  }

  .menu-body {
    margin: 20px 15px 30px 15px;
  }
}
</style>
