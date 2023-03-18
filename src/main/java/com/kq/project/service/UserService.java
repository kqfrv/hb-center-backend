package com.kq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kq.project.model.entity.User;
import com.kq.project.model.request.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     */
    int userLogout(HttpServletRequest request);

    /**
     * 是否为管理员
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     */
    boolean isAdmin(User user);

    /**
     * 断言是管理员
     */
    void assertAdmin(HttpServletRequest request);

    /**
     * 获取登录用户（查缓存）
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     */
    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     */
    int updateUser(User user,User loginUser);


    /**
     * 匹配用户
     */
    List<User> matchUsers(long num, User loginUser);

    /**
     * 推荐用户
     */
    List<User> recommendUser(long pageSize, long pageNum, HttpServletRequest request);
}
