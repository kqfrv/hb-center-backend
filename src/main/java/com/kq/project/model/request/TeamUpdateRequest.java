package com.kq.project.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("更新队伍请求")
public class TeamUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * 更新那条数据
     */
    private Long id;
    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireTime;

    /**
     * 0 - 公开 ，1 - 私有 ，2 - 加密
     */
    private Integer status;
    /**
     * 密码
     */
    private String password;

    /**
     * 公告
     */
    private String announce;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 图片地址
     */
    private String avatarUrl;
}
