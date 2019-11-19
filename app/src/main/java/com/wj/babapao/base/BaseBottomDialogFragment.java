package com.wj.babapao.base;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.wj.babapao.R;

public abstract class BaseBottomDialogFragment extends BaseRxDialogFragment {

    private static final float DEFAULT_DIM = 0.7f;

    public View rootView;

    protected Dialog mDialog;

    protected Window mWindow;

    protected double maxHeightScale = 0.8;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fq_BottomDialog);
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
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if (getHeightScale() > 0 && mHeightPixels > 0) {
                height = (int) (mHeightPixels * getHeightScale());
            }
            if (null != mWindow) {
                mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
                mWindow.setDimAmount(getDimAmount());
                mWindow.setGravity(Gravity.BOTTOM);
            }
            mDialog.setCancelable(getCancelOutside());
            mDialog.setCanceledOnTouchOutside(getCancelOutside());
        }

        super.onStart();
    }

    protected float getDimAmount() {
        return DEFAULT_DIM;
    }

    protected boolean getCancelOutside() {
        return true;
    }

    protected String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected double getHeightScale() {
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
