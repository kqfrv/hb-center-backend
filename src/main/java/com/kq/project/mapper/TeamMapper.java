package com.kq.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kq.project.model.entity.Team;
import com.kq.project.model.vo.TeamUserVO;

import java.util.List;

/**
* @author kq
* @description 针对表【team(队伍)】的数据库操作Mapper
* @createDate 2022-08-24 23:42:22
* @Entity generator.domain.Team
*/
public interface TeamMapper extends BaseMapper<Team> {

    List<TeamUserVO> listMyCreateTeam(long userId);
}




