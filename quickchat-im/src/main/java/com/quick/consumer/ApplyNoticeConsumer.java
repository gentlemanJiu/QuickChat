package com.quick.consumer;

import cn.hutool.json.JSONUtil;
import com.quick.constant.KafkaConstant;
import com.quick.enums.WsPushEnum;
import com.quick.netty.UserChannelRelation;
import com.quick.pojo.entity.WsPushEntity;
import com.quick.pojo.po.QuickChatApply;
import com.quick.pojo.po.QuickChatGroupMember;
import com.quick.store.QuickChatGroupMemberStore;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 徐志斌
 * @CreateTime: 2024-03-13  16:07
 * @Description: 好友、群聊申请消费者
 * @Version: 1.0
 */
@Component
public class ApplyNoticeConsumer {
    @Autowired
    private QuickChatGroupMemberStore memberStore;

    /**
     * 好友申请通知
     */
    @KafkaListener(topics = KafkaConstant.FRIEND_APPLY_TOPIC, groupId = KafkaConstant.CHAT_SEND_GROUP_ID)
    public void sendFriendApply(String message) {
        QuickChatApply apply = JSONUtil.parse(message).toBean(QuickChatApply.class);
        Channel channel = UserChannelRelation.getUserChannelMap().get(apply.getToId());
        if (ObjectUtils.isNotEmpty(channel)) {
            WsPushEntity<QuickChatApply> pushEntity = new WsPushEntity<>();
            pushEntity.setPushType(WsPushEnum.FRIEND_APPLY_NOTICE.getCode());
            pushEntity.setMessage(apply);
            channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(pushEntity)));
        }
    }

    /**
     * 群聊申请通知
     */
    @KafkaListener(topics = KafkaConstant.GROUP_APPLY_TOPIC, groupId = KafkaConstant.CHAT_SEND_GROUP_ID)
    public void sendGroupNotice(String message) {
        QuickChatApply apply = JSONUtil.parse(message).toBean(QuickChatApply.class);
        List<QuickChatGroupMember> members = memberStore.getListByGroupId(apply.getGroupId());
        for (QuickChatGroupMember member : members) {
            Channel channel = UserChannelRelation.getUserChannelMap().get(member.getAccountId());
            if (ObjectUtils.isNotEmpty(channel)) {
                WsPushEntity<QuickChatApply> pushEntity = new WsPushEntity<>();
                pushEntity.setPushType(WsPushEnum.GROUP_APPLY_NOTICE.getCode());
                pushEntity.setMessage(apply);
                channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(pushEntity)));
            }
        }
    }
}
