package com.wj.babapao.http.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.http.interceptor
 * @class describe
 * @time 2018/12/5 0005 09:52
 * @change
 * @chang time
 * @class describe
 */

public class BaseUrlManagerInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        //获取原始的originalRequest
        Request originalRequest = chain.request();
        //获取老的url
        HttpUrl oldUrl = originalRequest.url();
        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();
        HttpUrl baseURL = HttpUrl.parse("");

        if (baseURL == null) {
            return chain.proceed(chain.request());
        }

        List<String> urlNameList = originalRequest.headers("urlName");
        if (urlNameList != null && urlNameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("urlName");
            //获取头信息中配置的value
            String urlName = urlNameList.get(0);
            if (TextUtils.equals("upload", urlName)) {
                baseURL = HttpUrl.parse("");
            }
        }

        if (baseURL == null) {
            return chain.proceed(chain.request());
        }

        //重建新的HttpUrl，需要重新设置的url部分
        HttpUrl newHttpUrl = oldUrl.newBuilder()
                .scheme(baseURL.scheme())//http协议如：http或者https
                .host(baseURL.host())//主机地址
                .port(baseURL.port())//端口
                .build();
        //获取处理后的新newRequest
        Request newRequest = builder.url(newHttpUrl).build();
        return chain.proceed(newRequest);
    }
}
