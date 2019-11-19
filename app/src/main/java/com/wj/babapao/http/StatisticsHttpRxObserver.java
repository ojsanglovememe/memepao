package com.wj.babapao.http;


import com.wj.babapao.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 */
public class StatisticsHttpRxObserver<T> implements Observer<T> {

    private ResultCallBack callBack;

    public StatisticsHttpRxObserver(ResultCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try {

            ApiException apiException = (ApiException) e;
            if (this.callBack != null) {
                this.callBack.onError(apiException.getCode(), apiException.getMsg());
            }

        } catch (Exception ignored) {

        }
    }

    @Override
    public void onComplete() {

    }
    @SuppressWarnings("unchecked")
    @Override
    public void onNext(@NonNull T t) {
        if (this.callBack != null) {
            this.callBack.onSuccess(t);
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

}
