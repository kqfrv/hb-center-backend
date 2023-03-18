package com.kq.project.service;

import cn.hutool.core.convert.Convert;
import com.kq.project.mapper.UserMapper;
import com.kq.project.mapper.UserTeamMapper;
import com.kq.project.model.entity.User;
import com.kq.project.model.vo.TeamUserVO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;


    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Resource
    private UserTeamMapper userTeamMapper;
    @Test
    void testGetUser() {
        List<TeamUserVO> listMyJoinTeam = userTeamMapper.listMyJoinTeam(2);
        System.out.println(listMyJoinTeam);
    }



}
