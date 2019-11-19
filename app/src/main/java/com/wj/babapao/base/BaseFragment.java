package com.wj.babapao.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wj.babapao.events.BaseEvent;
import com.wj.babapao.ui.widgets.StateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author huxiangliang
 * @description: Fragment的基类
 * @date 2018/8/20
 */

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {
    protected String TAG;
    protected T mPresenter;
    protected Context mContext;
    protected Activity mActivity;

    protected StateView mStateView;
    protected ImmersionBar mImmersionBar;

    protected int pageNum = 1;
    protected boolean isDownRefresh = false;

    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;
    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isPrepared;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    public void onAttachToContext(Context context) {
        mContext = context;
        mActivity = getActivity() == null ? (Activity) context : getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mPresenter = createPresenter();
        getBundle(getArguments());
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (injectStateView(view) != null) {
            mStateView = StateView.inject(injectStateView(view));
        }
        initView(view, savedInstanceState);
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isLazyLoad()) {
            isPrepared = true;
            //只有Fragment onCreateView好了
            //另外这里调用一次lazyLoad(）
            lazyLoad();
        }
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {

        }

    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    public abstract @LayoutRes
    int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    public LifecycleProvider<FragmentEvent> getLifecycleProvider() {
        return this;
    }

    /**
     * 初始化控件
     */
    public abstract void initView(View view, @Nullable Bundle savedInstanceState);

    protected View injectStateView(View view) {
        return null;
    }

    /**
     * 设置listener的操作
     */
    public void initListener() {
    }

    /***
     * 得到Activity传进来的值
     * @param bundle
     */
    public void getBundle(Bundle bundle) {
    }

    public boolean isLazyLoad() {
        return false;
    }

    /**
     * 第二步
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoad()) {
            lazyLoad();
        }

        if (!isLazyLoaded) {
            return;
        }

        onFragmentVisible(isVisibleToUser);
    }

    /**
     * 调用懒加载
     * 第三步:在lazyLoad()方法中进行双重标记判断,通过后即可进行数据加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    /**
     * 第四步:定义抽象方法onLazyLoad(),具体加载数据的工作,交给子类去完成
     */
    @UiThread
    public void onLazyLoad() {

    }

    public void onFragmentVisible(boolean isVisible) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }

    @Subscribe
    public void onEventMainThread(BaseEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThreadSticky(BaseEvent event) {

    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> tarActivity, String key, int value) {
        Intent intent = new Intent(mContext, tarActivity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    public void showToast(@StringRes int msg) {
        ToastUtils.showShort(msg);
    }


}
