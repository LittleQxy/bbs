package com.qxy.bbs.dao;

import com.qxy.bbs.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/27 16:01
 */
@Mapper
public interface CommentDao {

    void insert(Comment comment);

    void delete(Integer id);

    List<Comment> getComment(int articleId,int articleType,int beg,int end);
}
