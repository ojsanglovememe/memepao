package com.wj.babapao.base;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.wj.babapao.R;

public abstract class BaseDialogFragment extends BaseRxDialogFragment {


    private static final float DEFAULT_DIM = 0.7f;

    public View rootView;

    protected Dialog mDialog;

    protected Window mWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fq_CenterDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mDialog = getDialog();
        mWindow = mDialog.getWindow();
        if (null != mWindow) {
            mWindow.requestFeature(Window.FEATURE_NO_TITLE);
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutRes(), container, false);
            initView(rootView);// 控件初始化
            initListener(rootView);// 控件初始化
        }

        return rootView;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    protected void initListener(View view) {
    }

    @Override
    public void onStart() {
        if (mDialog != null) {
            if (null != mWindow) {
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                if (getWidthScale() > 0) {
                    if (mWidthPixels > 0) {
                        width = (int) (mWidthPixels * getWidthScale());
                    }
                }

                if (getHeightScale() > 0) {
                    if (mHeightPixels > 0) {
                        height = (int) (mHeightPixels * getHeightScale());
                    }
                }

                mWindow.setLayout(width, height);
                mWindow.setDimAmount(getDimAmount());
            }
            mDialog.setCancelable(getCancelOutside());
            mDialog.setCanceledOnTouchOutside(getCancelOutside());
        }

        super.onStart();
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    public double getWidthScale() {
        return 0.7;
    }

    public double getHeightScale() {
        return 0;
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
