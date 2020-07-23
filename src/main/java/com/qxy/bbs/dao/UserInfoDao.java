package com.qxy.bbs.dao;

import com.qxy.bbs.domain.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qixiangyang5
 * @create 2020/7/2 16:06
 */

@Mapper
public interface UserInfoDao {


    UserInfo select(Integer id);

    void insert(UserInfo userInfo);

    void update(UserInfo userInfo);

    void delete(Integer id);
}
