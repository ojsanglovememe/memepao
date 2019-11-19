package com.wj.babapao.base;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive
 * @class describe
 * @time 2018/9/9 0009 14:39
 * @change
 * @chang time
 * @class describe
 */

public class BaseApp extends MultiDexApplication {

    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}



