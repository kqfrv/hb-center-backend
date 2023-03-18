package com.kq.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分类请求参数
 */
@Data
public class PageRequest implements Serializable {


    /**
     * 页面大小
     */
    protected int pageSize;
    /**
     * 页码
     */
    protected int pageNum;
}
