package com.qxy.bbs.controller;

import com.qxy.bbs.common.domain.PageDto;
import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.domain.dto.ArticleDto;
import com.qxy.bbs.domain.po.LostModule;
import com.qxy.bbs.service.LoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/3 16:33
 */
@RestController
@RequestMapping("/lose")
public class LoseController {

    @Autowired
    LoseService loseService;

    @RequestMapping("/list")
    public WebReslut<List<ArticleDto>> getByPage(@RequestBody PageDto pageDto) {
        return loseService.getByPage(pageDto);
    }

    @RequestMapping("/create")
    public WebReslut<LostModule> create(@RequestBody LostModule lostModule) {
        return loseService.create(lostModule);
    }

    @RequestMapping("/detail")
    public WebReslut<LostModule> detail(@RequestBody ArticleDto articleDto) {
        return loseService.detail(articleDto);
    }

    @RequestMapping("/edit")
    public WebReslut<LostModule> edit(@RequestBody LostModule lostModule) {
        return loseService.edit(lostModule);
    }

    @PostMapping("/delete")
    public WebReslut delete(@RequestBody ArticleDto articleDto) {
        return loseService.delete(articleDto.getId());
    }


}
