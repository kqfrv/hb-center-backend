package com.kq.project.job;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kq.project.model.entity.User;
import com.kq.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热任务
 * @author kq
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    List<Integer> mainUserList = Arrays.asList(1);
    //每天执行，预热推荐用户
    @Scheduled(cron = "0 30 0 * * *")
    public void  doCacheRecommendUser(){
        RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
        try {
            //加锁
            if (lock.tryLock(0,30000L,TimeUnit.MILLISECONDS)){
                System.out.println("getLock"+Thread.currentThread().getId());
                for (Integer userId : mainUserList) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User>  userPage = userService.page(new Page<>(1,20),queryWrapper);
                    String redisKey = String.format("yupao:user:recommend:%s",userId);
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    try {
                        valueOperations.set(redisKey,userPage,1000000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error",e);
                    }
                }
            }
        } catch (InterruptedException e) {
           log.error("doCacheRecommendUser error",e);
        }finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
                System.out.println("unlock"+Thread.currentThread().getId());
            }
        }

    }


}
