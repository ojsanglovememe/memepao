package com.wj.babapao.http;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 */
public abstract class SimpleRxObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T t) {
        accept(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    public abstract void accept(T t);

}
