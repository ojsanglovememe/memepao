package com.wj.babapao.http;

import com.wj.babapao.http.interceptor.BaseUrlManagerInterceptor;
import com.wj.babapao.http.interceptor.SignRequestInterceptor;
import com.wj.babapao.http.interceptor.StatisticsHeaderInterceptor;
import com.wj.babapao.http.utils.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticsApiRetrofit {

    private ApiService mApiService;

    private StatisticsApiRetrofit() {

        try {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .addInterceptor(new BaseUrlManagerInterceptor())
                    .addInterceptor(new StatisticsHeaderInterceptor())
                    .addInterceptor(new SignRequestInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("服务器地址")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                    .client(mClient)
                    .build();

            mApiService = retrofit.create(ApiService.class);
        } catch (Exception e) {

        }

    }

    private static class SingletonHolder {
        private static final StatisticsApiRetrofit INSTANCE = new StatisticsApiRetrofit();
    }

    public static StatisticsApiRetrofit getInstance() {
        return StatisticsApiRetrofit.SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public boolean isApiService() {
        return mApiService != null;
    }

}
