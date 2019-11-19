package com.wj.babapao.http.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @class 通过拦截器全局添加Http请求头
 * @chang time
 * @class describe
 */
public class StatisticsHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("deviceType", "android")
//                .header("appId", UserInfoManager.getInstance().getAppId())
                .build();
        return chain.proceed(request);
    }

}
