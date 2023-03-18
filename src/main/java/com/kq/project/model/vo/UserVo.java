package com.kq.project.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @Id
    private long id;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 标签列表
     */
    private String tags;
    /**
     * 电话
     */
    private String phone;
    /**
     * 联系方式
     */
    private String contactInfo;
    /**
     * 个人简介
     */
    private String profile;
    /**
     * 用户状态
     */
    private Integer userStatus;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色 0-普通用户 1-管理员
     */
    private Integer userRole;
    /**
     * 编号
     */
    private String planetCode;
}
