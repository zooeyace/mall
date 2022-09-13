package com.zyy.VO;

import lombok.Data;

@Data
public class MyPutRet {
    public String key;
    public String hash;
    public String bucket;
    public long fsize;
}
