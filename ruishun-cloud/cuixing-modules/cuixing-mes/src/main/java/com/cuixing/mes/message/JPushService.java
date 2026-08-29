package com.cuixing.mes.message;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.message.vo.Message;
import com.cuixing.mes.message.vo.ProductMessage;

import java.util.List;

public interface JPushService {
    /**
     * 推送消息给指定用户
     * @param message
     * @return
     */
    public AjaxResult sendPushToRegistrationIds(ProductMessage message);

    public AjaxResult sendBroadcast(String messageTitle, String messageContent);

    boolean sendPush(Message message, List<String> registrationIds);
}
