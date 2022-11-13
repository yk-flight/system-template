<template>
  <div class="tags-view-container">
    <div class="tags-view-left">
      <router-link
        class="tags-view-item"
        v-for="(tag, index) in this.$store.getters.tagsViewList"
        :class="isActive(tag) ? 'active' : ''"
        :key="tag.fullPath"
        :to="{ path: tag.fullPath }"
        :style="{
          backgroundColor: isActive(tag) ? '#409EFF' : '',
        }"
      >
        {{ tag.meta.title }}
        <i
          v-show="tag.path != '/dashboard'"
          class="el-icon-close"
          @click.prevent.stop="onCloseClick(index)"
        ></i>
      </router-link>
    </div>
    <!-- 关闭全部 -->
    <div class="tags-view-right">
      <span class="tags-view-item" @click="closeAll">关闭全部</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "TagsView",
  data() {
    return {
      // 鼠标右键
      selectIndex: 0,
    };
  },

  methods: {
    // 是否被选中
    isActive(tag) {
      return tag.path === this.$route.path;
    },
    // 关闭 tag 的点击事件
    onCloseClick(index) {
      this.selectIndex = index;
      this.$store.commit("app/removeTagsView", {
        type: "index",
        index,
      });
      // 如果关闭的是当前标签页则返回上一个标签页
      if (index === this.selectIndex) {
        this.$router.push(
          this.$store.getters.tagsViewList[
            this.$store.getters.tagsViewList.length - 1
          ].path
        );
      }
    },
    // 关闭全部标签事件
    closeAll() {
      this.$store.commit("app/closeAllTagsView");
      // 跳转路由到仪表盘
      this.$router.push("/dashboard");
    },
  },
};
</script>

<style lang="scss" scoped>
.tags-view-container {
  display: flex;
  justify-content: space-between;
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);

  .tags-view-left {
    width: calc(100% - 100px);
    /* 设置超出滚动 */
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;
  }

  .tags-view-right {
    width: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    right: 10px;
  }

  .tags-view-item {
    text-decoration: none;
    display: inline-block;
    position: relative;
    cursor: pointer;
    height: 26px;
    line-height: 26px;
    border: 1px solid #d8dce5;
    color: #495060;
    background: #fff;
    padding: 0 8px;
    font-size: 12px;
    margin-left: 5px;
    margin-top: 4px;
    margin-bottom: 2px;
    &:first-of-type {
      margin-left: 15px;
    }
    &:last-of-type {
      margin-right: 15px;
    }
    &.active {
      color: #fff;
      &::before {
        content: "";
        background: #fff;
        display: inline-block;
        width: 8px;
        height: 8px;
        border-radius: 50%;
        position: relative;
        margin-right: 4px;
      }
    }
    // close 按钮
    .el-icon-close {
      width: 16px;
      height: 16px;
      line-height: 10px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
      transform-origin: 100% 50%;
      &:before {
        transform: scale(0.6);
        display: inline-block;
        vertical-align: -3px;
      }
      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }
  }
}
</style>
