package com.quick.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.adapter.ContactAdapter;
import com.quick.adapter.SessionAdapter;
import com.quick.enums.ResponseEnum;
import com.quick.enums.YesNoEnum;
import com.quick.exception.QuickException;
import com.quick.mapper.QuickChatApplyMapper;
import com.quick.pojo.po.QuickChatApply;
import com.quick.pojo.po.QuickChatContact;
import com.quick.pojo.po.QuickChatSession;
import com.quick.service.QuickChatApplyService;
import com.quick.store.QuickChatApplyStore;
import com.quick.store.QuickChatContactStore;
import com.quick.store.QuickChatSessionStore;
import com.quick.utils.RequestContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 申请通知 服务实现类
 * </p>
 *
 * @author 徐志斌
 * @since 2024-03-12
 */
@Service
public class QuickChatApplyServiceImpl extends ServiceImpl<QuickChatApplyMapper, QuickChatApply> implements QuickChatApplyService {
    @Autowired
    private QuickChatApplyStore applyStore;
    @Autowired
    private QuickChatContactStore contactStore;
    @Autowired
    private QuickChatSessionStore sessionStore;

    @Override
    public List<QuickChatApply> getApplyList() {
        String loginAccountId = (String) RequestContextUtil.getData().get(RequestContextUtil.ACCOUNT_ID);
        return applyStore.getListByToId(loginAccountId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean agreeApply(Long applyId) {
        // 查询申请记录
        QuickChatApply apply = applyStore.getByApplyId(applyId);
        if (ObjectUtils.isEmpty(apply)) {
            throw new QuickException(ResponseEnum.FAIL);
        }
        if (YesNoEnum.YES.getCode().equals(apply.getStatus())) {
            throw new QuickException(ResponseEnum.FAIL);
        }

        // 同意申请
        applyStore.updateApplyStatus(applyId, YesNoEnum.YES.getCode());

        // 保存双方通讯录
        String fromId = apply.getFromId();
        String toId = apply.getToId();
        QuickChatContact contact1 = ContactAdapter.buildContactPO(fromId, Long.valueOf(toId), apply.getType(), null);
        QuickChatContact contact2 = ContactAdapter.buildContactPO(fromId, Long.valueOf(toId), apply.getType(), null);
        contactStore.saveContact(contact1);
        contactStore.saveContact(contact2);

        // 保存双方会话
        Long relationId = IdWorker.getId();
        QuickChatSession session1 = SessionAdapter.buildSessionPO(fromId, toId, relationId, apply.getType());
        QuickChatSession session2 = SessionAdapter.buildSessionPO(toId, fromId, relationId, apply.getType());
        sessionStore.saveInfo(session1);
        sessionStore.saveInfo(session2);
        return true;
    }

    @Override
    public Boolean deleteApply(Long applyId) {
        return applyStore.deleteByApplyId(applyId);
    }
}
