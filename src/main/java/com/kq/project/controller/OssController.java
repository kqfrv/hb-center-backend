package com.kq.project.controller;



import com.kq.project.common.BaseResponse;
import com.kq.project.common.ErrorCode;
import com.kq.project.common.ResultUtils;
import com.kq.project.exception.BusinessException;
import com.kq.project.service.OssService;
import io.minio.errors.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Api("Minio文件管理")
@RestController
@RequestMapping("/fileOss")
public class OssController {

    @Resource
    private OssService ossService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public BaseResponse<String> uploadOssFile(@RequestParam(required = false) MultipartFile file)   {
        //1.获取上传的文件
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //2.返回上传到oss的路径
        String url = null;
        try {
            url = ossService.uploadFileAvatar(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.返回r对象
        return ResultUtils.success(url);
    }

}
