package com.qxy.bbs.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用于分页展示的部分，仅展示部分内容
 *
 * @author qixiangyang5
 * @create 2020/7/3 16:13
 */
@Data
public class ArticleDto {

    private Integer id;

    private Integer userId;

    private Integer type;

    private String userName;

    private String avtar;

    private String title;

    private String updateTime;

    private Integer getLikeNum;

    private Integer conmentNum;

    private String content;

    private Integer getWatchNum;

}
