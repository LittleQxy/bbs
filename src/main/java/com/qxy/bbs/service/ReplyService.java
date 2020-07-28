package com.qxy.bbs.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.common.exception.NotLoginException;
import com.qxy.bbs.dao.ReplyDao;
import com.qxy.bbs.dao.UserInfoDao;
import com.qxy.bbs.domain.po.Reply;
import com.qxy.bbs.domain.po.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/27 17:32
 */
@Service
@Slf4j
public class ReplyService {

    @Autowired
    ReplyDao replyDao;

    @Autowired
    UserInfoDao infoDao;
    /**
     * 将回复内容插入数据库中
     * @param reply
     * @return
     */
    public WebReslut insert(Reply reply,String token){
        if(reply != null){
            reply.setCreateTime(new Date());
            reply.setGetLikeNum(0);
            log.info("回复的内容为：{}",reply.toString());
            try{
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new NotLoginException("用户未登录");
                }
                log.info("用户id是：{}",userId);
                UserInfo info = infoDao.select(Integer.parseInt(userId));
                log.info("获取的用户信息为：{}",info.toString());
                reply.setUserId(info.getUserId());
                reply.setUserName(info.getUserName());
                reply.setAvaterUrl(info.getAvtarUrl());
                replyDao.insert(reply);
                return WebReslut.success(true);
            }catch (Exception e){
                log.info("插入回复内容出错：{}",e.toString());
            }
        }
        return WebReslut.failed(20000,"评论出错");
    }

    /**
     * 获取评论的回复消息列表
     * @param articleId
     * @param articleType
     * @param commentId
     * @return
     */
    public List<Reply> getReply(int articleId,int articleType,int commentId){
        try{
            return replyDao.getReply(articleId, articleType, commentId);
        }catch (Exception e){
            log.info("获取回复列表出错：{}",e.toString());
        }
        return null;
    }
}
