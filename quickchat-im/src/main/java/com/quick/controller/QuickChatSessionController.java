package com.quick.controller;

import com.quick.enums.ResponseEnum;
import com.quick.pojo.vo.ChatSessionVO;
import com.quick.response.R;
import com.quick.service.QuickChatSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author 徐志斌
 * @Date: 2023/11/19 11:11
 * @Version 1.0
 * @Description: 聊天会话
 */
@Api(tags = "聊天会话")
@RestController
@RequestMapping("/session")
public class QuickChatSessionController {
    @Autowired
    private QuickChatSessionService sessionService;

    @ApiOperation("查询列表")
    @GetMapping("/list")
    public R getSessionList() {
        List<ChatSessionVO> result = sessionService.getSessionList();
        return R.out(ResponseEnum.SUCCESS, result);
    }

    @ApiOperation("查询详情")
    @GetMapping("/detail")
    public R getSessionDetail(String fromId, String toId) {
        ChatSessionVO result = sessionService.getByFromIdAndToId(fromId, toId);
        return R.out(ResponseEnum.SUCCESS, result);
    }

    @ApiOperation("恢复会话")
    @PostMapping("/active")
    public R activeSession(@NotBlank(message = "目标账号id参数不能为空") String toId) {
        sessionService.activeSession(toId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("更新已读时间")
    @PostMapping("/updateReadTime")
    public R updateReadTime(@NotNull(message = "会话id参数不能为空") Long sessionId) {
        sessionService.updateReadTime(sessionId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("删除会话")
    @DeleteMapping("/delete")
    public R deleteSession(@NotNull(message = "会话id参数不能为空") Long sessionId) {
        sessionService.deleteSession(sessionId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("置顶会话")
    @DeleteMapping("/top")
    public R topSession(@NotNull(message = "会话id参数不能为空") Long sessionId) {
        sessionService.topSession(sessionId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("消息免打扰")
    @DeleteMapping("/noDisturb")
    public R noDisturb(@NotBlank(message = "会话id参数不能为空") Long sessionId) {
        return R.out(ResponseEnum.SUCCESS);
    }
}
