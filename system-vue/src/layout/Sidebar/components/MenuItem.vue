<template>
  <div>
    <!-- element icon -->
    <i v-if="icon.includes('el-icon')" class="sub-el-icon" :class="icon"></i>
    <!-- 非 element icon -->
    <svg-icon v-else :icon="icon"></svg-icon>
    <!-- 文本 -->
    <span>{{ title }}</span>
  </div>
</template>

<script>
export default {
  name: "MenuItem",
  props: {
    title: {
      type: String,
      require: true,
    },
    icon: {
      type: String,
      require: true,
    },
  },

  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped></style>
