package com.qxy.bbs.service;

import com.alibaba.fastjson.JSONObject;
import com.qxy.bbs.common.domain.RedisKey;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.domain.dto.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/23 15:51
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取最新发布的文章
     * @param beg
     * @param end
     * @return
     */
    public WebReslut newPublishArticle(int beg, int end){
        List<ArticleDto> list = new ArrayList<>();
        try{
            ListOperations lop = redisTemplate.opsForList();
            lop.trim(RedisKey.NewArticleKey,0,50);
            List<String> info = lop.range(RedisKey.NewArticleKey,beg,end);
            for(String s:info){
                ArticleDto articleDto = JSONObject.parseObject(s,ArticleDto.class);
                list.add(articleDto);
            }
            WebReslut res = WebReslut.success(list);
            long total = lop.size(RedisKey.NewArticleKey);
            int cnt = (int)total;
            res.setTotal(cnt);
            return res;
        }catch (Exception e){
            log.info("获取最新发布文章列表失败：{}",e.toString());
        }
        return WebReslut.failed(2000,"获取Redis最新发布文章失败");
    }

    /**
     * 将最新创建的帖子存放到Redis list中
     * @param article
     */
    public  void RedisNewSave(ArticleDto article){
        ListOperations lop = redisTemplate.opsForList();
        String info = JSONObject.toJSONString(article);
        log.info("最新创建的帖子信息为：{}",info);
        try{
            lop.leftPush(RedisKey.NewArticleKey,info);
        }catch (Exception e){
            log.info("插入Redis list失败,插入信息；{},错误信息：{}",info,e.toString());
        }
    }
}

