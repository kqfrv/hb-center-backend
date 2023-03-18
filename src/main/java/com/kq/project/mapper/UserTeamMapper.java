package com.kq.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.model.vo.TeamUserVO;

import java.util.List;


/**
* @author kq
* @description 针对表【user_team(用户队伍关系)】的数据库操作Mapper
* @createDate 2022-08-24 23:44:12
* @Entity generator.domain.UserTeam
*/
public interface UserTeamMapper extends BaseMapper<UserTeam> {

    List<TeamUserVO> listMyJoinTeam(long id);
}




