<template>
  <div>
    <el-row>
      <el-col :xs="24" :md="12" :style="{ height: '350px' }">
        <vue-cropper
          ref="cropper"
          :img="options.img"
          :info="true"
          :autoCrop="options.autoCrop"
          :autoCropWidth="options.autoCropWidth"
          :autoCropHeight="options.autoCropHeight"
          :canMove="options.canMove"
          :fixedBox="options.fixedBox"
          @realTime="realTime"
        />
      </el-col>
      <el-col :xs="24" :md="12" :style="{ height: '350px' }">
        <div class="avatar-upload-preview">
          <img :src="previews.url" :style="previews.img" />
        </div>
      </el-col>
    </el-row>
    <br />
    <el-row>
      <el-col :lg="2" :md="2">
        <el-upload
          action="#"
          :http-request="requestUpload"
          :before-upload="beforeAvatarUpload"
          :show-file-list="false"
        >
          <el-button size="small">
            选择
            <i class="el-icon-upload el-icon--right"></i>
          </el-button>
        </el-upload>
      </el-col>
      <el-col :lg="{ span: 1, offset: 2 }" :md="2">
        <el-button
          icon="el-icon-plus"
          size="small"
          @click="changeScale(1)"
        ></el-button>
      </el-col>
      <el-col :lg="{ span: 1, offset: 1 }" :md="2">
        <el-button
          icon="el-icon-minus"
          size="small"
          @click="changeScale(-1)"
        ></el-button>
      </el-col>
      <el-col :lg="{ span: 1, offset: 1 }" :md="2">
        <el-button
          icon="el-icon-refresh-left"
          size="small"
          @click="rotateLeft()"
        ></el-button>
      </el-col>
      <el-col :lg="{ span: 1, offset: 1 }" :md="2">
        <el-button
          icon="el-icon-refresh-right"
          size="small"
          @click="rotateRight()"
        ></el-button>
      </el-col>
      <el-col :lg="{ span: 2, offset: 6 }" :md="2">
        <el-button type="primary" size="small" @click="uploadImg()"
          >提 交</el-button
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { VueCropper } from "vue-cropper";
import { uploadAvatar, updateAvatarById } from "@/api/user";

export default {
  name: "AvatarUp",
  components: { VueCropper },
  data() {
    return {
      options: {
        img: this.$store.getters.userInfo.avatar, //裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 100, // 默认生成截图框宽度
        autoCropHeight: 100, // 默认生成截图框高度
        canMoveBox: true, // 截图框能否拖动
        fixedBox: true, // 固定截图框大小 不允许改变
      },
      previews: {},
      // 上传的文件名称
      imageName: "",
      avatarVO: {},
    };
  },

  mounted() {},

  methods: {
    beforeAvatarUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError(
          "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。"
        );
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
      this.imageName = file.name;
    },
    // 实时预览
    realTime(data) {
      this.previews = data;
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob((data) => {
        let formData = new FormData();
        var fileOfBlob = new File([data], this.imageName);
        formData.append("file", fileOfBlob);
        uploadAvatar(formData).then((result) => {
          this.options.img = result;
          this.avatarVO.avatar = result;
          // 更新用户信息
          updateAvatarById(this.avatarVO).then();
          // 重新设置用户登录信息
          this.$store.dispatch("user/getUserInfo");
        });
      });
    },
    // 设置需要展示的图片  base64
    setAvatarBase64(src, callback) {
      let _this = this;
      let image = new Image();
      // 处理缓存
      image.src = src + "?v=" + Math.random();
      // 支持跨域图片
      image.crossOrigin = "*";
      image.onload = function () {
        let base64 = _this.transBase64FromImage(image);
        callback && callback(base64);
      };
    },
    // 将网络图片转换成base64格式
    transBase64FromImage(image) {
      let canvas = document.createElement("canvas");
      canvas.width = image.width;
      canvas.height = image.height;
      let ctx = canvas.getContext("2d");
      ctx.drawImage(image, 0, 0, image.width, image.height);
      // 可选其他值 image/jpeg
      return canvas.toDataURL("image/jpeg");
    },
  },
};
</script>

<style lang="scss" scoped>
.user-info-head:hover:after {
  content: "+";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}

.avatar-upload-preview {
  position: absolute;
  top: 50%;
  -webkit-transform: translate(50%, -50%);
  transform: translate(50%, -50%);
  width: 200px;
  height: 200px;
  border-radius: 50%;
  -webkit-box-shadow: 0 0 4px #ccc;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}
</style>
