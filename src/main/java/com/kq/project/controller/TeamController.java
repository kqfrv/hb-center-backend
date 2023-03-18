package com.kq.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kq.project.common.BaseResponse;
import com.kq.project.common.DeleteRequest;
import com.kq.project.common.ErrorCode;
import com.kq.project.common.ResultUtils;
import com.kq.project.exception.BusinessException;
import com.kq.project.model.dto.TeamQuery;
import com.kq.project.model.entity.Team;
import com.kq.project.model.entity.User;
import com.kq.project.model.entity.UserTeam;
import com.kq.project.model.request.TeamAddRequest;
import com.kq.project.model.request.TeamJoinRequest;
import com.kq.project.model.request.TeamQuitRequest;
import com.kq.project.model.request.TeamUpdateRequest;
import com.kq.project.model.vo.TeamUserVO;
import com.kq.project.model.vo.UserVo;
import com.kq.project.service.TeamService;
import com.kq.project.service.UserService;
import com.kq.project.service.UserTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
@Slf4j
@Api("队伍管理")
@CrossOrigin(origins = {"https://hb.kqcodingfrv.top/"}, allowCredentials = "true")
public class TeamController {

    @Resource
    private TeamService teamService;

    @Resource
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    @PostMapping("/add")
    @ApiOperation("创建队伍")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request) {
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest, team);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }

    @PostMapping("/update")
    @ApiOperation("修改队伍信息")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean update = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    @ApiOperation("查看队伍信息")
    public BaseResponse<TeamUserVO> getTeamById(@ApiParam(name = "队伍id") long id,HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        //2.将队伍信息和用户信息结合 TeamUserVO
        TeamUserVO teamUserVo = teamService.getTeamById(id, true, loginUser);
        if (teamUserVo == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //3.返回用户信息
        return ResultUtils.success(teamUserVo);
    }

    @GetMapping("/list")
    @ApiOperation("查看队伍列表")
    public BaseResponse<List<TeamUserVO>> listTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.getLoginUser(request);
        boolean isAdmin = userService.isAdmin(request);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        if (teamList.isEmpty()) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "未找到队伍信息");
        }
        queryTeamCount(request, teamList);
        return ResultUtils.success(teamList);
    }

    @PostMapping("/join")
    @ApiOperation("加入队伍")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request) {
        if (teamJoinRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/quit")
    @ApiOperation("退出队伍")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request) {
        if (teamQuitRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.quitTeam(teamQuitRequest, loginUser);
        return ResultUtils.success(result);
    }

    @GetMapping("/list/my/create")
    @ApiOperation("我创建的队伍")
    public BaseResponse<List<TeamUserVO>> listMyCreateTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long userId = loginUser.getId();
        List<TeamUserVO> teamList = teamService.listMyCreateTeam(userId);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        teamList.forEach(teamUserVO -> {
            teamUserVO.setHasJoin(true);
            Long TeamId = teamUserVO.getId(); //队伍id
            queryWrapper.eq("teamId", TeamId);
            List<UserTeam> list = userTeamService.list(queryWrapper);
            teamUserVO.setHasJoinNum(list.size());
            queryWrapper.clear(); //清空条件
        });
        return ResultUtils.success(teamList);
    }

    @GetMapping("/list/my/join")
    @ApiOperation("我加入的队伍")
    public BaseResponse<List<TeamUserVO>> listMyJoinTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "查询参数为空");
        }
        User loginUser = userService.getLoginUser(request);
        long id = loginUser.getId();
        List<TeamUserVO> teamList = userTeamService.listMyJoinTeam(id);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        teamList.forEach(teamUserVO -> {
            teamUserVO.setHasJoin(true);
            Long TeamId = teamUserVO.getId(); //队伍id
            queryWrapper.eq("teamId", TeamId);
            List<UserTeam> list = userTeamService.list(queryWrapper);
            teamUserVO.setHasJoinNum(list.size());
            queryWrapper.clear(); //清空条件
        });
        return ResultUtils.success(teamList);
    }

    @GetMapping("list/user")
    @ApiOperation("查询用户信息")
    public BaseResponse<List<Team>> getTeamListByUserId(Long userId){
        if(userId == null || userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Team> teamList = teamService.getTeamByUserId(userId);
        return ResultUtils.success(teamList);
    }

    private void queryTeamCount(HttpServletRequest request, List<TeamUserVO> teamList) {
        //条件查询出的队伍列表
        List<Long> teamIdList = teamList.stream().map(TeamUserVO::getId).collect(Collectors.toList());
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        try {
            User loginUser = userService.getLoginUser(request);
            userTeamQueryWrapper.eq("userId", loginUser.getId());
            userTeamQueryWrapper.in("teamId", teamIdList);
            //已加入队伍集合
            List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
            //已加入的队伍的id集合
            Set<Long> hasJoinTeamIdList = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
            teamList.forEach(team -> {
                boolean hasJoin = hasJoinTeamIdList.contains(team.getId());
                team.setHasJoin(hasJoin);
            });
        } catch (Exception e) {
        }

        List<UserTeam> userTeamJoinList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(teamIdList)){
            QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
            userTeamJoinQueryWrapper.in("teamId", teamIdList);
            userTeamJoinList = userTeamService.list(userTeamJoinQueryWrapper);
        }

        //按每个队伍Id分组
        Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamJoinList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));

        teamList.forEach(team -> {
            team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(), new ArrayList<>()).size());
        });
    }
}
