package com.zj.caoshangfei.utils;

import java.security.MessageDigest;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/25.
 */
public class MD5Utils {

    /**
     * MD5加密
     *
     * @param message 要进行MD5加密的字符串
     * @return 加密结果为16位小写字符串
     */
    public static String getMD5Hash(String message) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (Exception e) {

        }
        return result;
    }

}
