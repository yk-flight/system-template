/**
 * 存储数据
 */
export const setItem = (key, value) => {
  // value 分为两种情况
  // 1. 基本数据类型无需做任何处理
  // 2. 复杂数据类型
  if (typeof value === "object") {
    // 转换为 JSON 格式的字符串
    value = JSON.stringify(value);
  }
  // 转换后将 key 和 value 存储到本地
  window.localStorage.setItem(key, value);
};

/**
 * 获取数据
 */
export const getItem = (key) => {
  const data = window.localStorage.getItem(key);
  // 取出数据以后再进行是否为 JSON 的判断过于繁琐
  try {
    // 取出的数据直接转为 JSON
    return JSON.parse(data);
  } catch (error) {
    // 如果出错就说明原本数据不属于 JSON ，直接返回即可
    return data;
  }
};

/**
 * 删除指定数据
 */
export const removeItem = (key) => {
  window.localStorage.removeItem(key);
};

/**
 * 删除所有数据
 */
export const removeAllItem = () => {
  console.log("删除所有数据");
  window.localStorage.clear();
};
