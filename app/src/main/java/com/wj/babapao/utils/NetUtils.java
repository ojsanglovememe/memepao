package com.wj.babapao.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wj.babapao.base.BaseApp;

/**
 * @author wj
 * @name
 * @class
 * @class describe
 * @time 2018/6/5 9:42
 * @change
 * @chang time
 * @class describe
 */
public class NetUtils {

    /**
     * 没有网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    private NetUtils() {
    }

    public static boolean isNetworkAvailable() {

        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    /**
     * 获取网络状态
     */
    @SuppressLint("MissingPermission")
    public static NetworkInfo getActiveNetworkInfo() {

        ConnectivityManager sConnectivityManager = (ConnectivityManager) BaseApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return sConnectivityManager == null ? null : sConnectivityManager.getActiveNetworkInfo();
    }

    public static int getNetWorkState() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
//            LogManager.t("网络连接成功。。。");
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        }

//        else {
        //网络异常
//        LogManager.t("网络未连接。。。");
        return NETWORK_NONE;
//        }
//        return NETWORK_NONE;
    }

}
