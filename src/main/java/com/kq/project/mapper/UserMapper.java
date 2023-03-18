package com.kq.project.mapper;

import com.kq.project.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper
 */
public interface UserMapper extends BaseMapper<User> {

    User login(String userAccount, String userPassword);

}




