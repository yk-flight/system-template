import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./permission";
// ======================= 导入外部组件 ========================
import ElementUI from "element-ui";
import SvgIcons from "@/icons";
import filters from "@/filter/index";
import PerfectScrollbar from "vue2-perfect-scrollbar"
// ======================= 导入样式 ========================
import "./styles/index.scss";
import "nprogress/nprogress.css";
import "element-ui/lib/theme-chalk/index.css";
import "vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css"

Vue.use(ElementUI);
Vue.use(SvgIcons);
Vue.use(PerfectScrollbar)

Vue.config.productionTip = false;

Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key]);
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
