package com.kq.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test(){

        redisTemplate.opsForValue().set("kq",123456);
        System.out.println(redisTemplate.opsForValue().get("kq"));
    }
}
