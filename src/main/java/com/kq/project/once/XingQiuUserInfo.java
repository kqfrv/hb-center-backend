package com.kq.project.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户信息
 */
@Data
public class XingQiuUserInfo {
    /**

     */
    @ExcelProperty("成员编号")
    private String planetCode;
    /**
     */
    @ExcelProperty("成员昵称")
    private String username;

}