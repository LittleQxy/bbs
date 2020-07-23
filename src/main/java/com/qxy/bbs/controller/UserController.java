package com.qxy.bbs.controller;

import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.domain.po.User;
import com.qxy.bbs.domain.po.UserInfo;
import com.qxy.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qixiangyang5
 * @create 2020/7/2 17:08
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/login")
    public WebReslut<User> login(@RequestBody User user) {
        return service.login(user.getEmail(), user.getPassword());
    }

    @PostMapping("/regist")
    public WebReslut<User> regist(@RequestBody User user) {
        return service.regist(user);
    }

    @PostMapping("/delete")
    public WebReslut delete(@RequestBody UserInfo userInfo) {
        return service.delete(userInfo.getUserId());
    }
}
