package com.zyy.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zyy.VO.MyPutRet;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
@Data
public class QiNiuUtil {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Bean
    private UploadManager uploadManager() {
        return new UploadManager(new Configuration(Region.region0()));
    }

    private String getToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    public String upload(String filepath) {
        String filename = filepath.substring(filepath.indexOf("\\"), filepath.lastIndexOf("."));
        try {
            Response response = uploadManager().put(filepath, filename, getToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("---- 文件上传成功 -----key: {} ; hash: {}", putRet.key, putRet.hash);
            return filename;
        } catch (QiniuException e) {
            try {
                log.error("---- 文件上传失败 ---- {}", e.response.bodyString());
            } catch (QiniuException qiniuException) {

            }
        }
        return null;
    }

    public String upload(InputStream inputStream, String filename) {
        try {
            Response response = uploadManager().put(inputStream, filename, getToken(), null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("---- 文件上传成功 -----key: {} ; hash: {}", putRet.key, putRet.hash);
            return filename;
        } catch (QiniuException e) {
            try {
                log.error("---- 文件上传失败 ---- {}", e.response.bodyString());
            } catch (QiniuException qiniuException) {

            }
        }
        return null;
    }

    /**
     * 服务器直传
     * @param file      multipartFile
     */
    public MyPutRet upload(MultipartFile file) {
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(new Configuration(Region.region0()));
        //...生成上传凭证，然后准备上传
        byte[] uploadBytes = null;
        try {
            uploadBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = UUID.randomUUID().toString() + suffix;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            // 解析上传成功的结果
            return response.jsonToObject(MyPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
        return null;
    }

    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }
}
