package com.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.pojo.po.QuickChatFriend;

/**
 * <p>
 * 聊天好友 服务类
 * </p>
 *
 * @author 徐志斌
 * @since 2023-11-30
 */
public interface QuickChatFriendService extends IService<QuickChatFriend> {

    void getFriendList();

    void addFriend();

    void deleteFriend();
}
