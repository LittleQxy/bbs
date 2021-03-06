package com.qxy.bbs.domain.po;

import lombok.Data;

import java.util.Date;

/**
 * @author qixiangyang5
 * @create 2020/7/2 14:54
 */
@Data
public class ChatModule {
    private Integer id;

    private Integer userId;

    private Integer articleType;

    private String title;
    //内容
    private String content;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //点赞数量
    private Integer getLikeNum;
    //浏览量
    private Integer watchNum;
}
