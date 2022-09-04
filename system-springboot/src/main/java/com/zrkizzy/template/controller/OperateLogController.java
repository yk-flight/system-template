package com.zrkizzy.template.controller;


import com.zrkizzy.template.service.IOperateLogService;
import com.zrkizzy.template.vo.PageVO;
import com.zrkizzy.template.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangrongkang
 * @since 2022-09-03
 */
@RestController
@RequestMapping("/operate-log")
@Api(tags = "OperateLogController")
public class OperateLogController {

    @Resource
    private IOperateLogService operateLogService;

    @ApiOperation("获取日志记录")
    @GetMapping("/list")
    public PageVO getOperateLogList(@RequestParam("curPage") Integer curPage,
                                    @RequestParam("size") Integer size,
                                    @RequestParam("user") String user,
                                    @RequestParam("module") String module) {
        return operateLogService.getOperateLogList(curPage, size, user, module);
    }

    @ApiOperation("批量删除日志记录")
    @DeleteMapping("/deleteLogBatchIds/{ids}")
    public Result deleteLogBatchIds(@PathVariable Integer[] ids) {
        boolean result = operateLogService.removeByIds(Arrays.asList(ids));
        if (result) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
