package com.quick.controller;

import com.quick.enums.ResponseEnum;
import com.quick.pojo.dto.GroupDTO;
import com.quick.response.R;
import com.quick.service.QuickChatGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 徐志斌
 * @CreateTime: 2023-11-21  10:05
 * @Description: 群组信息
 * @Version: 1.0
 */
@Api(tags = "群组信息")
@RestController
@RequestMapping("/group")
public class QuickChatGroupController {
    @Autowired
    private QuickChatGroupService groupService;

    @ApiOperation("创建群聊")
    @PostMapping("/create")
    public R create(@Validated @RequestBody GroupDTO group) {
        groupService.createGroup(group);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("修改群信息")
    @GetMapping("/update")
    public R updateInfo(@Validated @RequestBody GroupDTO group) {
        groupService.updateInfo(group);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("解散群聊")
    @DeleteMapping("/release")
    public R releaseGroup(@NotBlank(message = "群聊id参数不能为空") Long groupId) {
        groupService.releaseGroup(groupId);
        return R.out(ResponseEnum.SUCCESS);
    }

    @ApiOperation("退出群聊")
    @GetMapping("/exit")
    public R exitGroup(@NotBlank(message = "群聊id参数不能为空") Long groupId) {
        groupService.exitGroup(groupId);
        return R.out(ResponseEnum.SUCCESS);
    }
}
