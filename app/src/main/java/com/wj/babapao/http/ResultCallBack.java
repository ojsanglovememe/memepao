package com.wj.babapao.http;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface ResultCallBack<T> {

    void onSuccess(T t);

    void onError(int code, String errorMsg);

}
