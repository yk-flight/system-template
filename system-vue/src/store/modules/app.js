import { LANG, TAGS_VIEW } from "@/constant";
import { getItem, setItem } from "@/utils/storage";

export default {
  namespaced: true,
  state: () => ({
    // 菜单是否折叠
    isCollapse: false,
    // 标签页
    tagsViewList: getItem(TAGS_VIEW) || [
      {
        fullPath: "/dashboard",
        name: "dashboard",
        path: "/dashboard",
        meta: {
          title: "仪表盘",
          icon: "dashboard",
        },
        title: "仪表盘",
      },
    ],
  }),
  mutations: {
    // 折叠侧边栏
    setCollapse: (state) => {
      state.isCollapse = !state.isCollapse;
    },
    // 添加标签页
    addTagsViewList(state, tag) {
      const isFind = state.tagsViewList.find((item) => {
        return item.path === tag.path;
      });
      // 处理重复
      if (!isFind) {
        // 如果不存在重复的标签页
        state.tagsViewList.push(tag);
        setItem(TAGS_VIEW, state.tagsViewList);
      }
    },
    // 为指定的 tag 修改 title
    changeTagsView(state, { index, tag }) {
      state.tagsViewList[index] = tag;
      setItem(TAGS_VIEW, state.tagsViewList);
    },
    /**
     * 关闭标签页
     *
     * @param { type: 'other' || 'right' || 'index' } payload 载荷
     */
    removeTagsView(state, payload) {
      // 关闭当前页
      if (payload.type === "index") {
        // 删除当前指定的一项标签页
        state.tagsViewList.splice(payload.index, 1);
      } else if (payload.type === "other") {
        // 删除当前位置之后的标签页
        state.tagsViewList.splice(
          payload.index + 1,
          state.tagsViewList.length - payload.index + 1
        );
        // 删除当前位置之前的标签页
        state.tagsViewList.splice(1, payload.index - 1);
      } else if (payload.type === "right") {
        // 删除当前位置之后的标签页
        state.tagsViewList.splice(
          payload.index + 1,
          state.tagsViewList.length - payload.index + 1
        );
      }
      setItem(TAGS_VIEW, state.tagsViewList);
    },
    // 关闭全部标签页
    closeAllTagsView(state) {
      (state.tagsViewList = [
        {
          fullPath: "/dashboard",
          name: "dashboard",
          path: "/dashboard",
          meta: {
            title: "仪表盘",
            icon: "dashboard",
          },
          title: "仪表盘",
        },
      ]),
        // 更新存储的标签页
        setItem(TAGS_VIEW, state.tagsViewList);
    },
  },
};
