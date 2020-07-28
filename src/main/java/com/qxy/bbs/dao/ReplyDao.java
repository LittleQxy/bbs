package com.qxy.bbs.dao;

import com.qxy.bbs.domain.po.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/27 17:21
 */
@Mapper
public interface ReplyDao {

    void insert(Reply reply);

    List<Reply> getReply(int articleId,int articleType,int commentId);
}
