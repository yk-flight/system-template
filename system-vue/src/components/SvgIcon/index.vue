<template>
  <!-- 展示外部图标 -->
  <div
    v-if="isExternal"
    :style="styleExternalIcon"
    class="svg-external-icon svg-icon"
    :class="className"
  ></div>
  <!-- 展示内部图标 -->
  <svg v-else class="svg-icon" :class="className" aria-hidden="true">
    <use :xlink:href="iconName" />
  </svg>
</template>

<script>
import { isExternal as external } from "@/utils/validate";

export default {
  name: "SvgIcon",
  props: {
    // icon 图标
    icon: {
      type: String,
      required: true,
    },
    // 图标类名
    className: {
      type: String,
      default: "",
    },
  },

  data() {
    return {};
  },

  mounted() {},
  computed: {
    /**
     * 判断当前图标是否为外部图标
     */
    isExternal() {
      return external(this.icon);
    },
    /**
     * 外部图标样式
     */
    styleExternalIcon() {
      return {
        mask: `url(${this.icon}) no-repeat 50%`,
        "-webkit-mask": `url(${this.icon}) no-repeat 50% 50%`,
      };
    },
    iconName() {
      return `#icon-${this.icon}`;
    },
  },
};
</script>

<style lang="scss" scpoed>
.svg-icon {
  width: 1em;
  height: 1em;
  fill: currentColor;
  overflow: hidden;
}

.svg-external-icon {
  background-color: currentColor;
  mask-size: cover !important;
  display: inline-block;
}
</style>
