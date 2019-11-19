package com.wj.babapao.base;

import android.content.Context;

import com.wj.babapao.http.ApiRetrofit;
import com.wj.babapao.http.ApiService;
import com.wj.babapao.http.HttpRxObservable;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;


public class BasePresenter<V> {

    protected ApiService mApiService;
    protected WeakReference<Context> mContextRef;
    protected WeakReference<V> mViewRef;

    public BasePresenter(Context context, V view) {
        mApiService = ApiRetrofit.getInstance().getApiService();
        attachView(context, view);
    }

    public void attachView(Context context, V view) {
        mContextRef = new WeakReference<>(context);
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if (mContextRef != null) {
            mContextRef.clear();
            mContextRef = null;
        }
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /***
     * UI展示相关的操作需要判断一下 Activity 是否已经 finish.
     * @return
     */
    public boolean isAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /***
     * 当前Activity上下文
     * @return
     */
    protected Context getContext() {
        return mContextRef != null ? mContextRef.get() : null;
    }

    /***
     *
     * @return
     */
    protected V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

    /**
     * 对 ACTIVITY 生命周期进行管理
     *
     * @return
     */
    public LifecycleProvider getLifecycleProvider() {
        V view = getView();
        if ((view != null) && (view instanceof LifecycleProvider)) {
            return (LifecycleProvider) view;
        }

        return null;
    }

    public boolean isApiService() {
        return mApiService != null;
    }

    @SuppressWarnings("unchecked")
    public void addMapSubscription(Observable apiObservable, Observer observer) {

        if (!isAttached()) {    //当前Activity已销毁，不进行任何操作
            return;
        }

        HttpRxObservable.getObservable(apiObservable, getLifecycleProvider()).subscribe(observer);
    }

    @SuppressWarnings("unchecked")
    public void addMapSubscription(Observable apiObservable, Observer observer, int maxRetries, int retryDelaySecond) {

        if (!isAttached()) {    //当前Activity已销毁，不进行任何操作
            return;
        }

        HttpRxObservable.getObservable(apiObservable, getLifecycleProvider(), maxRetries, retryDelaySecond).subscribe(observer);
    }


}
