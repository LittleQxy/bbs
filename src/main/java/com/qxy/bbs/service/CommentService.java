package com.qxy.bbs.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.qxy.bbs.common.domain.CommentList;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.common.exception.NotLoginException;
import com.qxy.bbs.common.utils.DateUtils;
import com.qxy.bbs.dao.CommentDao;
import com.qxy.bbs.dao.UserInfoDao;
import com.qxy.bbs.domain.dto.CommentDto;
import com.qxy.bbs.domain.dto.ReplyDto;
import com.qxy.bbs.domain.po.Comment;
import com.qxy.bbs.domain.po.Reply;
import com.qxy.bbs.domain.po.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/27 16:00
 */
@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    ReplyService replyService;

    @Autowired
    UserInfoDao userInfo;
    /**
     * 插入评论到数据库
     * @param comment
     * @return
     */
    public WebReslut<Boolean> insert(Comment comment,String token){
        if(comment != null){
            comment.setCreateTime(new Date());
            comment.setGetLikeNum(0);
            log.info("插入的评论内容为：{}",comment.toString());
            try {
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new NotLoginException("用户未登录");
                }
                log.info("用户id是：{}",userId);
                UserInfo info = userInfo.select(Integer.parseInt(userId));
                log.info("获取的用户信息为：{}",info.toString());
                comment.setUserId(info.getUserId());
                comment.setAvtarUrl(info.getAvtarUrl());
                comment.setUserName(info.getUserName());
                commentDao.insert(comment);
                return WebReslut.success(true);
            }catch (Exception e){
                log.info("插入评论内容出错：{}",e.toString());
            }
        }
        return WebReslut.failed(20000,"插入评论出错");
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    public WebReslut<Boolean> delete(Integer id){
        if(id != null){
            try{
                commentDao.delete(id);
                return WebReslut.success(true);
            }catch (Exception e){
                log.info("删除评论出错");
            }
        }
        return WebReslut.failed(20000,"删除评论出错");
    }

    /**
     * 获取评论列表
     * @param articleId
     * @param articleType
     * @return
     */
    public  WebReslut getComment(Integer articleId,Integer articleType,int pageNum,int pageSize){
        if(articleId != null && articleType != null){
            try{
                int beg = (pageNum-1)*pageSize;
                int end = beg + pageSize;
                List<Comment> list = commentDao.getComment(articleId,articleType,beg,end);
                List<CommentList> res = new ArrayList<>();
                for(Comment com:list){
                    CommentList list1 = new CommentList();
                    list1.setComment(getCommentDto(com));
                    List<Reply> tmp = replyService.getReply(com.getArticleId(),com.getArticleType(),com.getId());
                    List<ReplyDto> replyDtos = new ArrayList<>();
                    for (Reply reply:tmp){
                        replyDtos.add(getReplyDto(reply));
                    }
                    list1.setList(replyDtos);
                    list1.setTotal(replyDtos.size());
                    res.add(list1);
                }

                WebReslut reslut = WebReslut.success(res);
                reslut.setTotal(res.size());
                return reslut;
            }catch (Exception e){
                log.info("获取评论列表出错：{}",e.toString());
            }
        }
        return WebReslut.failed(20000,"获取评论列表失败");
    }

    public CommentDto getCommentDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setArticleId(comment.getArticleId());
        dto.setArticleType(comment.getArticleType());
        dto.setAvtarUrl(comment.getAvtarUrl());
        dto.setContent(comment.getContent());
        dto.setGetLikeNum(comment.getGetLikeNum());
        dto.setId(comment.getId());
        dto.setUserId(comment.getUserId());
        dto.setUserName(comment.getUserName());
        dto.setCreateTime(DateUtils.changeDate(comment.getCreateTime()));
        return dto;
    }

    public ReplyDto getReplyDto(Reply reply){
        ReplyDto dto = new ReplyDto();
        dto.setArticleId(reply.getArticleId());
        dto.setArticleType(reply.getArticleType());
        dto.setAvaterUrl(reply.getAvaterUrl());
        dto.setCommentId(reply.getCommentId());
        dto.setContent(reply.getContent());
        dto.setGetLikeNum(reply.getGetLikeNum());
        dto.setId(reply.getId());
        dto.setReplyUserId(reply.getReplyUserId());
        dto.setReplyUserName(reply.getReplyUserName());
        dto.setUserId(reply.getUserId());
        dto.setUserName(reply.getUserName());
        dto.setCreateTime(DateUtils.changeDate(reply.getCreateTime()));
        return dto;
    }

}
