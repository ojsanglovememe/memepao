package com.wj.babapao.http.utils;


import com.wj.babapao.http.exception.ApiException;
import com.wj.babapao.utils.ConstantUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.http.utils
 * @class describe
 * @time 2019/2/27 0027 10:38
 * @change
 * @chang time
 * @class describe
 */
public class RetryWithDelayUtils implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int maxRetries;//最大出错重试次数
    private int retryDelaySecond;//重试间隔时间
    private int retryCount = 0;//当前出错重试次数

    public RetryWithDelayUtils(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable)  {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {

            if (throwable instanceof ApiException) {
                //指定的Code码不需要重试
                if (((ApiException) throwable).getCode() == ConstantUtils.KICK_OUT_CODE) {
                    return Observable.error(throwable);
                }
            }

            if (++retryCount <= maxRetries) {
                return Observable.timer(retryDelaySecond, TimeUnit.SECONDS);
            }
            return Observable.error(throwable);
        });
    }
}
