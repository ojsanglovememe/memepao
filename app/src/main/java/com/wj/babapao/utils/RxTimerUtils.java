package com.wj.babapao.utils;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wj.babapao.http.SimpleRxObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
public class RxTimerUtils {
    private RxTimerUtils() {
    }

    private static class SingletonHolder {
        private static final RxTimerUtils INSTANCE = new RxTimerUtils();
    }

    public static RxTimerUtils getInstance() {
        return RxTimerUtils.SingletonHolder.INSTANCE;
    }

    public void timer(Context context, long delay, TimeUnit unit, RxTimerAction action) {
        if ((context instanceof RxAppCompatActivity)) {
            Observable.timer(delay, unit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new SimpleRxObserver<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            if (action != null) {
                                action.action(aLong);
                            }
                        }
                    });
            return;
        }

        Observable.timer(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxObserver<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (action != null) {
                            action.action(aLong);
                        }
                    }
                });

    }

    public void timerBindDestroy(LifecycleProvider<ActivityEvent> provider, long delay, TimeUnit unit, RxTimerAction action) {
        Observable.timer(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new SimpleRxObserver<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (action != null) {
                            action.action(aLong);
                        }
                    }
                });
    }

    public void timerBindDestroyFragment(LifecycleProvider<FragmentEvent> provider, long delay, TimeUnit unit, RxTimerAction action) {
        Observable.timer(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new SimpleRxObserver<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (action != null) {
                            action.action(aLong);
                        }
                    }
                });
    }

    public interface RxTimerAction {
        void action(long number);
    }
}
