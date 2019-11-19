package com.wj.babapao.utils;

import android.text.TextUtils;

import com.wj.babapao.R;
import com.wj.babapao.http.RequestParams;
import com.wj.babapao.http.interceptor.AddHeaderInterceptor;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Request;


public class SignRequestUtils {

    private SignRequestUtils() {
    }

    /**
     * @param content
     * @param charset
     * @return
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(SystemUtils.getResString(R.string.fq_md5_error) + charset);
        }
    }

    /**
     * 参数签名加密
     *
     * @param parameters
     * @return
     */
    public static String signRequest(Request original, TreeMap<String, Object> parameters, String charset) {

        TreeMap<String, Object> treeMap = new TreeMap<>(String::compareTo);
        treeMap.putAll(parameters);

        treeMap.put(AddHeaderInterceptor.TIME_STAMP_STR, original.header(AddHeaderInterceptor.TIME_STAMP_STR));
        treeMap.put(AddHeaderInterceptor.RANDOM_STR, original.header(AddHeaderInterceptor.RANDOM_STR));
        treeMap.put(AddHeaderInterceptor.DEVICE_ID, original.header(AddHeaderInterceptor.DEVICE_ID));

        StringBuilder toSignStr = new StringBuilder();
        //把map中的集合拼接成字符串
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (TextUtils.equals(RequestParams.PAGE_SIZE, key)
                    || TextUtils.equals(RequestParams.PAGE_NUMBER, key) || value == null) {
                continue;
            }
            toSignStr.append(key).append("=").append(value).append("&");
        }

        //删掉最后一个&符号
        if (toSignStr.indexOf("&") != -1) {
            toSignStr.deleteCharAt(toSignStr.lastIndexOf("&"));
        }


        StringBuilder preSignStr = new StringBuilder();
        preSignStr.append("自定义的key");
        preSignStr.append("_");
        preSignStr.append(toSignStr.toString().toUpperCase());

//        LogManager.i("preSignStr>>>"+preSignStr.toString());

        //进行MD5加密
//        String sign = EncryptUtils.encryptMD5ToString(getContentBytes(preSignStr.toString(), charset));
//        logMsg("加密后的签名：" + sign);
        return MD5Utils.hash(preSignStr.toString());
    }

    public static String getRandomString() {
        return StringUtils.getRandomString(16);
    }

    public static String getTimestampString() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

}
