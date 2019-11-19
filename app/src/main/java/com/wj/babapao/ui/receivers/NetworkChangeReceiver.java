package com.wj.babapao.ui.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.wj.babapao.utils.NetUtils;


/**
 * @author HuXiangLiang
 * @name
 * @class
 * @class describe
 * @time 2018/6/27 0027 11:36
 * @change
 * @chang time
 * @class describe
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetChangeListener listener = null;
    private int tempState = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtils.getNetWorkState();
            if (tempState != netWorkState) {
                tempState = netWorkState;
                if (listener != null) {
                    listener.onNetChangeListener(netWorkState);
                }
            }
        }
    }

    public void setOnNetChangeListener(NetChangeListener listener) {
        if (listener != null) this.listener = listener;
    }

    // 自定义接口
    public interface NetChangeListener {
        void onNetChangeListener(int status);
    }

}
