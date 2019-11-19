package com.wj.babapao.http.interceptor;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.wj.babapao.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @class 通过拦截器全局添加Http请求头
 * @chang time
 * @class describe
 */
public class AddHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
//                .header("deviceType", "android")
//                .header("appId", UserInfoManager.getInstance().getAppId())
//                .header("appKey", TomatoLiveSDK.getSingleton().APP_KEY)
//                .header("sdkVersion", BuildConfig.VERSION_NAME)
//                .header("osVersion", DeviceUtils.getSDKVersionName())
//                .header("deviceName", DeviceUtils.getModel())
//                .header(TIME_STAMP_STR, String.valueOf(System.currentTimeMillis() / 1000))
//                .header(RANDOM_STR, StringUtils.getRandomString(16))
//                .header(DEVICE_ID, DeviceUtils.getAndroidID())
//                .header("token", UserInfoManager.getInstance().getToken())
//                .header("openId", UserInfoManager.getInstance().getAppOpenId())
//                .header("userId", UserInfoManager.getInstance().getUserId())
                .build();

        if (BuildConfig.DEBUG) {
            LogUtils.i(request.url());
        }

        return chain.proceed(request);
    }

    public static final String TIME_STAMP_STR = "timeStampStr";
    public static final String RANDOM_STR = "randomStr";
    public static final String DEVICE_ID = "deviceId";

}
