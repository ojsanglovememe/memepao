/*
 * Copyright (c) 2014. kugou.com
 */

package com.wj.babapao.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Utils {

    private static char[] Digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private MD5Utils() {
    }

    /**
     * 获取字节数组的MD5值
     *
     * @param arrays 字节数组
     */
    public static String getMd5(byte[] arrays) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(arrays);
            return StringUtils.bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符串的MD5值
     *
     * @param src 要获取的字符串
     */
    public static String getMd5(String src) {
        return getMd5(src.getBytes());
    }

    /**
     * 获取文件的MD5值
     *
     * @param file 要获取的文件
     */
    public static String getMd5(File file) {
        String s = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
                    0, file.length());

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            s = StringUtils.bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return s;
    }

    public static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的 sprintf(outbuf,"%02X",ib)
     */
    public static String byteHEX(byte ib) {
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        return new String(ob);
    }


    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            return algorithm.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            if (TextUtils.isEmpty(toHex(md5(s)))) {
                return s;
            }
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * 对密码按照用户名，密码，盐进行加密
     *
     * @param username 用户名
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String encryptPassword(String username, String password, String salt) {
        return MD5Utils.hash(username + password + salt);
    }

    /**
     * 对密码按照密码，盐进行加密
     *
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        return MD5Utils.hash(password + salt);
    }

    public static String encryptPassword(String password) {
        return MD5Utils.hash(password);
    }

    public static String getDefaultPassword() {
        return MD5Utils.hash("123456");
    }
}
