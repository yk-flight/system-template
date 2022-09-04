import { getItem, setItem } from "@/utils/storage";
import { TIME_STAMP, TOKEN_TIMEOUT_VALUE } from "../constant";

/**
 * 获取时间戳
 */
export function getTimeStamp() {
  // 将保存到登录时间进行返回
  return getItem(TIME_STAMP);
}

/**
 * 设置时间戳
 */
export function setTimeStamp() {
  setItem(TIME_STAMP, Date.now());
}

/**
 * 对比当前时间和登录时间查看是否超时
 */
export function isCheckTimeout() {
  // 当前时间
  const currentTime = Date.now();
  // 缓存时间
  const timeStamp = getTimeStamp();
  // 判断是否超时并返回
  return currentTime - timeStamp > TOKEN_TIMEOUT_VALUE;
}
