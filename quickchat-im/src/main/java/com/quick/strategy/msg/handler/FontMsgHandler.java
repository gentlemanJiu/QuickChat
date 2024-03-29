package com.quick.strategy.msg.handler;

import com.quick.adapter.ChatMsgAdapter;
import com.quick.enums.ChatMsgEnum;
import com.quick.pojo.dto.ChatMsgDTO;
import com.quick.pojo.po.QuickChatMsg;
import com.quick.store.QuickChatMsgStore;
import com.quick.strategy.msg.AbstractChatMsgStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 徐志斌
 * @CreateTime: 2023-11-17  17:02
 * @Description: 文字消息
 * @Version: 1.0
 */
@Component
public class FontMsgHandler extends AbstractChatMsgStrategy {
    @Autowired
    private QuickChatMsgStore msgStore;

    @Override
    protected ChatMsgEnum getEnum() {
        return ChatMsgEnum.FONT;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuickChatMsg sendMsg(ChatMsgDTO msgDTO) throws Throwable {
        String fromId = msgDTO.getFromId();
        String toId = msgDTO.getToId();
        String content = msgDTO.getContent();
        QuickChatMsg chatMsg = ChatMsgAdapter.buildChatMsgPO(fromId, toId, content, null, this.getEnum().getCode());
        msgStore.saveMsg(chatMsg);
        return chatMsg;
    }
}
