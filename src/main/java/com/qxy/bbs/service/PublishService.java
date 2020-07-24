package com.qxy.bbs.service;

import com.alibaba.fastjson.JSONObject;
import com.qxy.bbs.common.domain.Page;
import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.RedisKey;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.dao.PublishDao;
import com.qxy.bbs.domain.po.Publish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/22 18:30
 */
@Service
@Slf4j
public class PublishService {

    @Autowired
    PublishDao publishDao;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取公告历史列表
     * @param pageDto
     * @param type
     * @return
     */
    public Page<Publish> list(PageDto pageDto,int type){
        Page<Publish> page = new Page<>();
        page.setNumber(pageDto.getPageNum());
        page.setSize(pageDto.getPageSize());
        try{
            int beg = (pageDto.getPageNum()-1)*pageDto.getPageSize();
            int end = beg + pageDto.getPageSize();
            log.info("获取公告的起始位置：beg=:{},end=:{}",beg,end);
//            List<Publish> list = publishDao.selectList(beg,end,type);
            ListOperations op = redisTemplate.opsForList();
            List<Publish> list = new ArrayList<>();
            if(type == 1){
                List<String> info = op.range(RedisKey.publishKey,beg,end);
                for(String s:info){
                    Publish publish = JSONObject.parseObject(s,Publish.class);
                    list.add(publish);
                }
            }else{
                List<String> info = op.range(RedisKey.Tongzhikey,beg,end);
                for(String s:info){
                    Publish publish = JSONObject.parseObject(s,Publish.class);
                    list.add(publish);
                }
            }
            page.setContent(list);
            log.info("获取公告的列表结果为：{}",list == null?null:list);
            long total = op.size(RedisKey.publishKey);
            int to = (int) total;
            log.info("获取全部公告的数量：total={}",total);
            page.setTotalElements(to);
            return  page;
        }catch (Exception e){
            log.info("获取公告数据出错:{}",e.toString());
        }
        page.setTotalElements(0);
        return page;
    }


    /**
     * 创建公告
     * @param publish
     * @return
     */
    public WebReslut<Publish> create(Publish publish){
        try{
            publish.setUpdateTime(new Date());
            publishDao.insert(publish);
            //将公告数据写入Redis
            insertIntoRedis(publish,publish.getLeixing());
            return WebReslut.success(publish);
        }catch (Exception e){
            log.info("插入新公告出错：{}",e.toString());
        }
        return WebReslut.failed(2000,"创建新公告出错");
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    public WebReslut<Boolean> delete(int id){
        try{
            Publish publish = publishDao.getPublishById(id);
            refreshRedis(publish.getLeixing());
            publishDao.delete(id);
            return WebReslut.success(true);
        }catch (Exception e){
            log.info("删除公告信息出错：{}",e.toString());
        }
        return WebReslut.failed(2000,"删除公告出错");
    }

    /**
     * 编辑公告
     * @param publish
     * @return
     */
    public WebReslut<Publish> edit(Publish publish){
        try{
            publish.setUpdateTime(new Date());
            publishDao.update(publish);
            log.info("更新公告内容为：{}",publish.toString());
            int id = publish.getId();
            Publish newPublish = publishDao.getPublishById(id);
            //更新Redis缓存
            refreshRedis(publish.getLeixing());
            return WebReslut.success(newPublish);
        }catch (Exception e){
            log.info("更新公告失败：{}",e.toString());
        }
        return WebReslut.failed(2000,"更新公告失败");
    }

    /**
     * 将公告写Redis的list中
     * @param publish
     */
    public void insertIntoRedis(Publish publish,int type){
        ListOperations lop = redisTemplate.opsForList();
        String info = JSONObject.toJSONString(publish);
        log.info("要插入Redis list中的公告信息为：{}",info);
        try{
            if(type == 1){
                lop.leftPush(RedisKey.publishKey,info);
            }else {
                lop.leftPush(RedisKey.Tongzhikey,info);
            }
        }catch (Exception e){
            log.info("插入Redis list 报错：{}",e.toString());
        }
    }

    /**
     * 当有公告被编辑的时候，缓存失效，更新Redis的缓存
     */
    public void refreshRedis(int type){
       try{
           redisTemplate.delete(RedisKey.publishKey);
           ListOperations lop = redisTemplate.opsForList();
           int total = publishDao.getPublishNum(type);
           List<Publish> list = publishDao.selectList(0,total,type);
           log.info("获取全部公告信息：{}");
           for(Publish p:list){
               String info = JSONObject.toJSONString(p);
               if(type == 1){
                   lop.rightPush(RedisKey.Tongzhikey,info);
               }else{
                   lop.rightPush(RedisKey.publishKey,info);
               }
           }
       }catch (Exception e){
           log.info("更新缓存出错：{}",e.toString());
       }
    }

}
