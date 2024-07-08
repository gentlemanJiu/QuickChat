package com.quick.store;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.pojo.po.QuickChatContact;

import java.util.List;

/**
 * <p>
 * 通讯录 服务类
 * </p>
 *
 * @author 徐志斌
 * @since 2023-11-30
 */
public interface QuickChatContactStore extends IService<QuickChatContact> {
    /**
     * 根据 from_id 查询通讯录列表
     *
     * @param fromId 用户id
     * @return 通讯录列表
     */
    List<QuickChatContact> getListByFromId(String fromId);

    /**
     * 根据 from_id to_id 查询好友信息
     *
     * @param fromId 账号id
     * @param toId   账号id
     * @return 通讯录信息
     */
    QuickChatContact getByFromIdAndToId(String fromId, String toId);

    /**
     * 根据 from_id to_id 删除好友信息
     *
     * @param fromId 账号id
     * @param toId   账号id
     * @return 执行结果
     */
    Boolean deleteByFromIdAndToId(String fromId, String toId);

    /**
     * 保存通讯录
     *
     * @param contact 通讯录信息
     * @return 执行结果
     */
    Boolean saveContact(QuickChatContact contact);

    /**
     * 批量保存通讯录
     *
     * @param contacts 通讯录列表
     * @return 执行结果
     */
    Boolean saveContactList(List<QuickChatContact> contacts);
}
