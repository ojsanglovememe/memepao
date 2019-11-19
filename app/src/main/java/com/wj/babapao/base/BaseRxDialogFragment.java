package com.wj.babapao.base;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.wj.babapao.utils.SoftKeyboardUtils;

public abstract class BaseRxDialogFragment extends RxDialogFragment {

    protected Context mContext;
    protected Activity mDialogActivity;

    private DialogDismissListener mDialogDismissListener;

    protected int mWidthPixels;
    protected int mHeightPixels;

    public int pageNum = 1;

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
        mDialogActivity = getActivity() == null ? (Activity) context : getActivity();

        mWidthPixels = ScreenUtils.getScreenWidth();
        mHeightPixels = ScreenUtils.getScreenHeight();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle(getArguments());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSoftInputListener();
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {

        }
    }

    @Override
    public void dismiss() {
        SoftKeyboardUtils.hideDialogSoftKeyboard(getDialog());
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogDismissListener != null) {
            mDialogDismissListener.onDialogDismiss(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setOnShowListener(null);
            getDialog().setOnCancelListener(null);
            getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int resId) {
        ToastUtils.showShort(resId);
    }

    public void setOnDismissListener(DialogDismissListener dialogDismissListener) {
        this.mDialogDismissListener = dialogDismissListener;
    }

    public void getBundle(Bundle bundle) {
    }

    public String getArgumentsString(String key) {
        return getArgumentsString(key, "");
    }

    public String getArgumentsString(String key, String defaultValue) {
        if (getArguments() == null) {
            return defaultValue;
        }
        return getArguments().getString(key, defaultValue);
    }

    public int getArgumentsInt(String key) {
        return getArgumentsInt(key, 0);
    }

    public int getArgumentsInt(String key, int defaultValue) {
        if (getArguments() == null) {
            return defaultValue;
        }
        return getArguments().getInt(key, defaultValue);
    }

    public double getArgumentsDouble(String key) {
        return getArgumentsDouble(key, 0);
    }

    public double getArgumentsDouble(String key, double defaultValue) {
        if (getArguments() == null) {
            return defaultValue;
        }
        return getArguments().getDouble(key, defaultValue);
    }

    public float getArgumentsFloat(String key) {
        return getArgumentsFloat(key, 0);
    }

    public float getArgumentsFloat(String key, float defaultValue) {
        if (getArguments() == null) {
            return defaultValue;
        }
        return getArguments().getFloat(key, defaultValue);
    }

    public boolean getArgumentsBoolean(String key) {
        return getArgumentsBoolean(key, false);
    }

    public boolean getArgumentsBoolean(String key, boolean defaultValue) {
        if (getArguments() == null) {
            return defaultValue;
        }
        return getArguments().getBoolean(key, defaultValue);
    }

    public interface DialogDismissListener {
        void onDialogDismiss(BaseRxDialogFragment dialogFragment);
    }

    private void initSoftInputListener() {
        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        if (null != window) {
            window.getDecorView()
                    .setOnTouchListener((view, event) -> {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (getDialog().getCurrentFocus() != null
                                    && getDialog().getCurrentFocus().getWindowToken() != null) {
                                SoftKeyboardUtils.hideDialogSoftKeyboard(getDialog());
                            }
                        }
                        return false;
                    });
        }
    }
}
