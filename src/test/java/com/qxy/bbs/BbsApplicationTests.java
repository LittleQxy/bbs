package com.qxy.bbs;

import com.alibaba.fastjson.JSONObject;
import com.qxy.bbs.common.domain.RedisKey;
import com.qxy.bbs.common.utils.DateUtils;
import com.qxy.bbs.domain.po.LostModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.ParseException;
import java.util.Date;

@SpringBootTest
class BbsApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws ParseException {
//        ValueOperations ops = redisTemplate.opsForValue();
//        LostModule lostModule = new LostModule();
//        lostModule.setContent("测试丢失模块");
//        lostModule.setTitle("校园卡低丢了");
//        lostModule.setUpdateTime(new Date());
//        ops.set("lose", JSONObject.toJSON(lostModule));
//        System.out.println(JSONObject.parseObject(ops.get("lose").toString(),LostModule.class));
          Date date = new Date();
        System.out.println(DateUtils.changeDate(date));

    }

}
