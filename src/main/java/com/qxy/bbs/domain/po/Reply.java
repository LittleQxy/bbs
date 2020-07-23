package com.qxy.bbs.domain.po;

import lombok.Data;

import java.util.Date;

/**
 * 评论回复表
 *
 * @author qixiangyang5
 * @create 2020/7/2 14:28
 */
@Data
public class Reply {

    private Integer id;

    private Integer articleId;
    private Integer articleType;
    private Integer userId;
    //回复评论的Id
    private Integer commentId;
    //回复的Id
    private Integer replyId;
    //回复的内容
    private String content;
    //回复时间
    private Date createTime;
    //点赞数量
    private Integer getLikeNum;
}
