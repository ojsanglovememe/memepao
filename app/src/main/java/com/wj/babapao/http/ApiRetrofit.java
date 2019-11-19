package com.wj.babapao.http;

import com.wj.babapao.http.interceptor.AddHeaderInterceptor;
import com.wj.babapao.http.interceptor.BaseUrlManagerInterceptor;
import com.wj.babapao.http.interceptor.SignRequestInterceptor;
import com.wj.babapao.http.utils.CustomGsonConverterFactory;
import com.wj.babapao.http.utils.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiRetrofit {

    private ApiService mApiService = null;

    private ApiRetrofit() {

        try {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BaseUrlManagerInterceptor())
                    .addInterceptor(new AddHeaderInterceptor())
                    .addInterceptor(new SignRequestInterceptor())
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("服务器地址")
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                    .client(client)
                    .build();

            mApiService = retrofit.create(ApiService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class SingletonHolder {
        private static final ApiRetrofit INSTANCE = new ApiRetrofit();
    }

    public static ApiRetrofit getInstance() {
        return ApiRetrofit.SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public boolean isApiService() {
        return mApiService != null;
    }

}
