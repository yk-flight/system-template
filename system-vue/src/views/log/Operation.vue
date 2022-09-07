<template>
  <div class="log-container">
    <el-card shadow="never">
      <div class="log-header">
        <el-form :inline="true">
          <el-form-item>
            <span class="title-label">系统模块</span>
            <el-input
              v-model="module"
              placeholder="请输入系统模块"
              style="width: 250px"
            ></el-input>
          </el-form-item>

          <el-form-item>
            <span class="title-label">操作人员</span>
            <el-input
              v-model="user"
              placeholder="请输入操作人员"
              style="width: 250px"
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
          <el-button type="warning" plain icon="el-icon-download" size="mini">
            导出
          </el-button>
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            @click="handleDeleteBatch"
          >
            批量删除
          </el-button>
        </div>
      </div>

      <div class="log-body">
        <el-table
          :header-cell-style="{ background: '#f8f8f9', color: '#606266' }"
          ref="table"
          row-key="id"
          :data="logList"
          @selection-change="handleSelectionChange"
          border
          style="width: 100%"
          size="medium"
        >
          <el-table-column
            type="selection"
            width="40"
            :reserve-selection="true"
          >
          </el-table-column>
          <el-table-column label="序号" width="50" align="center">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="module" label="系统模块" align="center">
          </el-table-column>
          <el-table-column prop="description" label="操作描述" align="center">
          </el-table-column>
          <el-table-column label="请求方式" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.requestMethod === 'PUT'">{{
                scope.row.requestMethod
              }}</el-tag>
              <el-tag
                v-else-if="scope.row.requestMethod === 'GET'"
                type="success"
                >{{ scope.row.requestMethod }}</el-tag
              >
              <el-tag
                v-else-if="scope.row.requestMethod === 'POST'"
                type="warning"
                >{{ scope.row.requestMethod }}</el-tag
              >
              <el-tag v-else type="danger">{{
                scope.row.requestMethod
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="user" label="操作人员" align="center">
          </el-table-column>
          <el-table-column prop="operateIp" label="操作地址" align="center">
          </el-table-column>
          <el-table-column prop="operateSource" label="操作地点" align="center">
          </el-table-column>
          <el-table-column prop="status" label="操作状态" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status" type="success">成功</el-tag>
              <el-tag v-else type="danger">失败</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作日期" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.operateTime | dateFilter }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleShow(scope.row)"
              >
                详细
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="log-footer">
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

    <el-dialog
      title="操作日志详细"
      width="750px"
      :visible="visible"
      :before-close="handleClose"
    >
      <el-form label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="系统模块：">
              {{ log.module }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作描述：">
              {{ log.description }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人员：">
              {{ log.user }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求方式：">
              <el-tag v-if="log.requestMethod === 'PUT'">
                {{ log.requestMethod }}
              </el-tag>
              <el-tag v-else-if="log.requestMethod === 'GET'" type="success">
                {{ log.requestMethod }}
              </el-tag>
              <el-tag v-else-if="log.requestMethod === 'POST'" type="warning">
                {{ log.requestMethod }}
              </el-tag>
              <el-tag v-else type="danger">
                {{ log.requestMethod }}
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">
              {{ log.operateMethod }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">
              {{ log.requestParam }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作地址：">
              {{ log.operateIp }}
            </el-form-item>
            <el-form-item label="操作地点：">
              {{ log.operateSource }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <el-tag v-if="log.status" type="success">成功</el-tag>
              <el-tag v-else type="danger">失败</el-tag>
            </el-form-item>
            <el-form-item label="执行时长：">
              {{ log.duration / 1000 }}s
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作日期：">
              {{ log.operateTime | dateFilter }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态描述：">
              {{ log.statusDescription }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getOperateLogList, deleteLogBatchIds } from "@/api/log.js";

export default {
  name: "Operation",

  data() {
    return {
      // 操作人员
      user: "",
      // 系统模块
      module: "",
      // 当前页数
      curPage: 1,
      // 页面大小
      size: 10,
      // 用户数据
      logList: [],
      // 数据总数
      total: 0,
      // log对象
      log: {},
      // 表格多选框
      multipleSelection: [],
      // 详细对话框
      visible: false,
    };
  },

  mounted() {
    this.getTableData();
  },

  methods: {
    // 获取表格数据
    getTableData() {
      getOperateLogList({
        curPage: this.curPage,
        size: this.size,
        user: this.user,
        module: this.module,
      }).then((result) => {
        this.logList = result.list;
        this.total = result.total;
      });
    },
    // 点击了查询按钮
    handleQuery() {
      this.getTableData();
    },
    // 点击了重置按钮
    resetQuery() {
      this.user = "";
      this.module = "";
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
    // 表格多选框点击事件
    handleSelectionChange(val) {
      this.multipleSelection = val; //存储选中的数据
    },
    // 点击详细按钮
    handleShow(row) {
      this.log = row;
      // 显示对话框
      this.visible = true;
    },
    // 关闭对话框
    handleClose() {
      this.visible = false;
      // 重置对话框相关数据
      this.reset();
    },
    // 重置对话框内容
    reset() {
      // 修改对话框对象
      this.log = {};
    },
    // 点击批量删除数据
    handleDeleteBatch() {
      // 将选中的id进行存储
      const ids = [];
      this.multipleSelection.forEach((item) => {
        ids.push(item.id);
      });
      this.$confirm("确定删除这些操作日志吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 执行删除操作
          deleteLogBatchIds(ids).then(() => {
            // 重新获取表格数据
            this.getTableData();
            // 重置当前批量删除多选的数据
            this.multipleSelection = [];
          });
        })
        .catch(() => {});
    },
  },
};
</script>

<style lang="scss" scoped>
.log-container {
  margin-top: 10px;

  .log-header {
    margin-top: 10px;
    padding-left: 15px;

    .title-label {
      font-size: 14px;
      font-weight: 700;
      color: #606266;
      margin-right: 12px;
    }
  }

  .log-body {
    margin: 20px 15px 30px 15px;
  }

  .log-footer {
    padding-left: 15px;
    margin-top: 40px;
  }
}
</style>
