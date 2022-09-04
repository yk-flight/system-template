<template>
  <div @click="onToggle">
    <svg-icon
      :icon="isFullScreen ? 'exit-fullscreen' : 'fullscreen'"
    ></svg-icon>
  </div>
</template>

<script>
import screenfull from "screenfull";

export default {
  name: "Screenfull",

  data() {
    return {
      // 当前是否为全屏状态
      isFullScreen: false,
    };
  },
  // on: 绑定监听
  mounted() {
    screenfull.on("change", this.change);
  },
  // off: 取消监听
  unmounted() {
    screenfull.off("change", this.change);
  },
  methods: {
    // 触发事件
    onToggle() {
      screenfull.toggle();
    },
    // 监听 screenfull 的变化
    change() {
      this.isFullScreen = screenfull.isFullscreen;
    },
  },
};
</script>

<style lang="scss" scoped></style>
