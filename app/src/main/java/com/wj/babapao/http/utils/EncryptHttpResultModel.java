package com.wj.babapao.http.utils;

import com.wj.babapao.utils.ConstantUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/15.
 */

public class EncryptHttpResultModel implements Serializable {

    private int code = 0;

    private String msg;

    private int status;

    private EncryptMode data = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return this.code == 100001 || status == 1;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String returnMessage) {
        this.msg = returnMessage;
    }

    public int getCode() {
        return code;
    }

    public String getJsonData() {
        try {
            return data.getJsonData();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static class EncryptMode implements Serializable {

        public String key;
        public String data;

        public String getJsonData() throws Exception {
            String rsaKey = EncryptUtil.RSADecrypt(ConstantUtils.ENCRYPT_API_KEY, key);
            return EncryptUtil.DESDecrypt(rsaKey, data);
        }

    }

}
