package com.zyy.util;

public class FileUtil {
    public static String getFileSuffix(String filename) {
        return filename.contains(".") ? filename.substring(filename.lastIndexOf(".")) : null;
    }
}
