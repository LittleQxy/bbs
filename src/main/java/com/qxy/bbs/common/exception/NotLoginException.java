package com.qxy.bbs.common.exception;

/**
 * @author qixiangyang5
 * @create 2020/7/25 17:57
 */
public class NotLoginException extends RuntimeException {

    private static final long serialVersionUID = 7188448796350293118L;

    private static final int DEFAULT_EXCEPTION_CODE = 500;

    /**
     * 异常码
     */
    private final int code;

    private final  String msg;

    /**
     * 构造方法 抛出系统异常码
     * <p>
     * 不指定异常码使用默认的异常码：500
     *
     * @param msg 抛出异常信息
     */
    public NotLoginException(String msg) {
        this(DEFAULT_EXCEPTION_CODE, msg);
    }

    /**
     * 构造方法 指定异常码和异常信息
     *
     * @param code    异常码
     * @param msg 异常信息
     */
    public NotLoginException(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
