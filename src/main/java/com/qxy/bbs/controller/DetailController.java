package com.qxy.bbs.controller;

import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.common.jwt.LoginToken;
import com.qxy.bbs.domain.dto.ArticleDto;
import com.qxy.bbs.service.LoseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @LoginToken
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

}
