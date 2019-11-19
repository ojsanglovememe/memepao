package com.wj.babapao.ui.widgets;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.wj.babapao.R;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.ui.view.widget
 * @class describe
 * @time 2019/4/4 0004 17:19
 * @change
 * @chang time
 * @class describe
 */
public class LoadingView extends AppCompatImageView {

    private AnimationDrawable frameAnimation;//创建帧动画的对象

    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fq_live_loading_animation));
        this.frameAnimation = (AnimationDrawable) this.getDrawable();
    }

    public void showLoading() {
        this.post(() -> {
            if (frameAnimation != null) {
                frameAnimation.start();
            }
        });
    }

    public void stopLoading() {
        if (this.frameAnimation != null && this.frameAnimation.isRunning()) {
            frameAnimation.stop();
        }
    }

    public void release() {
        if (frameAnimation != null && frameAnimation.isRunning()) {
            frameAnimation.stop();
        }
        frameAnimation = null;
        this.setImageDrawable(null);
    }
}
