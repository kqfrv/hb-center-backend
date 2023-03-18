package com.kq.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.service.UserService;
import com.kq.project.service.UserTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TeamServiceImplTest {

    @Autowired
    private UserTeamService userTeamService;
    @Test
    void test1(){

        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", 2);
        queryWrapper.eq("teamId", 12);
        long count = userTeamService.count(queryWrapper);
    }
}
