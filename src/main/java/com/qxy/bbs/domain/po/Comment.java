package com.qxy.bbs.domain.po;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

/**
 * 一级评论
 *
 * @author qixiangyang5
 * @create 2020/7/2 14:25
 */

@Data
public class Comment {

    private Integer id;

    private Integer articleType;
    //评论文章的Id
    private Integer acticleId;

    //评论的用户
    private Integer userId;
    //创建时间
    private Date createTime;
    //评论内容
    private String content;
    //点赞数量
    private Integer getLikeNum;
}
