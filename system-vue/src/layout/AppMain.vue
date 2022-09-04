<template>
  <div class="app-main">
    <!-- 带有切换动画，并且具备组件缓存的 -->
    <transition name="fade-transform" mode="out-in">
      <router-view />
    </transition>
  </div>
</template>

<script>
import { isTags } from "@/utils/tags";

export default {
  name: "AppMain",
  mounted() {},
  data() {
    return {};
  },
  watch: {
    $route(to, from) {
      this.addTagsViewList(to);
    },
  },
  methods: {
    // 添加到标签栏
    addTagsViewList(to) {
      // 并不是所有的路由都不需要保存
      if (!isTags(to.path)) return;
      // 从 to 中解构出想要的属性
      const { fullPath, meta, name, params, path, query } = to;
      this.$store.commit("app/addTagsViewList", {
        fullPath,
        meta,
        name,
        params,
        path,
        query,
        title: this.$route.name,
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.app-main {
  // 浏览器可视区域的高度 100vh
  min-height: calc(100vh - 50px - 43px);
  width: 100%;
  position: relative;
  overflow: hidden;
  padding: 104px 20px 20px 20px;
  box-sizing: border-box;
}
</style>
