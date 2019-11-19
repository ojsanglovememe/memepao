package com.wj.babapao.utils;

import android.content.Context;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wj.babapao.http.SimpleRxObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.utils
 * @class describe
 * @time 2019/3/18 0018 10:28
 * @change
 * @chang time
 * @class describe
 */
public class RxViewUtils {
    private RxViewUtils() {
    }

    private static class SingletonHolder {
        private static final RxViewUtils INSTANCE = new RxViewUtils();
    }

    public static RxViewUtils getInstance() {
        return RxViewUtils.SingletonHolder.INSTANCE;
    }

    public void throttleFirst(View view, int milliSeconds, RxViewAction action) {
        throttleFirst(view, milliSeconds, TimeUnit.MILLISECONDS, action);
    }

    public void throttleFirst(View view, long windowDuration, TimeUnit unit, RxViewAction action) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof RxAppCompatActivity) {
            RxView.clicks(view)
                    .throttleFirst(windowDuration, unit)
                    .compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new SimpleRxObserver<Object>() {
                        @Override
                        public void accept(Object o) {
                            if (action != null) {
                                action.action(o);
                            }
                        }
                    });

            return;
        }

        RxView.clicks(view)
                .throttleFirst(windowDuration, unit)
                .subscribe(new SimpleRxObserver<Object>() {
                    @Override
                    public void accept(Object o) {
                        if (action != null) {
                            action.action(o);
                        }
                    }
                });
    }

    public interface RxViewAction {
        void action(Object o);
    }
}
