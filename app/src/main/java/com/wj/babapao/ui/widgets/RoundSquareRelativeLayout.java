package com.wj.babapao.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author HuXiangLiang
 */

public class RoundSquareRelativeLayout extends RoundRelativeLayout {

    public RoundSquareRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundSquareRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundSquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
