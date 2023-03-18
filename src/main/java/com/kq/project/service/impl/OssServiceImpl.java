package com.kq.project.service.impl;

import com.kq.project.service.OssService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public String uploadFileAvatar(MultipartFile file) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 生成随机的文件名
        String newFileName = UUID.randomUUID().toString() + getFileExtension(fileName);
        // 获取文件的输入流
        InputStream inputStream = file.getInputStream();
        // 设置上传的对象名称
        String objectName = "avatars/" + newFileName;
        // 上传文件到Minio服务器
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    inputStream, file.getSize(), -1)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 生成预签名的URL
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(7 * 24 * 3600) // URL的有效期为7天
                    .build());
        } catch (Exception e){
            e.printStackTrace();
        }
        // 返回上传的文件URL
        return url;
    }

    // 获取文件的扩展名
    private String getFileExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        return "." + parts[parts.length - 1];
    }


}
