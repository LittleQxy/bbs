package com.qxy.bbs.controller;

import com.qxy.bbs.common.domain.Page;
import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.domain.dto.UserDto;
import com.qxy.bbs.domain.dto.UserInfoDto;
import com.qxy.bbs.domain.po.Publish;
import com.qxy.bbs.domain.po.UserInfo;
import com.qxy.bbs.service.PublishService;
import com.qxy.bbs.service.RedisService;
import com.qxy.bbs.service.UserInfoService;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qixiangyang5
 * @create 2020/7/2 18:14
 */
@Slf4j
@RestController
@RequestMapping
public class HomeController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    PublishService publishService;
    @Autowired
    RedisService redisService;


    //请求个人主页展示的路径
    @PostMapping("/selfpage")
    public WebReslut<UserInfo> selfPage(@RequestBody UserDto userDto) {
        log.info("UserDto:{}", userDto.toString());
        return userInfoService.selfPage(userDto);
    }

    //请求个人主页编辑的路径
    @PostMapping("/selfedit")
    public WebReslut<UserInfo> selfEdit(@RequestBody UserInfoDto userInfoDto) {
        log.info("UserInfoDto:{}", userInfoDto.toString());
        return userInfoService.selfEdit(userInfoDto);
    }

    /**
     * 获取公告的列表
     * @param pageDto
     * @param type
     * @return
     */
    @PostMapping("/getPublishList/{type}")
    public WebReslut getPublishList(@RequestBody PageDto pageDto, @PathVariable int type ){
        log.info("请求公告列表参数：pagesize:{},pageNum:{},type:{}",pageDto.getPageSize(),pageDto.getPageNum(),type);
        try{
            Page<Publish> page=publishService.list(pageDto,type);
            return WebReslut.success(page);
        }catch (Exception e){
            log.info("获取列表失败：{}",e.toString());
        }
        return WebReslut.failed(2000,"获取列表失败");
    }

    @PostMapping("/createPublish")
    public WebReslut<Publish> createPublish(@RequestBody Publish publish){
        log.info("即将创建公告信息：{}",publish);
        return publishService.create(publish);
    }

    @PostMapping("/deletePublish/{id}")
    public WebReslut<Boolean> deletePublish(@RequestParam("id") int id){
        return  publishService.delete(id);
    }

    @PostMapping("/getNewPublish")
    public WebReslut getNewPublish(@RequestBody PageDto pageDto){
        int beg = (pageDto.getPageNum()-1)*pageDto.getPageSize();
        int end = beg+pageDto.getPageSize();
        log.info("获取最新发布的分页请求，beg={},end={}",beg,end);
        try {
            return redisService.newPublishArticle(beg,end);
        }catch (Exception e){
            log.info("获取分页请求失败：{}",e.toString());
        }
        return WebReslut.failed(2000,"获取分页请求失败");
    }

}
