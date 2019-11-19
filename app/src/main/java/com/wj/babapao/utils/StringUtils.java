/*
 * Copyright (c) 2014. kugou.com
 */

package com.wj.babapao.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Created by LeonLee on 2014/9/4 17:21.</p>
 * <p><a href="mailto:liwenlong1@kugou.net">Email:liwenlong1@kugou.net</a></p>
 * <p/>
 * 字符工具处理类
 * <p/>
 * <ul>
 * 对网络操作相关
 * <li>{@link #modifyUrl(String)} 修正连接使其符合网络协议</li>
 * <li>{@link #errEncode(String)} 对乱码进行转码</li>
 * <li>{@link #isErrCode(String)} 是否乱码</li>
 * </ul>
 * <ul>
 * 对字符处理操作(合并、分割)
 * <li>{@link #split(String, String)} 分割字符串</li>
 * <li>{@link #splitString(String, String)} 把|分隔的字符串转化成 字符串List</li>
 * <li>{@link #mergeString(List, String)} 把string list 转化为 | 分隔的字符串</li>
 * </ul>
 * <ul>
 * 对字符格式化相关
 * <li>{@link #add0IfLgTen(int)} 小于10的正整数前面补0</li>
 * <li>{@link #getSizeText(long)} 单位换算</li>
 * <li>{@link #getSizeText(Context, long)} 返回文件大小表示</li>
 * <li>{@link #imeiToBigInteger(String)} 把IMEI用一大整数代替</li>
 * <li>{@link #imeiTolong(String)} 把IMEI用一大整数代替</li>
 * <li>{@link #formatTime(DateFormat, long)} 格式化时间</li>
 * </ul>
 * <ul>
 * 对字符获取与判断相关
 * <li>{@link #getRandomString(int)} 产生一个描写长度的随机字符串</li>
 * <li>{@link #bytesToHex(byte[])} 将byte数组转换为16进制</li>
 * <li>{@link #isEmpty(CharSequence)} 判断字符串是否为空</li>
 * <li>{@link #versionOver(String, String)} 判断两个版本的高低</li>
 * <li>{@link #countWords(String)} 统计字符串(汉字、数字和英文)中字节个数</li>
 * <li>{@link #getExceptionString(Exception)} 将Exception转换成字符串</li>
 * </ul>
 * <ul>
 * 对文件相关的操作
 * <li>{@link #formatFilePath(String)} 格式化文件路径（去除一些特殊字符）</li>
 * <li>{@link #hashKeyForDisk(String)} 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名</li>
 * <li>{@link #spiltImageName(String)} 截取图片名称</li>
 * <li>{@link #hashImageName(String, String)} 通过图像地址和hash值获得由hash值和图片扩展名组成的一个文件名</li>
 * </ul>
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 产生一个描写长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sbf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);// 0~61
            sbf.append(str.charAt(number));
        }
        return sbf.toString();
    }

    /**
     * 生成GUID字符串号
     *
     * @return
     */
    public static String makeGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /**
     * 分割字符串
     *
     * @param src
     * @param delimiter
     * @return
     */
    public static String[] split(String src, String delimiter) {
        if (src == null || delimiter == null || src.trim().equals("") || delimiter.trim().equals("")) {
            return new String[]{src};
        }
        ArrayList<String> list = new ArrayList<>();
        int lengthOfDelimiter = delimiter.length();
        int pos = 0;
        while (true) {
            if (pos < src.length()) {
                int indexOfDelimiter = src.indexOf(delimiter, pos);
                if (indexOfDelimiter < 0) {
                    list.add(src.substring(pos));
                    break;
                } else {
                    list.add(src.substring(pos, indexOfDelimiter));
                    pos = indexOfDelimiter + lengthOfDelimiter;
                }
            } else if (pos == src.length()) {
                list.add("");
                break;
            } else {
                break;
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    /**
     * 修正连接使其符合网络协议
     *
     * @param url
     * @return
     */
    public static String modifyUrl(String url) {
        if (url == null)
            return null;
        String enc = "UTF-8";
        StringBuilder strBuffer = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if (c == '\\')
                c = '/';
            if (c > 256 || c == ' ' || c == '[' || c == ']' || c == '.' || c == '(' || c == ')') {
                strBuffer.append(UrlEncoderUtils.encode("" + c, enc));
            } else {
                strBuffer.append(c);
            }
        }
        return strBuffer.toString();
    }

    /**
     * 对乱码进行转码
     *
     * @param s
     * @return
     */
    public static String errEncode(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5\\u0800-\\u4e00]+");
        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) {
            try {
                s = new String(s.getBytes("iso-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    /**
     * 是否乱码
     *
     * @param s
     * @return true 乱码
     */
    public static boolean isErrCode(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    /**
     * 小于10的正整数前面补0
     *
     * @param i
     * @return
     */
    public static String add0IfLgTen(int i) {
        if (0 < i && i < 10) {
            return "0" + i + ".";
        } else {
            return i + ".";
        }
    }

    /**
     * 单位换算
     *
     * @param fileSize
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getSizeText(long fileSize) {
        if (fileSize <= 0) {
            return "0.0M";
        }
        if (fileSize < 100 * 1024) {
            // 大于0小于100K时，直接返回“0.1M”（产品需求）
            return "0.1M";
        }
        float result = fileSize;
        String suffix = "M";
        result = result / 1024 / 1024;
        return String.format("%.1f", result) + suffix;
    }

    /**
     * 返回文件大小表示
     *
     * @param context
     * @param bytes   字节数
     * @return
     */
    public static String getSizeText(Context context, long bytes) {
        String sizeText = "";
        if (bytes < 0) {
            return sizeText;
        } else {
            sizeText = Formatter.formatFileSize(context, bytes);
        }
        return sizeText;

    }

    /**
     * 截取图片名称
     *
     * @param imageurl
     * @return
     */
    public static String spiltImageName(String imageurl) {
        if (TextUtils.isEmpty(imageurl)) {
            return null;
        }
        imageurl = imageurl.toLowerCase();
        int start = imageurl.lastIndexOf("filename");
        if (start == -1) {
            start = imageurl.lastIndexOf("/");
            if (start == -1) {
                return null;
            } else {
                start += 1;
            }
        } else {
            start += 9;
        }
        int end = imageurl.indexOf(".jpg", start);
        if (end == -1) {
            end = imageurl.indexOf(".png", start);
            if (end == -1) {
                return null;
            } else {
                end += 4;
            }
        } else {
            end += 4;
        }
        return imageurl.substring(start, end);
    }

    /**
     * 通过图像地址和hash值获得由hash值和图片扩展名组成的一个文件名
     *
     * @param imageUrl
     * @param hash
     * @return
     */
    public static String hashImageName(String imageUrl, String hash) {
        if (TextUtils.isEmpty(imageUrl) || TextUtils.isEmpty(hash)) {
            return null;
        }
        imageUrl = imageUrl.toLowerCase();
        hash = hash.toLowerCase();

        int index = imageUrl.indexOf(".jpg");
        if (index == -1) {
            index = imageUrl.indexOf(".png");
        }
        return hash + imageUrl.substring(index);
    }

    /**
     * 将Exception转换成字符串
     */
    public static String getExceptionString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString().replace("\n", "<br />");
    }

    /**
     * 把IMEI用一大整数代替
     *
     * @param imei
     * @return
     */
    public static String imeiToBigInteger(String imei) {
        BigInteger result = new BigInteger("0");
        try {
            BigInteger n = new BigInteger("16");
            String md5 = MD5Utils.getMd5(imei);
            int size = md5.length();
            for (int i = 0; i < size; i++) {
                BigInteger a = new BigInteger("" + md5.charAt(i), 16);
                BigInteger b = n.pow(size - 1 - i);
                result = result.add(a.multiply(b));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 把IMEI用一大整数代替
     *
     * @param imei
     * @return
     */
    public static BigInteger imeiTolong(String imei) {
        BigInteger result = new BigInteger("0");
        try {
            BigInteger n = new BigInteger("16");
            String md5 = MD5Utils.getMd5(imei);
            int size = md5.length();
            for (int i = 0; i < size; i++) {
                BigInteger a = new BigInteger("" + md5.charAt(i), 16);
                BigInteger b = n.pow(size - 1 - i);
                result = result.add(a.multiply(b));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>按字节截取字符串。</p> 按照指定的有效编码格式，指定的字节长度，以及截断方向(右截断/左截断)。截取后不产生乱码。<br>
     * 返回的字符串的字节长度将小于等于指定长度。可能为空字符串。<br>
     *
     * @param original          原字符串
     * @param charsetName       编码格式名
     * @param byteLen           字节长度
     * @param isRightTruncation 是否右截断。
     * @return String
     * @throws UnsupportedEncodingException
     * @author leo_soul
     */
//    public static String subStrByByteLen(String original, String charsetName, int byteLen, boolean isRightTruncation) throws UnsupportedEncodingException {
//        if (original == null || "".equals(original.trim()))
//            return "";
//        if (charsetName == null || "".equals(charsetName))
//            throw new UnsupportedEncodingException("subStrByByteLen方法，必须指定编码格式");
//        byte[] bytes = original.getBytes(charsetName);
//        if (byteLen <= 0)
//            return "";
//        if (byteLen >= bytes.length)
//            return original;
//
//        int tempLen = 0;
//        String result = "";
//        if (isRightTruncation) {
//            //右截断
//            //按照指定字节长度截断，再转成临时String，计算长度。
//            tempLen = new String(bytes, 0, byteLen, charsetName).length();
//            //根据该长度右截取原字符串。
//            result = original.substring(0, tempLen);
//            //超过预订字节长度，则去掉一个字符。
//            if (result != null && !"".equals(result.trim()) && result.getBytes(charsetName).length > byteLen)
//                result = original.substring(0, tempLen - 1);
//        } else {
//            //左截断
//            //全字符长-左截预订点(注意必须是预定点，bytes.length-byteLen+1)所右截断的串的字符长+1，计算长度。(为了给左截串多留一个字符。)
//            //tempLen = original.length()-new String(bytes,0,bytes.length-byteLen+1,charsetName).length()+1;
//            //根据该长度左截取原字符串。注意起始下标计算方法。
//            //result = original.substring(original.length()-tempLen);
//            //由于以上公式可以展开，由此得到简化版。
//            tempLen = new String(bytes, 0, bytes.length - byteLen + 1, charsetName).length() - 1;
//            result = original.substring(tempLen);
//            //超过预订字节长度，则去掉一个字符(左截)。
//            if (result != null && !"".equals(result.trim()) && result.getBytes(charsetName).length > byteLen)
//                result = original.substring(tempLen + 1);
//        }
//        return result;
//    }

    /**
     * 统计字符串(汉字、数字和英文)中字节个数
     *
     * @param str
     * @return
     */
    public static int countWords(String str) {
        int len = 0;
        try {
            if (!TextUtils.isEmpty(str)) {
                len = str.getBytes("GBK").length;
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return len;
    }

    /**
     * 判断两个版本的高低
     *
     * @param v1
     * @param v2
     */
    public static boolean versionOver(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return false;
        if (v1.startsWith("v"))
            v1 = v1.substring(1);
        if (v2.startsWith("v"))
            v2 = v2.substring(1);
        String[] vs1 = v1.split("\\.");
        String[] vs2 = v2.split("\\.");
        int len = vs1.length;
        len = len < vs2.length ? len : vs2.length;
        for (int i = 0; i < len; i++) {
            try {
                int vi1 = Integer.parseInt(vs1[i]);
                int vi2 = Integer.parseInt(vs2[i]);
                if (vi1 < vi2)
                    return false;
                else if (vi1 > vi2)
                    return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return vs1.length >= vs2.length;
    }

    /**
     * 格式化时间
     *
     * @param dateFormat
     * @param time
     * @return
     */
    public static String formatTime(DateFormat dateFormat, long time) {
        // 如果时间只有10位，转化为android时间
        if (String.valueOf(time).length() < 13) {
            time *= 1000;
        }
        return dateFormat.format(new Date(time));
    }

    /**
     * 把|分隔的字符串转化成 字符串List
     *
     * @return
     */
    public static ArrayList<String> splitString(String strNameLst, String split) {
        if (TextUtils.isEmpty(strNameLst)) {
            return null;
        }
        String[] array = StringUtils.split(strNameLst, split);
        if (array == null || array.length == 0) {
            return null;
        }
        ArrayList<String> temp = new ArrayList<>();
        Collections.addAll(temp, array);
        return temp;
    }

    /**
     * 把string list 转化为 | 分隔的字符串
     *
     * @param stringLst
     * @return
     */
    public static String mergeString(List<String> stringLst, String split) {
        StringBuilder sb = new StringBuilder();
        int len = stringLst.size();
        for (int i = 0; i < len; i++) {
            sb.append(stringLst.get(i));
            if (i < (len - 1)) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    /**
     * 将byte数组转换为16进制
     *
     * @param bytes
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 格式化文件路径（去除一些特殊字符）
     *
     * @param filePath
     * @return
     */
    public static String formatFilePath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        return filePath.replace("\\", "").replace("/", "").replace("*", "").replace("?", "").replace(":", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "");
    }

    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     */
    public static String hashKeyForDisk(String key) {
        if (TextUtils.isEmpty(key)) {
            return key;
        }
        String cacheKey = MD5Utils.getMd5(key);
        if (cacheKey == null) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 根据长度折叠字符串
     *
     * @param s
     * @param length
     * @return
     */
    public static String getFoldStringByLength(String s, int length) {
        if (TextUtils.isEmpty(s) || length == 0) {
            return "";
        }
        if (length >= s.length()) {
            return s;
        }
        return s.substring(0, length) + "…";
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @return int 得到的字符串长度
     */
    public static double getLength(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        //进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 获取指定长度的字串，中文算2个字符,英文数字算1个字符
     *
     * @param src       指定的字符串
     * @param maxLength 最大长度(字符)
     * @return 截取的字符串
     */
    public static String substring(String src, int start, int maxLength) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = start; i < src.length(); i++) {
            // 获取一个字符
            String temp = src.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
            if (valueLength == maxLength) {
                return src.substring(start, i + 1);
            } else if (valueLength > maxLength) {
                return src.substring(start, i);
            }
        }
        return src;
    }

    /**
     * 获取指定长度的字串，中文占2个字符,英文数字占1个字符
     * 超过部分用...代替
     *
     * @param src       指定的字符串
     * @param maxLength 最大长度
     * @param addDot    是否使用...代替超出部分
     * @return 字符串的长度
     */
    public static String substring(String src, int maxLength, boolean addDot) {
        String result = substring(src, 0, maxLength);
        if (!result.equals(src)) {
            return result + "...";
        }
        return src;
    }

    public static String formatStrLen(String userName, int maxLength) {

        if (!TextUtils.isEmpty(userName) && userName.length() > maxLength) {
            String str = userName.substring(0, maxLength);
            return str + "...";
        }

        return userName;
    }

    public static void modifyTextViewDrawable(TextView v, Drawable drawable, int index) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //index 0:左 1：上 2：右 3：下
        if (index == 0) {
            v.setCompoundDrawables(drawable, null, null, null);
        } else if (index == 1) {
            v.setCompoundDrawables(null, drawable, null, null);
        } else if (index == 2) {
            v.setCompoundDrawables(null, null, drawable, null);
        } else {
            v.setCompoundDrawables(null, null, null, drawable);
        }
    }

    /***
     * 去掉手机号码中的空格和-
     * @param phone
     * @return
     */
    public static String formatPhoneRemoveSpaces(String phone) {
        String str = phone.replaceAll(" ", "");
        return str.replaceAll("-", "");
    }

    /**
     * 获取File md5
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {

        StringBuffer stringbuffer = null;
        FileInputStream in = null;
        try {
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(byteBuffer);
            byte[] bytes = messagedigest.digest();
            int n = bytes.length;
            stringbuffer = new StringBuffer(2 * n);
            for (byte bt : bytes) {
                char c0 = hexDigits[(bt & 0xf0) >> 4];
                char c1 = hexDigits[bt & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringbuffer != null ? stringbuffer.toString() : "";

    }

    public static byte[] toByteArray(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            return "";
        }

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    // 压缩
    public static String compress(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        ByteArrayOutputStream out = null;
        GZIPOutputStream gzip = null;

        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");

        } catch (Exception e) {

        } finally {

            if (gzip != null) {
                gzip.close();
            }

            if (out != null) {
                out.close();
            }
        }
        return str;
    }

    // 解压缩
    public static String uncompress(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gunzip = null;

        try {

            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            gunzip = new GZIPInputStream(in);

            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
            return out.toString();

        } catch (Exception e) {

        } finally {
            if (out != null) {
                out.close();
            }

            if (gunzip != null) {
                gunzip.close();
            }

            if (in != null) {
                in.close();
            }
        }

        return str;
    }

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /***
     * 关键字高亮显示
     * @param context
     * @param text  需要显示的文字
     * @param target    需要高亮的关键字
     * @param colorRes  高亮颜色
     * @return
     */
    public static SpannableString getHighLightText(Context context, String text, String target, @ColorRes int colorRes) {
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(ContextCompat.getColor(context, colorRes));
            spannableString.setSpan(span, matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

}
