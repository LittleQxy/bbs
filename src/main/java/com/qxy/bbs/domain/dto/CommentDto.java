package com.qxy.bbs.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author qixiangyang5
 * @create 2020/7/27 20:06
 */
@Data
public class CommentDto {

    private Integer id;

    private Integer articleType;
    //评论文章的Id
    private Integer articleId;

    //评论的用户
    private Integer userId;
    //创建时间
    private String createTime;
    //评论内容
    private String content;
    //点赞数量
    private Integer getLikeNum;

    private String userName;

    private String avtarUrl;
}
