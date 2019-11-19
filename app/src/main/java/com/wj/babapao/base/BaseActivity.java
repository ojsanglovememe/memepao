package com.wj.babapao.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wj.babapao.R;
import com.wj.babapao.events.BaseEvent;
import com.wj.babapao.ui.receivers.NetworkChangeReceiver;
import com.wj.babapao.ui.widgets.StateView;
import com.wj.babapao.utils.ConstantUtils;
import com.wj.babapao.utils.SoftKeyboardUtils;
import com.wj.babapao.utils.language.MultiLanguageUtil;
import com.wj.babapao.openlib.titlebar.BGATitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;


/**
 * @author huxiangliang
 * @description: activity的基类
 * @date 2018/6/1
 */

public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements NetworkChangeReceiver.NetChangeListener {

    protected String TAG;
    protected Activity mActivity;
    protected Context mContext;

    protected T mPresenter;
    protected Bundle savedInstanceState;
    protected ImmersionBar mImmersionBar;
    protected StateView mStateView;
    private NetworkChangeReceiver networkChangeReceiver;

    public int pageNum = 1;
    private MyKickOutBroadCastReceiver myKickOutBroadCastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private MyTokenInvalidBroadCastReceiver myTokenInvalidBroadCastReceiver;
    protected static boolean hasRemindTraffic = false;//是否已经提示过

    private Disposable mCountdownDisposable;    //应用换至其他应用或后台，返回时倒计时
    private long maxSeconds = 10;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.getInstance().attachBaseContext(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //禁止截屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        mActivity = this;
        mContext = this;
        EventBus.getDefault().register(this);
        setContentView(this.getLayoutId());
        SoftKeyboardUtils.init(this);
        this.savedInstanceState = savedInstanceState;
        initNetworkChangeReceiver();
        mStateView = injectStateView() != null ? StateView.inject(injectStateView()) : StateView.inject(this);
        initImmersionBar();
        mPresenter = createPresenter();
        initView(savedInstanceState);
        initData();
        initListener();
//        registerHomeReceiver();

        registerDialogReceiver();
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract @LayoutRes
    int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    /**
     * @author huxiangliang
     * @time 2018/4/4  17:02
     * @describe 注入页面多状态布局（content，loading，empty，error）
     */
    protected View injectStateView() {
        return null;
    }

    public void initData() {
    }

    public void initListener() {
    }

    public void onAutoRefreshData() {
    }

    public boolean isAutoRefreshDataEnable() {
        return false;
    }

    protected void initImmersionBar() {
        View topView = findViewById(R.id.title_top_view);
        if (topView == null) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.statusBarDarkFont(true, ImmersionBar.isSupportStatusBarDarkFont() ? 0f : 0.2f)
                    .init();
        } else {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.transparentStatusBar()
                    .statusBarView(topView)
                    .statusBarDarkFont(true, ImmersionBar.isSupportStatusBarDarkFont() ? 0f : 0.2f)
                    .init();
        }

    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isAutoRefreshDataEnable()) {
            return;
        }
        onReleaseDisposable();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!isAutoRefreshDataEnable()) {
            return;
        }
        onReleaseDisposable();

        mCountdownDisposable = Flowable.intervalRange(0, maxSeconds + 1, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        LogManager.i("自动刷新数据倒计时结束");
                        onAutoRefreshData();
                    }
                }).subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        onReleaseDisposable();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        unRegisterDialogReceiver();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }

    public LifecycleProvider<ActivityEvent> getLifecycleProvider() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThreadSticky(BaseEvent event) {

    }

    @Override
    public void onNetChangeListener(int status) {

    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int resId) {
        ToastUtils.showShort(resId);
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void startActivityByLogin(Class<? extends Activity> tarActivity) {
//        if (!AppUtils.isLogin(mContext)) {
//            return;
//        }
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void setActivityTitle(@StringRes int titleResId) {
        setActivityTitle(getString(titleResId));
    }

    public void setActivityTitle(String title) {
        BGATitleBar titleBar = (BGATitleBar) findViewById(R.id.tb_prepare_title_bar);
        if (null == titleBar) {
            return;
        }
        titleBar.setLeftDrawable(R.drawable.fq_ic_title_back);
        titleBar.setTitleText(title);
        titleBar.setDelegate(new TitleBarOnClickListener());
    }

    public void setActivityTitle(@DrawableRes int backResId, String title) {
        BGATitleBar titleBar = (BGATitleBar) findViewById(R.id.tb_prepare_title_bar);
        if (null == titleBar) {
            return;
        }
        titleBar.setLeftDrawable(backResId);
        titleBar.setTitleText(title);
        titleBar.setDelegate(new TitleBarOnClickListener());
    }

    public void setActivityRightTitle(String title, String rightTitle, View.OnClickListener
            listener) {
        BGATitleBar titleBar = (BGATitleBar) findViewById(R.id.tb_prepare_title_bar);
        if (null == titleBar) {
            return;
        }
        titleBar.setLeftDrawable(R.drawable.fq_ic_title_back);
        titleBar.setTitleText(title);
        titleBar.setRightText(rightTitle);
        titleBar.setDelegate(new TitleBarOnClickListener(titleBar, listener));
    }

    public void setActivityRightIconTitle(String title, @DrawableRes int rightIcon, View.OnClickListener
            listener) {
        BGATitleBar titleBar = (BGATitleBar) findViewById(R.id.tb_prepare_title_bar);
        if (null == titleBar) {
            return;
        }
        titleBar.setLeftDrawable(R.drawable.fq_ic_title_back);
        titleBar.setTitleText(title);
        titleBar.setRightDrawable(rightIcon);
        titleBar.setDelegate(new TitleBarOnClickListener(titleBar, listener));
    }

    public void setActivityRightTitle(@StringRes int titleResId, @StringRes int rightTextResId,
                                      View.OnClickListener listener) {
        setActivityRightTitle(getString(titleResId), getString(rightTextResId), listener);
    }

    public void setActivityTitle(@StringRes int leftTitle, @StringRes int title, @StringRes int rightTitle, View.OnClickListener listener) {
        setActivityTitle(getString(leftTitle), getString(title), getString(rightTitle), listener);
    }

    public void setActivityTitle(String leftTitle, String title, String rightTitle, View.OnClickListener listener) {
        BGATitleBar titleBar = (BGATitleBar) findViewById(R.id.tb_prepare_title_bar);
        if (null == titleBar) {
            return;
        }
        titleBar.setLeftText(leftTitle);
        titleBar.setTitleText(title);
        titleBar.setRightText(rightTitle);
        titleBar.setDelegate(new TitleBarOnClickListener(titleBar, listener));
    }

    private class TitleBarOnClickListener implements BGATitleBar.Delegate {

        private BGATitleBar titleBar;
        private View.OnClickListener rightListener;
        private View.OnClickListener rightListenerSec;

        public TitleBarOnClickListener() {
        }

        public TitleBarOnClickListener(BGATitleBar titleBar, View.OnClickListener rightListener) {
            this.titleBar = titleBar;
            this.rightListener = rightListener;
        }

        public TitleBarOnClickListener(BGATitleBar titleBar, View.OnClickListener rightListener,
                                       View.OnClickListener rightListenerSec) {
            this.titleBar = titleBar;
            this.rightListener = rightListener;
            this.rightListenerSec = rightListenerSec;
        }

        @Override
        public void onClickLeftCtv() {
            onBackPressed();
        }

        @Override
        public void onClickTitleCtv() {

        }

        @Override
        public void onClickRightCtv() {
            if (this.rightListener == null || this.titleBar == null) {
                return;
            }
            this.rightListener.onClick(titleBar.getRightCtv());
        }

        @Override
        public void onClickRightSecondaryCtv() {
            if (this.rightListenerSec == null || this.titleBar == null) {
                return;
            }
            this.rightListenerSec.onClick(titleBar.getRightSecondaryCtv());
        }
    }


    /**
     * 被踢出的广播
     */
    private class MyKickOutBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.equals(action, ConstantUtils.LIVE_KICK_OUT_ACTION)) {
                //弹出kickout对话框
                showKickOutDialog();
            }
        }
    }


    /**
     * token失效的广播
     */
    private class MyTokenInvalidBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.equals(action, ConstantUtils.LIVE_TOKEN_INVALID_ACTION)) {
                //弹出token失效的广播对话框
                showTokenInvalidDialog();
            }
        }
    }

    private void showTokenInvalidDialog() {
//        if (AppUtils.isRunBackground(mContext)) {
//            return;
//        }
//        TokenInvalidDialog.newInstance().show(getSupportFragmentManager());
    }


    private void showKickOutDialog() {
//        if (AppUtils.isRunBackground(mContext)) {
//            return;
//        }
//        LiveKickOutDialog.newInstance().show(getSupportFragmentManager());
    }

    private void registerDialogReceiver() {
        registerKickDialogReceiver();
        registerTokenDialogReceiver();
    }

    private void unRegisterDialogReceiver() {
        unRegisterKickDialogReceiver();
        unRegisterTokenDialogReceiver();
    }

    /**
     * 注册踢出的监听
     */
    private void registerKickDialogReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter(ConstantUtils.LIVE_KICK_OUT_ACTION);
        myKickOutBroadCastReceiver = new MyKickOutBroadCastReceiver();
        localBroadcastManager.registerReceiver(myKickOutBroadCastReceiver, filter);
    }


    /**
     * 解除踢出广播监听
     */
    private void unRegisterKickDialogReceiver() {
        if (localBroadcastManager != null && myKickOutBroadCastReceiver != null) {
            localBroadcastManager.unregisterReceiver(myKickOutBroadCastReceiver);
        }
    }


    /**
     * 注册token的监听
     */
    private void registerTokenDialogReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter(ConstantUtils.LIVE_TOKEN_INVALID_ACTION);
        myTokenInvalidBroadCastReceiver = new MyTokenInvalidBroadCastReceiver();
        localBroadcastManager.registerReceiver(myTokenInvalidBroadCastReceiver, filter);
//        LogManager.i(this.toString()+"注册token失效广播");
    }


    /**
     * 解除token广播监听
     */
    private void unRegisterTokenDialogReceiver() {
        if (localBroadcastManager != null && myTokenInvalidBroadCastReceiver != null) {
            localBroadcastManager.unregisterReceiver(myTokenInvalidBroadCastReceiver);
//            LogManager.i(this.toString()+":解除token失效广播");
        }
    }

    private void initNetworkChangeReceiver() {
//        if ((mActivity instanceof TomatoLiveActivity) || (mActivity instanceof PrepareLiveActivity)) {
//            //实例化IntentFilter对象
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//            networkChangeReceiver = new NetworkChangeReceiver();
//            networkChangeReceiver.setOnNetChangeListener(this);
//            //注册广播接收
//            registerReceiver(networkChangeReceiver, filter);
//
//            return;
//        }

    }

    private void onReleaseDisposable() {
        if (mCountdownDisposable != null && !mCountdownDisposable.isDisposed()) {
            mCountdownDisposable.dispose();
        }
    }

//    /**
//     * 注册home键的监听
//     */
//    private void registerHomeReceiver() {
//        if (myHomeBroadCastReceiver == null) {
//            myHomeBroadCastReceiver = new MyHomeBroadCastReceiver();
//            IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            registerReceiver(myHomeBroadCastReceiver, filter);
//        }
//
//    }
//
//
//    /**
//     * 解除注册
//     */
//    private void unregisterHomeReceiver() {
//        if (myHomeBroadCastReceiver != null) {
//            unregisterReceiver(myHomeBroadCastReceiver);
//        }
//    }

//    private class MyHomeBroadCastReceiver extends BroadcastReceiver {
//        static public final String SYSTEM_DIALOG_REASON_KEY = "reason";
//        static public final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
//        static public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            //按下Home键会发送ACTION_CLOSE_SYSTEM_DIALOGS的广播
//            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
//                if (reason != null) {
//                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                        //关闭小窗口
//                        AppUtils.sendDismissOnlyAlertBroadcast();
////                        LogManager.i("baseActivity：短按home键 ");
//                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                        // RECENT_APPS键
//                        AppUtils.sendDismissOnlyAlertBroadcast();
////                        LogManager.i("baseActivity：RECENT_APPS键 ");
//                    }
//                }
//            }
//        }
//    }

}
