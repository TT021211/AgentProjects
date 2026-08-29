package com.cuixing.auth.exception;

/**
 * @author zhan
 * @create 2024/3/5 11:32
 */
public class WechatLoginException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     *
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public WechatLoginException()
    {
    }

    public WechatLoginException(String message)
    {
        this.message = message;
    }

    public WechatLoginException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public WechatLoginException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public WechatLoginException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}
