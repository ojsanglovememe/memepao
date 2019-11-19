package com.wj.babapao.http;


public class HttpResultModel<T> {

    private int code = 0;

    private String msg;

    private int status;

    private T data = null;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }
}
