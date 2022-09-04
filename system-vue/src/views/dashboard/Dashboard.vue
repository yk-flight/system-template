<template>
  <div class="dashboard-container">
    <!-- 头部视图 -->
    <top-view></top-view>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>后台管理系统</span>
          </div>
          <div class="body">
            <p>
              一直想开发一个属于自己的后台管理系统模板，但是自己最初没有接触过前后端分离的系统，开发前端时被各种样式和菜单动态渲染搞得痛不欲生，
              开发后端时被Spring
              Security虐的体无完肤，期间无数次想过用熟练一套优秀的开源框架，但是本着这样一个浮躁的心态去学习，最终的结果就是再次被各种优秀的开源框架摁在地上摩擦......
              <br /><br />
              痛定思痛，深刻意识到自己应该沉下心来去踏踏实实的学习技术。终于在一段时间以后，我将之前的问题一一解决并且成功的看懂了一些优秀的开源框架，内心对自己开发一套后台管理系统模板的渴望
              愈发强烈，有想法就去做，最后我终于开发出了属于自己的一套后台管理系统模板。<br /><br />
              <b
                >由于本人缺乏设计美感，开发过程中前端样式参考了若依框架、element-admin、风宇的个人博客、楠橘星后台管理的样式进行开发。</b
              >当下项目仍然有很多局限，后续我会继续迭代优化<br /><br />
            </p>
            <p>
              <svg-icon icon="gitee"></svg-icon>&nbsp;
              <el-link
                href="https://gitee.com/dream-flight/system--template"
                target="_blank"
              >
                https://gitee.com/dream-flight/system--template
              </el-link>
            </p>
            <p>
              <svg-icon icon="github"></svg-icon>&nbsp; GitHub：
              <el-link
                href="https://github.com/Architect-Java/system-template"
                target="_blank"
              >
                https://github.com/Architect-Java/system-template
              </el-link>
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>技术选型</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="前端技术">
              <ol>
                <li>Vue</li>
                <li>Vuex</li>
                <li>Element-ui</li>
                <li>Axios</li>
                <li>Scss</li>
                <li>dayjs...</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
          <el-collapse accordion>
            <el-collapse-item title="后端技术">
              <ol>
                <li>Spring Boot</li>
                <li>Mybatis-Plus</li>
                <li>Spring Security</li>
                <li>JWT</li>
                <li>Fastjson</li>
                <li>kaptcha...</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
          <el-collapse accordion>
            <el-collapse-item title="开发环境">
              <ol>
                <li>JDK1.8</li>
                <li>MySQL8.0.13</li>
                <li>Maven3.3.9</li>
                <li>Redis3.2...</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>学习交流</span>
          </div>
          <div style="text-align: center">
            <img src="../../assets/image/QQ.png" alt="donate" width="65%" />
            <span
              style="display: inline-block; height: 30px; line-height: 30px"
            >
              欢迎大家加入QQ群聊一起交流学习！
            </span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TopView from "./components/TopView.vue";

export default {
  name: "Dashboard",
  components: { TopView },
  data() {
    return {
      // 版本号
      version: "3.8.3",
    };
  },

  mounted() {},

  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style lang="scss" scoped>
.dashboard-container {
  height: 100%;
  width: 100%;

  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
