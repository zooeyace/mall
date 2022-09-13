package com.zyy.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zyy.common.utils.R;
import com.zyy.util.GiteeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class OssController {

//    @Resource
//    private QiNiuUtil qiNiuUtil;

    @RequestMapping("/oss/upload")
    public R getQiNiuPolicy(@RequestParam("file") MultipartFile file) throws IOException {
//        JSONObject json = new JSONObject();
//        MyPutRet putRet = qiNiuUtil.upload(file);
//        StringMap putPolicy = new StringMap();
//        putPolicy.put("returnBody", new ObjectMapper().writeValueAsString(putRet));
//        putPolicy.put("mimeLimit", "!application/json;text/plain");
//        json.put("token", qiNiuUtil.auth().uploadToken(qiNiuUtil.getBucket(), null, 1800, putPolicy));
//        json.put("host", "http://rhuc1rqdv.hd-bkt.clouddn.com");
//        json.put("dir", new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/");
//        return R.ok().put("data", json);


//        Auth auth = Auth.create(qiNiuUtil.getAccessKey(), qiNiuUtil.getSecretKey());
//        StringMap putPolicy = new StringMap();
//        putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
//        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
//        putPolicy.put("callbackBodyType", "application/json");
//        long expireSeconds = 3600;
//        String upToken = auth.uploadToken(qiNiuUtil.getBucket(), null, expireSeconds, putPolicy);
//        System.out.println(upToken);

//        return R.ok().put("data", upToken);
        // 根据文件名生成指定的url
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) return R.error("文件名为空");
        String url = GiteeUtil.createUploadFileUrl(filename);
        Map<String, Object> uploadBodyMap = GiteeUtil.getUploadBodyMap(file.getBytes());
        String resp = HttpUtil.post(url, uploadBodyMap);
        JSONObject json = JSONUtil.parseObj(resp);
        if (json.getObj("commit") == null) {
            return R.error();
        }
        JSONObject content = JSONUtil.parseObj(json.getObj("content"));
        System.out.println(content);
        return R.ok().put("data", content);
        // "download_url": "https://gitee.com/zooeyace/picture-bed/raw/master/upload-img/2022-09-09/1662712685752_26adf403-0eef-4e42-ad2f-c2765384f33a.jpg"
        // "name": "1662712685752_26adf403-0eef-4e42-ad2f-c2765384f33a.jpg"
    }
}