package com.wj.babapao.http;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.wj.babapao.http.exception.ApiException;
import com.wj.babapao.ui.dialogs.LoadingDialog;
import com.wj.babapao.ui.widgets.StateView;
import com.wj.babapao.utils.ConstantUtils;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 */
public class HttpRxObserver<T> implements Observer<T> {

    private ResultCallBack callBack;
    private WeakReference<Context> context;

    private StateView stateLayout;
    private LoadingDialog dialog = null;

    private boolean isShowLoadingLayout = true;
    private int progressType = 0;

    public HttpRxObserver(Context context, ResultCallBack<T> callBack) {
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
    }

    public HttpRxObserver(Context context, ResultCallBack<T> callBack, boolean isShowDialog) {
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        if (isShowDialog) {
            initProgressDialog();
        }
    }

    public HttpRxObserver(Context context, ResultCallBack<T> callBack, String message) {
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        initProgressDialog(message);
    }

    public HttpRxObserver(Context context, ResultCallBack<T> callBack, StateView stateLayout, boolean isShowLoadingLayout) {
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.stateLayout = stateLayout;
        this.progressType = 1;
        this.isShowLoadingLayout = isShowLoadingLayout;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        showLoadingView();
    }

    @Override
    public void onNext(@NonNull T t) {
        if (this.callBack != null) {
            this.callBack.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        try {

            ApiException apiException = (ApiException) e;

            if (this.callBack != null) {
                this.callBack.onError(apiException.getCode(), apiException.getMsg());
            }

            if (apiException.getCode() == ConstantUtils.TOKEN_INVALID) {     //token失效

                showStateLayoutContent();
                dismissProgressDialog();

                Context context = this.context.get();
                if (context == null) {
                    return;
                }

                if (context instanceof FragmentActivity) {
                    //TODO 可以区分具体的activity
                }
                return;
            }

            showErrorView();

            if (apiException.getCode() == 30000 || apiException.getCode() == 30001
                    || apiException.getCode() == 200023 || apiException.getCode() == 200002
                    || apiException.getCode() == ConstantUtils.NOBILITY_RECOMMEND_STATUS_ERROR_CODE
                    || apiException.getCode() == ConstantUtils.NOBILITY_RECOMMEND_USED_ERROR_CODE) {
                return;
            }

            if (!TextUtils.isEmpty(apiException.getMsg())) {
                ToastUtils.showShort(apiException.getMsg());
            }

        } catch (Exception ignored) {
            onComplete();
        }
    }

    @Override
    public void onComplete() {
        showContentView();
    }

    private void initProgressDialog() {
        Context context = this.context.get();
        if (context == null) {
            return;
        }
        if (dialog == null) {
            dialog = new LoadingDialog(context);
        }
    }

    private void initProgressDialog(String message) {
        Context context = this.context.get();
        if (context == null) {
            return;
        }
        if (dialog == null) {
            dialog = new LoadingDialog(context, message);
        }
    }

    private void showProgressDialog() {
        Context context = this.context.get();
        if (context == null) {
            return;
        }
        if (dialog == null) {
            return;
        }
        try {
            if (context instanceof Activity) {
                final Activity activity = (Activity) context;
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }

                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }

                return;
            }

            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }

        } catch (Exception e) {

        }

    }

    private void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        }

    }

    private void showStateLayoutLoading() {
        Context context = this.context.get();
        if (this.stateLayout == null || context == null) return;

        if (this.isShowLoadingLayout) {
            this.stateLayout.showLoading();
        }
    }

    private void showStateLayoutContent() {
        Context context = this.context.get();
        if (this.stateLayout == null || context == null) return;
        this.stateLayout.showContent();

    }

    private void showStateLayoutError() {
        Context context = this.context.get();
        if (this.stateLayout == null || context == null) return;
        this.stateLayout.showRetry();
    }

    private void showErrorView() {
        if (this.progressType == 0) {
            dismissProgressDialog();
        } else {
            showStateLayoutError();
        }
    }

    private void showLoadingView() {
        if (this.progressType == 0) {
            showProgressDialog();
            return;
        }
        showStateLayoutLoading();
    }

    private void showContentView() {
        if (this.progressType == 0) {
            dismissProgressDialog();
            return;
        }
        showStateLayoutContent();
    }

}
