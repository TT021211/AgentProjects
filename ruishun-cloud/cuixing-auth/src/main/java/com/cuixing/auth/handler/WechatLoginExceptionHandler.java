package com.cuixing.auth.handler;

import com.cuixing.auth.config.WechatLoginConfig;
import com.cuixing.auth.exception.WechatLoginException;
import com.cuixing.common.security.handler.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhan
 * @create 2024/3/5 14:10
 */
@ControllerAdvice
public class WechatLoginExceptionHandler {
    @Autowired
    private WechatLoginConfig wechatLoginConfig;
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 企业微信扫码登录发生异常
     */
    @ExceptionHandler(WechatLoginException.class)
    public String handleServiceException(WechatLoginException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
       // return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
        return "redirect:"+ wechatLoginConfig.getLoginUrl() +"/?error_code="+e.getCode();
    }

}
