package com.wj.babapao.http.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.wj.babapao.utils.SignRequestUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class SignRequestInterceptor implements Interceptor {

    @SuppressWarnings("unchecked")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        //获取到方法
        String method = original.method();

        //get请求的封装
        if (TextUtils.equals(method, "GET") || TextUtils.equals(method, "PUT")) {
            //获取到请求地址api
            HttpUrl originalHttpUrl = original.url();
            //通过请求地址(最初始的请求地址)获取到参数列表
            Set<String> parameterNames = originalHttpUrl.queryParameterNames();
            TreeMap<String, Object> treeMap = new TreeMap<>();
            for (String key : parameterNames) {
                treeMap.put(key, originalHttpUrl.queryParameter(key));
            }
            String sign = SignRequestUtils.signRequest(original, treeMap, "utf-8");
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("sign", sign)
                    .build();
            Request request = original.newBuilder().url(url).build();
            return chain.proceed(request);


        } else if (TextUtils.equals(method, "POST")) {

            RequestBody requestBody = original.body();

            if (requestBody instanceof MultipartBody) {
                return chain.proceed(original);
            } else {
                TreeMap<String, Object> treeMap;
                Gson gson = new Gson();
                Buffer buffer = new Buffer();
                Objects.requireNonNull(requestBody).writeTo(buffer);
                String oldParamsJson = buffer.readUtf8();
                treeMap = gson.fromJson(oldParamsJson, TreeMap.class);  //原始参数
                String sign = SignRequestUtils.signRequest(original, treeMap, "utf-8");
//                treeMap.put("sign", sign);
//                String newJsonParams = gson.toJson(treeMap);  //装换成json字符串
//                Request request = original.newBuilder().post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), newJsonParams)).build();

                Request request = original.newBuilder().header("sign", sign).build();

                return chain.proceed(request);
            }

        }

        return chain.proceed(original);
    }
}
