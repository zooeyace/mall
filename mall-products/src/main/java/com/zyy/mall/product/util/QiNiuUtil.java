package com.zyy.mall.product.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
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

//    public void delete(String filename) {
//        Configuration configuration = new Configuration(Region.huadong());
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, configuration);
//        try {
//            bucketManager.delete(bucket, filename);
//            log.info("-----删除文件成功-----");
//        } catch (QiniuException e) {
//            log.error("删除失败 -----> {}", e.code());
//            log.error(e.response.toString());
//        }
//    }
}
