package com.wj.babapao.utils;

import android.util.Log;

import com.wj.babapao.BuildConfig;

public class LogManager {
    public static final String TAG_S = "debug_socket";
    public static final String TAG_P = "debug_push";
    public static final String TAG_I = "debug_info";
    static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG_T = "debug_test";

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG_I, msg + "");
        }
    }

    public static void s(String msg) {
        if (isDebug) {
            Log.i(TAG_S, msg + "");
        }
    }


    public static void p(String msg) {
        if (isDebug) {
            Log.i(TAG_P, msg + "");
        }
    }


    public static void t(String msg) {
        if (isDebug) {
            Log.i(TAG_T, msg + "");
        }
    }
}