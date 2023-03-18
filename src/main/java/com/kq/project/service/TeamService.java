package com.kq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kq.project.model.dto.TeamQuery;
import com.kq.project.model.entity.Team;
import com.kq.project.model.entity.User;
import com.kq.project.model.request.TeamJoinRequest;
import com.kq.project.model.request.TeamQuitRequest;
import com.kq.project.model.request.TeamUpdateRequest;
import com.kq.project.model.vo.TeamUserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* @author kq
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 修改队伍
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    /**
     * 退出队伍
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除队伍
     */
    @Transactional(rollbackFor = Exception.class)
    boolean deleteTeam(long id,User loginUser);

    List<TeamUserVO> listMyCreateTeam(long userId);

    TeamUserVO getTeamById(long id, boolean b, User loginUser);

    /**
     * 查看用户加入的队伍
     */
    List<Team> getTeamByUserId(Long userId);
}

