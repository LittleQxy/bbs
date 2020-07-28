package com.qxy.bbs.controller;

import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.common.jwt.LoginToken;
import com.qxy.bbs.domain.dto.ArticleDto;
import com.qxy.bbs.domain.po.Comment;
import com.qxy.bbs.domain.po.Reply;
import com.qxy.bbs.service.CommentService;
import com.qxy.bbs.service.LoseService;
import com.qxy.bbs.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于请求贴子详情的controller
 * @author qixiangyang5
 * @create 2020/7/24 18:12
 */
@RestController
@Slf4j
public class DetailController {

    @Autowired
    LoseService loseService;

    @Autowired
    CommentService commentService;

    @Autowired
    ReplyService replyService;

    @RequestMapping("/detail/{id}/{type}")
    public WebReslut getDetail(@PathVariable("id") Integer id,@PathVariable("type") Integer type){
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(id);
        articleDto.setType(type);
        try{
            switch (type){
                case 1:{//类型1对应lose模块
                    return loseService.detail(articleDto);
                }
            }
        }catch (Exception e){
            log.info("获取帖子详情出错：{}",e.toString());
        }
        return WebReslut.failed(20000,"获取详情出错");
    }


    /**
     * 评论文章
     * @param comment
     * @return
     */
    @PostMapping("/comment")
    public WebReslut Comment(@RequestBody Comment comment, HttpServletRequest request){
        try{
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            return commentService.insert(comment,token);
        }catch (Exception e){
            log.info("插入评论出错：{}",e.toString());
        }
        return WebReslut.failed(20000,"评论出错");
    }

    /**
     * 获取文章的评论和回复列表
     * @param articleType
     * @param articleId
     * @return
     */
    @PostMapping("/replyList/{articleType}/{articleId}")
    public WebReslut ReplyList(@PathVariable("articleType") Integer articleType, @PathVariable("articleId")Integer articleId, @RequestBody PageDto pageDto){
        try{
            return commentService.getComment(articleId,articleType,pageDto.getPageNum(),pageDto.getPageSize());
        }catch (Exception e){
            log.info("获取评论列表出错：{}",e.toString());
        }
        return WebReslut.failed(20000,"获取评论列表出错");
    }

    /**
     * 进行回复，回复评论
     * @param reply
     * @return
     */
    @PostMapping("/ReplyComment")
    public WebReslut Reply(@RequestBody Reply reply,HttpServletRequest request){
        try{
            String token = request.getHeader("Authorization");
            return replyService.insert(reply,token);
        }catch (Exception e){
            log.info("回复出错：{}",e.toString());
        }
        return WebReslut.failed(20000,"回复评论出错");
    }
}
