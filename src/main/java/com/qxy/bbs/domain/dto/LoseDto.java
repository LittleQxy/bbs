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
public class LoseDto {

    private Integer id;

    private Integer userId;

    private String userName;

    private String avtar;

    private String title;

    private Date updateTime;

    private Integer getLikeNum;

}
