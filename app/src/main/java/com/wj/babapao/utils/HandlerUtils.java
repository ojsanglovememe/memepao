package com.wj.babapao.utils;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

/**
 * @ProjectName: TomatoLiveSDK2
 * @Package: com.tomatolive.library.utils
 * @ClassName: HandlerUtils
 * @Description: java类作用描述
 * @Author: wj
 * @CreateDate: 2019/3/29 17:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/29 17:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HandlerUtils {


    private HandlerThread ioHandlerThread;
    private Handler ioHandler;

    private HandlerUtils() {
    }

    private static class SingletonHolder {
        private static final HandlerUtils INSTANCE = new HandlerUtils();
    }

    public static HandlerUtils getInstance() {
        return HandlerUtils.SingletonHolder.INSTANCE;
    }

    public Handler startIOThread(String threadName, Handler.Callback callback) {
        if (ioHandlerThread == null || !ioHandlerThread.isAlive()) {
            ioHandlerThread = new HandlerThread(threadName);
            ioHandlerThread.start();
            ioHandler = new Handler(ioHandlerThread.getLooper(), callback);
            return ioHandler;
        }
        return null;
    }

    public void stopIOThread() {
        if (ioHandler != null) {
            ioHandler.removeCallbacksAndMessages(null);
            ioHandler = null;
        }
        if (ioHandlerThread != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    ioHandlerThread.quitSafely();
                } else {
                    ioHandlerThread.quit();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ioHandlerThread = null;
            }
        }

    }
}
