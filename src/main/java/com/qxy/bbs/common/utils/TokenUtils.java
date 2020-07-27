package com.qxy.bbs.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qxy.bbs.domain.po.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 用于生产token
 * @author qixiangyang5
 * @create 2020/7/25 16:13
 */
@Slf4j
public class TokenUtils {

    public static final long EXPIRE_TIME = 10*60*60*1000;
    public static final String TOKEN_SECRET = "dady";//密钥盐

    /**
     * token生成
     * @param user
     * @return
     */
    public static String sign(User user){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", user.getEmail())
                    .withAudience(String.valueOf(user.getId()))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("验证通过");
            log.info("username={},id={}",jwt.getClaim("username"),jwt.getClaim("id"));
            log.info("过期时间：{}",jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            log.info("验证token失败，：{}",e.toString());
            return false;
        }
    }

}
