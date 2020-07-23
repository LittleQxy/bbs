package com.qxy.bbs.common.domain;

import lombok.Data;

/**
 * 用于接口返回通用的数据
 *
 * @author qixiangyang5
 * @create 2020/7/2 10:27
 */
@Data
public class WebReslut<T> {

    private Integer code;

    private String msg;

    private T Data;

    private int total;

    public WebReslut() {
    }

    public WebReslut(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebReslut(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.Data = data;
    }

    /**
     * 成功返回
     *
     * @param reslut
     * @param <T>
     * @return
     */
    public static <T> WebReslut<T> success(T reslut) {
        return new WebReslut<T>(200, "success", reslut);
    }

    /**
     * 失败返回对应错误类型
     *
     * @param code
     * @param msg
     * @return
     */
    public static WebReslut failed(Integer code, String msg) {
        return new WebReslut(code, msg, null);
    }


}
