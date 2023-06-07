package com.salmon.happyrmmall.mall.service.impl;

import com.salmon.happyrmmall.mall.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @date 2022/4/8 - 1:25
 * Created by Salmon
 */
@Service("iRedisService")
public class RedisServiceImpl implements IRedisService {

    /**
     * 因为本项目中使用spring session统一管理session，所以不需要进行object（如user对象）的序列化与反序列化
     * 至于为什么不使用redisTemplate而使用stringRedisTemplate
     * 是因为对给定的对象执行自动序列化/反序列化时，如果使用redisTemplate，默认情况下，它使用Java序列化
     * (即通过JdkSerializationRedisSerializer),此时如果value为object会出现乱码的情况，即序列化不成功。
     * 该项目使用redis代替本地guava缓存，只进行字符串操作。
     * 根据官方文档：
     * 对于字符串密集型操作，要考虑专用的StringRedisTemplate。
     */


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 终止时间，单位为秒
     * @param key
     * @param expire
     * @return
     */
    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }
}
