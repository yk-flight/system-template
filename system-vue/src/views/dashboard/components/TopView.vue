<template>
  <div class="topView-container">
    <el-row :gutter="25">
      <el-col :span="6">
        <el-card shadow="hover">
          <access-view></access-view>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><article-view></article-view></el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><leave-view></leave-view></el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><user-view></user-view></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import AccessView from "@/components/AccessView/index";
import ArticleView from "@/components/ArticleView/index";
import LeaveView from "@/components/LeaveView/index";
import UserView from "@/components/UserView/index";

export default {
  name: "TopView",
  components: { AccessView, ArticleView, LeaveView, UserView },
  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
</style>
