package com.salmon.happyrmmall.mall.service;

/**
 * @date 2022/4/8 - 1:24
 * Created by Salmon
 */
public interface IRedisService {

    void set(String key, String value);

    String get(String key);

    boolean expire(String key, long expire);

    void remove(String key);


}
