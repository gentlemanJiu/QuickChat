package com.quick.controller;

import com.quick.enums.ResponseEnum;
import com.quick.pojo.po.QuickChatApply;
import com.quick.response.R;
import com.quick.service.QuickChatApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 徐志斌
 * @CreateTime: 2024-07-04  15:45
 * @Description: 申请通知
 * @Version: 1.0
 */
@Api(tags = "申请通知")
@RestController
@RequestMapping("/apply")
public class QuickChatApplyController {
    @Autowired
    private QuickChatApplyService applyService;

    @ApiOperation("查询列表")
    @PostMapping("/list")
    public R getApplyList() {
        List<QuickChatApply> list = applyService.getApplyList();
        return R.out(ResponseEnum.SUCCESS, list);
    }

    @ApiOperation("同意申请")
    @PostMapping("/agree")
    public R agreeApply(@NotNull(message = "申请记录id参数不存在") Long applyId) {
        applyService.agreeApply(applyId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("删除申请")
    @PostMapping("/delete")
    public R deleteApply(@NotNull(message = "申请记录id参数不存在") Long applyId) {
        applyService.deleteApply(applyId);
        return R.out(ResponseEnum.SUCCESS);
    }
}
