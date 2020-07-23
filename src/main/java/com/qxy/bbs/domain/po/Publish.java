package com.qxy.bbs.domain.po;

import lombok.Data;

import java.util.Date;

/**
 * @author qixiangyang5
 * @create 2020/7/22 14:50
 */
@Data
public class Publish {

    Integer id;

    String title;

    String content;

    Integer leixing;

    Date createTime;

    Date updateTime;
}
