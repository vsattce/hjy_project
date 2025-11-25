package com.hjy.common.exception;

public class ServiceException extends RuntimeException {

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
    public ServiceException()
    {
    }


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }
}
