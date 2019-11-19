package com.wj.babapao.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wj.babapao.R;


/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.ui.view.dialog
 * @class describe
 * @time 2019/5/10 0010 16:03
 * @change
 * @chang time
 * @class describe
 */
public class LoadingDialog extends Dialog {

    private Context mContext;
    private String tips;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        this.mContext = context;
    }

    public LoadingDialog(@NonNull Context context, String tips) {
        super(context, R.style.DialogStyle);
        this.tips = tips;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(mContext, R.layout.fq_dialog_loading, null);
        TextView tvTips = view.findViewById(R.id.tv_tips);
        if (!TextUtils.isEmpty(this.tips)) {
            tvTips.setText(tips);
        }

        Window window = this.getWindow();
        if (window != null) {
            Activity activity = this.getOwnerActivity();
            if (activity != null) {
                DisplayMetrics dm = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                window.setLayout((int) (dm.widthPixels * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.setDimAmount(0.7f);
            }
        }
        setContentView(view);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
