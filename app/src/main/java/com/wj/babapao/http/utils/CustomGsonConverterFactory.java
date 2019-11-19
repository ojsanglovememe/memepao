package com.wj.babapao.http.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.http.utils
 * @class describe
 * @time 2018/11/20 0020 14:01
 * @change
 * @chang time
 * @class describe
 */

public class CustomGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private CustomGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return new CustomGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new CustomGsonResponseBodyConverter<>(gson, adapter);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        try {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new CustomGsonRequestBodyConverter<>(gson, adapter);
        } catch (Exception e) {
            return null;
        }

    }

}
