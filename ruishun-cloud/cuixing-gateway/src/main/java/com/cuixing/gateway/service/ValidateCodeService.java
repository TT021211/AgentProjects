package com.cuixing.gateway.service;

import java.io.IOException;
import com.cuixing.common.core.exception.CaptchaException;
import com.cuixing.common.core.web.domain.AjaxResult;

/**
 * 验证码处理
 *
 * @author cuixing
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    public AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String key, String value) throws CaptchaException;
}
