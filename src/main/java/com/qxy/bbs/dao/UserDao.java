package com.qxy.bbs.dao;

import com.qxy.bbs.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qixiangyang5
 * @create 2020/7/2 16:06
 */
@Mapper
public interface UserDao {

    void insert(User user);

    User select(String email);

    void delete(Integer id);

    User selectById(Integer id);

}
