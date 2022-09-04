package com.zrkizzy.template.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回对象
 *
 * @author zhangrongkang
 * @date 2022/8/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {

    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据集合
     */
    private List<?> list;
}
