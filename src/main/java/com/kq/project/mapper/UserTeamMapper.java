package com.kq.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.model.vo.TeamUserVO;

import java.util.List;


/**
* @author kq
*/
public interface UserTeamMapper extends BaseMapper<UserTeam> {

    List<TeamUserVO> listMyJoinTeam(long id);
}




