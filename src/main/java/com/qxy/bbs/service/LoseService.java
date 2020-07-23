package com.qxy.bbs.service;

import com.alibaba.fastjson.JSONObject;
import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.RedisKey;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.dao.LoseDao;
import com.qxy.bbs.dao.UserInfoDao;
import com.qxy.bbs.domain.dto.LoseDto;
import com.qxy.bbs.domain.po.LostModule;
import com.qxy.bbs.domain.po.UserInfo;
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
 * @create 2020/7/3 15:50
 */
@Service
@Slf4j
public class LoseService {

    @Autowired
    LoseDao loseDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取当前模块的一页数据，默认按照更新时间和点赞数量排序
     *
     * @param pageDto
     * @return
     */
    public WebReslut<List<LoseDto>> getByPage(PageDto pageDto) {
        log.info("请求分页参数 pageDto={}", pageDto);
        try {
            int beg = (pageDto.getPageNum() - 1) * pageDto.getPageSize();
            int end = pageDto.getPageNum() * pageDto.getPageSize();
            pageDto.setPageNum(beg);
            pageDto.setPageSize(end);
            List<LostModule> lostModules = loseDao.selectByPage(pageDto);
            if (lostModules != null && lostModules.size() > 0) {
                List<LoseDto> loseDtos = new ArrayList<>();
                for (LostModule module : lostModules) {
                    LoseDto loseDto = new LoseDto();
                    loseDto.setId(module.getId());
                    loseDto.setTitle(module.getTitle());
                    loseDto.setGetLikeNum(module.getGetLikeNum());
                    loseDto.setUpdateTime(module.getUpdateTime());
                    loseDto.setUserId(module.getUserId());
                    loseDto.setUserName(module.getUserName());
                    loseDto.setAvtar(module.getAvaterUrl());
                    loseDtos.add(loseDto);
                }
                int total = loseDao.getCount();
                log.info("获取帖子总数量为：{}",total+"");
                WebReslut res = WebReslut.success(loseDtos);
                res.setTotal(total);
                return res;
            }
        } catch (Exception e) {
            log.error("选择分页失败：{}", e.toString());
        }
        return WebReslut.failed(2004, "获取数据库数据失败");
    }

    /**
     * 创建帖子
     *
     * @param lostModule
     * @return
     */
    public WebReslut create(LostModule lostModule) {
        log.info("新创建的帖子内容：{}", lostModule.toString());
        try {
            lostModule.setCreateTime(new Date());
            lostModule.setUpdateTime(new Date());
            lostModule.setGetLikeNum(0);
            lostModule.setWatchNum(0);
            lostModule.setArticleType(1);
            UserInfo info = userInfoDao.select(lostModule.getUserId());
            log.info("查询用户名：{}", info.toString());
            //将用户的发布文章数加一
            info.setTotalArticleNum(info.getTotalArticleNum() + 1);
            userInfoDao.update(info);
            //设置用户名
            lostModule.setUserName(info.getUserName());
            lostModule.setAvaterUrl(info.getAvtarUrl());
            loseDao.insert(lostModule);
            //插入Redis中
            RedisNewSave(lostModule);
            return WebReslut.success(lostModule);
        } catch (Exception e) {
            log.error("创建帖子出错：{}", e.toString());
        }
        return WebReslut.failed(2005, "创建帖子出错");
    }


    /**
     * 帖子详情页面
     *
     * @param loseDto
     * @return
     */
    public WebReslut<LostModule> detail(LoseDto loseDto) {
        log.info("贴子编辑:{}", loseDto.toString());
        try {
            LostModule lostModule = loseDao.select(loseDto.getId());
            return WebReslut.success(lostModule);
        } catch (Exception e) {
            log.error("选择帖子出错：{}", e.toString());
        }
        return WebReslut.failed(2006, "选择帖子出错");
    }

    /**
     * 帖子编辑
     *
     * @param lostModule
     * @return
     */
    public WebReslut edit(LostModule lostModule) {
        log.info("帖子编辑后的内容为：{}", lostModule.toString());
        try {
            lostModule.setUpdateTime(new Date());
            loseDao.update(lostModule);
            return WebReslut.success(lostModule);
        } catch (Exception e) {
            log.error("更新出错：{}", e.toString());
        }
        return WebReslut.failed(2007, "更新帖子出错");
    }

    /**
     * 帖子删除
     *
     * @param id
     * @return
     */
    public WebReslut delete(Integer id) {
        log.info("要删除的帖子Id：{}", id);
        if (id != null) {
            try {
                loseDao.delete(id);
                return WebReslut.success(null);
            } catch (Exception e) {
                log.error("删除帖子出错：{}", e.toString());
            }
            return WebReslut.failed(2008, "删除帖子出错");
        }
        return WebReslut.failed(2009, "空指针异常");
    }

    /**
     * 将最新创建的帖子存放到Redis list中
     * @param lostModule
     */
    public void RedisNewSave(LostModule lostModule){
        ListOperations lop = redisTemplate.opsForList();
        String info = JSONObject.toJSONString(lostModule);
        log.info("最新创建的帖子信息为：{}",info);
        try{
            lop.leftPush(RedisKey.HotArticleKey,info);
        }catch (Exception e){
            log.info("插入Redis list失败,插入信息；{},错误信息：{}",info,e.toString());
        }
    }

}
