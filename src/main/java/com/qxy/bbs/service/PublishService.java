package com.qxy.bbs.service;

import com.qxy.bbs.common.domain.Page;
import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.dao.PublishDao;
import com.qxy.bbs.domain.po.Publish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
            List<Publish> list = publishDao.selectList(beg,end,type);
            page.setContent(list);
            log.info("获取公告的列表结果为：{}",list == null?null:list);
            int total = publishDao.getPublishNum(type);
            log.info("获取全部公告的数量：total={}",total);
            page.setTotalElements(total);
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
            publishDao.delete(id);
            return WebReslut.success(true);
        }catch (Exception e){
            log.info("删除公告信息出错：{}",e.toString());
        }
        return WebReslut.failed(2000,"删除公告出错");
    }


    public WebReslut<Publish> edit(Publish publish){
        try{
            publish.setUpdateTime(new Date());
            publishDao.update(publish);
            log.info("更新公告内容为：{}",publish.toString());
            int id = publish.getId();
            Publish newPublish = publishDao.getPublishById(id);
            return WebReslut.success(newPublish);
        }catch (Exception e){
            log.info("更新公告失败：{}",e.toString());
        }
        return WebReslut.failed(2000,"更新公告失败");
    }
}
