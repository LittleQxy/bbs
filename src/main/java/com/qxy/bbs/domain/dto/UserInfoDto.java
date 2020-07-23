package com.qxy.bbs.domain.dto;

import lombok.Data;

/**
 * 用于修改用户个人主页的数据
 *
 * @author qixiangyang5
 * @create 2020/7/2 18:25
 */
@Data
public class UserInfoDto {
    private Integer id;

    private String phoneNumber;

    private Integer age;

    private String userName;
    //个人简介
    private String selfcontent;

    //头像地址
    private String avtarUrl;

}
