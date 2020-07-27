package com.qxy.bbs.service;

import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.common.utils.MD5Utils;
import com.qxy.bbs.common.utils.TokenUtils;
import com.qxy.bbs.dao.UserDao;
import com.qxy.bbs.dao.UserInfoDao;
import com.qxy.bbs.domain.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qixiangyang5
 * @create 2020/7/2 16:25
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserInfoDao userInfoDao;

    public WebReslut regist(User user) {
        User newuser = userDao.select(user.getEmail());
        if (newuser != null) {//说明用户已经存在
            return WebReslut.failed(2003, "用户已存在");
        }
        try {
            String password = user.getPassword();
            user.setPassword(MD5Utils.md5(password));
            userDao.insert(user);
            String token = TokenUtils.sign(user);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            return WebReslut.success(map);
        } catch (Exception e) {
            log.error("创建用户出错：{}", e.toString());
        }
        return WebReslut.failed(2003, "创建用户出错");
    }

    public WebReslut login(String email, String password) {
        try {
            User user = userDao.select(email);
            if (user != null) {
                //生成一个token
                String pd = MD5Utils.md5(password);
                if (pd.equals(user.getPassword())) {
                    String token = TokenUtils.sign(user);
                    Map<String,String> map = new HashMap<>();
                    map.put("token",token);
                    return WebReslut.success(map);
                }
                return WebReslut.failed(20002, "密码错误");
            }
            return WebReslut.failed(20001, "用户未注册");
        } catch (Exception e) {
            log.error("登陆失败：{}", e.toString());
        }
        return WebReslut.failed(20000, "登陆失败");
    }


    public WebReslut delete(Integer id) {
        log.info("要删除的用户id为{}", id);
        if (id != null) {
            try {
                userDao.delete(id);
                userInfoDao.delete(id);
                return WebReslut.success("删除用户成功");
            } catch (Exception e) {
                log.info("删除用户出错：{}", e.toString());
            }
            return WebReslut.failed(21001, "删除用户信息出错");
        }
        return WebReslut.failed(21000, "用户id为空");
    }
}
