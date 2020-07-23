package com.qxy.bbs.dao;

import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.domain.po.LostModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 失物招领模块的数据操作层
 *
 * @author qixiangyang5
 * @create 2020/7/3 14:35
 */
@Mapper
public interface LoseDao {

    //根据id删除帖子
    void delete(Integer id);

    //插入帖子
    int insert(LostModule lostModule);

    //更新相关内容
    void update(LostModule lostModule);

    //根据id选择帖子
    LostModule select(Integer id);

    //分页选择帖子
    List<LostModule> selectByPage(PageDto pageDto);

    //获取总共帖子数量
    int getCount();


}
