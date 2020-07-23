package com.qxy.bbs.service;

import com.qxy.bbs.common.domain.WebReslut;
import com.qxy.bbs.dao.UserInfoDao;
import com.qxy.bbs.domain.dto.UserDto;
import com.qxy.bbs.domain.dto.UserInfoDto;
import com.qxy.bbs.domain.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qixiangyang5
 * @create 2020/7/2 18:05
 */
@Service
public class UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 个人主页的展示功能
     *
     * @param userDto
     * @return
     */
    public WebReslut<UserInfo> selfPage(UserDto userDto) {
        Integer id = userDto.getId();//获取用户的Id
        if (id != null) {
            UserInfo userInfo = userInfoDao.select(id);
            if (userInfo != null) {
                return WebReslut.success(userInfo);
            }
            //如果是新用户
            userInfo = new UserInfo();
            userInfo.setEmail(userDto.getEmail());
            userInfo.setUserId(userDto.getId());
            userInfo.setTotalArticleNum(0);
            userInfo.setGetLikeNum(0);
            userInfoDao.insert(userInfo);

            UserInfo newUserInfo = userInfoDao.select(id);
            return WebReslut.success(newUserInfo);
        }
        return WebReslut.failed(2001, "用户不存在");
    }

    /**
     * 个人主页的编辑
     *
     * @param userInfoDto
     * @return
     */
    public WebReslut<UserInfo> selfEdit(UserInfoDto userInfoDto) {
        Integer id = userInfoDto.getId();
        if (id != null) {
            UserInfo userInfo = userInfoDao.select(id);
            if (userInfo != null) {
                userInfo.setAge(userInfoDto.getAge());
                userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
                userInfo.setAvtarUrl(userInfoDto.getAvtarUrl());
                userInfo.setSelfcontent(userInfoDto.getSelfcontent());
                userInfo.setUserName(userInfoDto.getUserName());

                userInfoDao.update(userInfo);
                return WebReslut.success(userInfo);
            }
            return WebReslut.failed(2001, "用户不存在");
        }
        return WebReslut.failed(2001, "用户不存在");
    }


}
