package com.wj.babapao.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class WrapContentHeightViewPager extends ViewPager {

	public WrapContentHeightViewPager(Context context) {
		super(context);
	}

	public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//		int height = 0;
//		//下面遍历所有child的高度
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec,
//					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height) //采用最大的view的高度。
//				height = h;
//		}
//
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//				MeasureSpec.EXACTLY);
//
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);


		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childSize = getChildCount();
		int maxHeight = 0;
		for (int i = 0; i < childSize; i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			if (child.getMeasuredHeight() > maxHeight) {
				maxHeight = child.getMeasuredHeight();
			}
		}

		if (maxHeight > 0) {
			setMeasuredDimension(getMeasuredWidth(), maxHeight);
		}
	}
}