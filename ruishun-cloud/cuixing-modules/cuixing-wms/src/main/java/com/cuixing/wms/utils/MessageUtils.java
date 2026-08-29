package com.cuixing.wms.utils;

import com.cuixing.common.core.utils.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
/* loaded from: MessageUtils.class */
public class MessageUtils {
    public static String message(String code, Object... args) {
        MessageSource messageSource = (MessageSource) SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
