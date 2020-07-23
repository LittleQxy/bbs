package com.qxy.bbs.domain.po;

import lombok.Data;


/**
 * @author qixiangyang5
 * @create 2020/7/2 14:18
 */
@Data
public class UserInfo {
    private Integer id;

    private Integer userId;

    private String phoneNumber;

    private Integer age;

    private String userName;
    //个人简介
    private String selfcontent;
    //发布的文章总数
    private Integer totalArticleNum;
    //帖子点赞总数
    private Integer getLikeNum;

    private String email;
    //头像地址
    private String avtarUrl;

}
