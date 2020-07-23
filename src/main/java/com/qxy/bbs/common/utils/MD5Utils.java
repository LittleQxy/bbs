package com.qxy.bbs.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qixiangyang5
 * @create 2020/7/2 16:57
 */
public class MD5Utils {

    public static String md5(String str) {
        MessageDigest md;
        StringBuffer sb = new StringBuffer();
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] data = md.digest();
            int index;
            for (byte b : data) {
                index = b;
                if (index < 0) index += 256;
                if (index < 16) sb.append("0");
                sb.append(Integer.toHexString(index));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.printf(md5("齐向阳"));
    }
}
