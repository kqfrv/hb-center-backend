package com.kq.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kq.project.mapper.UserTeamMapper;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.model.vo.TeamUserVO;
import com.kq.project.service.UserTeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author kq
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2022-08-24 23:44:12
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {


    @Resource
    private UserTeamMapper userTeamMapper;
    @Override
    public List<TeamUserVO> listMyJoinTeam(long id) {
        List<TeamUserVO> listMyJoinTeam = userTeamMapper.listMyJoinTeam(id);
        return listMyJoinTeam;
    }
}




