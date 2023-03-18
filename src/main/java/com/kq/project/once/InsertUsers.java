package com.kq.project.once;

import com.kq.project.mapper.UserMapper;
import com.kq.project.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

    /**
     * 批量插入用户
     */
    //@Scheduled(initialDelay = 5000 ,fixedRate = Long.MAX_VALUE)
    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        System.out.println("11111111111111");
        stopWatch.start();
        final int INSERT_NUM = 100;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("");
            user.setUserAccount("");
            user.setAvatarUrl("https://tse2-mm.cn.bing.net/th/id/OIP-C.P7IxvMAx8MteSa1_VqmX3wHaKO?w=145&h=200&c=7&r=0&o=5&dpr=1.25&pid=1.7");
            user.setGender(0);
            user.setUserPassword("");
            user.setPhone("12334");
            user.setContactInfo("123@qq.com");
            user.setTags("[]");
            user.setUserStatus(0);
            user.setUserRole(0);
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}
