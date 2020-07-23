package com.qxy.bbs.dao;

import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.domain.po.Publish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/22 14:52
 */
@Mapper
public interface PublishDao {

    int insert(Publish publish);

    void delete(Integer id);

    void update(Publish publish);

    List<Publish> selectList(int pageNum,int pageSize,int leixing);

    int getPublishNum(Integer leixing);

    Publish getPublishById(int id);

}
