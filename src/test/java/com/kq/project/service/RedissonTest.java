package com.kq.project.service;


import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedissonTest {


    @Resource
    private RedissonClient redissonClient;


    @Test
    void test() {
        //
        List<String> list = new ArrayList<>();
        list.add("kq");
        list.get(0);
        System.out.println("list: " + list.get(0));

        RList<String> rList = redissonClient.getList("test-list");
        //rList.add("kq");
        System.out.println("rList: " + rList.get(0));
        rList.remove(0);
        System.out.println("rList: " + rList.get(0));
    }
}
