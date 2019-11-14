package com.zj.caoshangfei.utils;


import org.apache.commons.lang.StringUtils;

import java.security.NoSuchAlgorithmException;

/**
 * Created by jin.zhang@fuwo.com on 2017/11/22.
 */
public class IdUtils {

    private static final String CANDIDATE = "9786452310";

    private static final int DEFAULT_LENGTH = 10;


    public static String encode(int num) {
        if (num < 0) {
            return "";
        }
        long r = num * 99 + 1024;
        String result = String.valueOf(r);
        int length = String.valueOf(r).length();

        if (length < DEFAULT_LENGTH) {
            int differ = DEFAULT_LENGTH - length;
            String prefix = CANDIDATE.substring(0, differ);
            result = differ + prefix + result;
        }
        return result;
    }

    public static Integer decode(String str) {
        if (StringUtils.isBlank(str)) {

        }
        //获取第一个数字，代表补几位
        int firstNum = Integer.valueOf(str.substring(0, 1));
        //去掉补位后的数字
        String ret = str.substring(firstNum + 1);

        int id = (Integer.valueOf(ret) - 1024) / 99;

        return id;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        for (int i = 0; i <= 50000; i++) {
            String result = encode(i);
            Integer decode = decode(result);
            System.out.println("encode=" + result + "*********decode=" + decode);
        }
    }
}
