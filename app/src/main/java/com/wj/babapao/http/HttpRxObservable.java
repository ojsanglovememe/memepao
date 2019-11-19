package com.wj.babapao.http;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wj.babapao.http.function.HttpResultFunction;
import com.wj.babapao.http.function.ServerResultFunction;
import com.wj.babapao.http.utils.RetryWithDelayUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 适用Retrofit网络请求Observable(被监听者)
 *
 * @author
 */
public class HttpRxObservable {

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     *
     * @author huxiangliang
     */
    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable) {
        return apiObservable
                .map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable, int maxRetries, int retryDelaySecond) {
        return apiObservable
                .map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .retryWhen(new RetryWithDelayUtils(maxRetries, retryDelaySecond))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity.../RxFragment...
     *
     * @author huxiangliang
     */
    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable, LifecycleProvider lifecycle) {
        Observable observable;
        if (lifecycle != null) {
            //随生命周期自动管理.eg:onCreate(start)->onStop(end)
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable, LifecycleProvider lifecycle, int maxRetries, int retryDelaySecond) {
        Observable observable;
        if (lifecycle != null) {
            //随生命周期自动管理.eg:onCreate(start)->onStop(end)
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .retryWhen(new RetryWithDelayUtils(maxRetries, retryDelaySecond))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable, maxRetries, retryDelaySecond);
        }
        return observable;
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     *
     * @author huxiangliang
     */
    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event) {
        // showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:ActivityEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     *
     * @author huxiangliang
     */
    @SuppressWarnings("unchecked")
    public static Observable getObservable(Observable apiObservable, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event) {
        //  showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:FragmentEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

}
