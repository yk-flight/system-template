<template>
  <div class="leave-container">
    <div class="icon-container">
      <svg-icon icon="message" class="icon-style"></svg-icon>
    </div>
    <div class="content-container">
      <p class="font">留言数量</p>
      <p class="font-num">1000</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "LeaveView",

  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
.leave-container {
  padding-top: 10px;
  height: 70px;
  width: 100%;
  .icon-container {
    float: left;
    height: 100%;
    padding-left: 20px;

    .icon-style {
      color: #f4516c;
      font-size: 60px;
      font-weight: 900;
    }
  }

  .content-container {
    padding-right: 25px;
    float: right;

    .font {
      margin-top: 0;
      line-height: 18px;
      color: rgba(0, 0, 0, 0.45);
      font-size: 16px;
      margin-bottom: 12px;
      font-weight: 700;
    }

    .font-num {
      margin-top: 0;
      color: #666;
      font-size: 20px;
      font-weight: 800;
    }
  }
}
</style>
