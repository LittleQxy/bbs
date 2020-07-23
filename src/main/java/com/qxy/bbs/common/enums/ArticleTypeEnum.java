package com.qxy.bbs.common.enums;

/**
 * @author qixiangyang5
 * @create 2020/7/2 15:55
 */
public enum ArticleTypeEnum {
    SALE_MODULE(1, "二手市场"),
    LOSE_MODULE(2, "失物招领"),
    CHAT_MODULE(3, "闲聊"),
    LOVE_MODULE(4, "表白墙");

    private int code;
    private String des;


    ArticleTypeEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
