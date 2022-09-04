import dayjs from "dayjs";
import rt from "dayjs/plugin/relativeTime";
import "dayjs/locale/zh-cn";
import store from "@/store";

// 具体时间
const dateFilter = (val, format = "YYYY-MM-DD HH:mm:ss") => {
  // 如果当前的 val 可以被转换为一个数字
  if (!isNaN(val)) {
    val = parseInt(val);
  }
  return dayjs(val).format(format);
};

dayjs.extend(rt);
// 相对时间
function relativeTime(val) {
  // 如果当前的 val 可以被转换为一个数字
  if (!isNaN(val)) {
    val = parseInt(val);
  }
  return dayjs()
    .locale(store.getters.language === "zh" ? "zh-cn" : "en")
    .to(dayjs(val));
}

// 时间戳显示过滤器
export default {
  dateFilter,
  relativeTime,
};
