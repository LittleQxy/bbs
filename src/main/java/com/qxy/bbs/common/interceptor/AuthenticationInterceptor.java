package com.qxy.bbs.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.qxy.bbs.common.exception.NotLoginException;
import com.qxy.bbs.common.jwt.LoginToken;
import com.qxy.bbs.common.jwt.PassToken;
import com.qxy.bbs.common.utils.TokenUtils;
import com.qxy.bbs.dao.UserDao;
import com.qxy.bbs.domain.po.User;
import com.qxy.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author qixiangyang5
 * @create 2020/7/25 16:53
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserDao userDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken userLoginToken = method.getAnnotation(LoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new NotLoginException("用户未登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new NotLoginException("用户未登录");
                }
                User user = userDao.selectById(Integer.parseInt(userId));
                if (user == null) {
                    throw new NotLoginException("用户不存在，请重新登录");
                }
                // 验证 token
                return TokenUtils.verify(token);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
