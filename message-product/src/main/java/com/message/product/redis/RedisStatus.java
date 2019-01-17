package com.message.product.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Francis
 * @Description:
 * @TIME: Created on 2018/11/3
 * @Modified by:
 */
@Component
public class RedisStatus {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String today1 = format.format(new Date());

        return today1;
    }

    public boolean isExists(int id){

        return redisTemplate.hasKey(getToday()+"-"+id);
    }

    public void setIntoRedis(int id){
        ValueOperations opra = redisTemplate.opsForValue();
        //序列化 以免乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        opra.set(getToday()+"-"+id,"success");
    }
}
