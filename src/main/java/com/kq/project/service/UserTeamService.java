package com.kq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.model.vo.TeamUserVO;

import java.util.List;


/**
* @author kq
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service
* @createDate 2022-08-24 23:44:12
*/
public interface UserTeamService extends IService<UserTeam> {

    List<TeamUserVO> listMyJoinTeam(long id);

}
