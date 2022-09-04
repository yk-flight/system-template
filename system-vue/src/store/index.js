import Vue from "vue";
import Vuex from "vuex";
import getters from "./getter";

import app from "./modules/app";
import permission from "./modules/permisssion";
import user from "./modules/user";

Vue.use(Vuex);

export default new Vuex.Store({
  getters,
  modules: {
    app,
    permission,
    user,
  },
});
