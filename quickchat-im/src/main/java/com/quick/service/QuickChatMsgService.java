package com.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.pojo.dto.ChatMsgDTO;
import com.quick.pojo.po.QuickChatMsg;
import com.quick.pojo.vo.ChatMsgVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 聊天信息 服务类
 * </p>
 *
 * @author 徐志斌
 * @since 2023-11-25
 */
public interface QuickChatMsgService extends IService<QuickChatMsg> {
    /**
     * 根据 relation_id 分页查询聊天信息
     *
     * @param relationId 关联id
     * @param current    当前页
     * @param size       每页条数
     * @return 聊天信息集合
     */
    Map<Long, List<ChatMsgVO>> getByRelationId(Long relationId, Integer current, Integer size);

    /**
     * 根据 account_id 集合批量查询聊天记录
     *
     * @param relationIds 会话列表
     * @return 聊天信息
     */
    Map<Long, List<ChatMsgVO>> getByRelationIds(List<Long> relationIds, Integer size);

    /**
     * 发送聊天信息
     *
     * @param msgDTO 消息实体
     */
    void sendMsg(ChatMsgDTO msgDTO) throws Throwable;

    /**
     * 对方正在输入...
     *
     * @param fromId 发送方
     * @param toId   接收方
     */
    void writing(String fromId, String toId);
}
