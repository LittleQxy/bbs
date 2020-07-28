package com.qxy.bbs.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author qixiangyang5
 * @create 2020/7/27 20:07
 */
@Data
public class ReplyDto {


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
    private String createTime;
    //点赞数量
    private Integer getLikeNum;
    private Integer ReplyUserId;
    private String userName;

    private String avaterUrl;

    private String replyUserName;

}
