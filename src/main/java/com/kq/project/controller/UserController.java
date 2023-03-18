package com.kq.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kq.project.common.BaseResponse;
import com.kq.project.common.ErrorCode;
import com.kq.project.common.ResultUtils;
import com.kq.project.exception.BusinessException;
import com.kq.project.model.dto.UserQuery;
import com.kq.project.model.entity.User;
import com.kq.project.model.request.UserLoginRequest;
import com.kq.project.model.request.UserRegisterRequest;
import com.kq.project.model.vo.UserVo;
import com.kq.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("注册")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        long result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入正确的账户或密码");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    @ApiOperation("注销")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    @ApiOperation("当前用户")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        userService.assertAdmin(request);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        userService.assertAdmin(request);
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户信息")
    public BaseResponse<Integer> updateUser(@Validated @RequestBody User user, HttpServletRequest request) {
        //1.校验权限
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //2.校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = userService.updateUser(user, loginUser);
        return ResultUtils.success(result);
    }

    @GetMapping("/recommend")
    @ApiOperation("获取推荐用户")
    public BaseResponse<Page<User>> recommendUser(long pageSize, long pageNum, HttpServletRequest request) {
        Page<User> userPage = new Page<>(pageSize,pageNum) ;
        List<User> userList = userService.recommendUser(pageSize, pageNum, request);
        userPage.setRecords(userList);
        return ResultUtils.success(userPage);
    }

    @GetMapping("/match")
    @ApiOperation("获取最匹配的用户")
    public BaseResponse<List<User>> matchUsers(long num, HttpServletRequest request) {
        if (num <= 0 || num > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.matchUsers(num, user));
    }

    @PostMapping("/searchPage")
    @ApiOperation("根据条件查询用户")
    public BaseResponse<Page<User>> searchUsersPage(@RequestBody UserQuery userQuery) {
        String searchText = userQuery.getSearchText();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.like("username", searchText)
                    .or().like("profile", searchText)
                    .or().like("tags", searchText);
        }
        Page<User> page = new Page<>(userQuery.getPageNum(), userQuery.getPageSize());
        Page<User> userListPage = userService.page(page, queryWrapper);
        List<User> userList = userListPage.getRecords();
        List<User> safetyUserList = userList.stream()
                .map(user -> userService.getSafetyUser(user))
                .collect(Collectors.toList());
        userListPage.setRecords(safetyUserList);
        return ResultUtils.success(userListPage);
    }

    @PostMapping("/getUserListByIds")
    @ApiOperation("获取用户信息")
    public BaseResponse<List<UserVo>> getUserListByIds(@RequestBody UserQuery userQuery){
        List<User> userList = userService.listByIds(userQuery.getIds());
        List<UserVo> userVoList = userList.stream().map(user -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVoList);
    }
}
