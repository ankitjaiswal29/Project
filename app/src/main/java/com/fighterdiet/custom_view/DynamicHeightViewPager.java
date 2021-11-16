package com.fighterdiet.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created and coded by Shubham Mathur (OwnageByte) 17/07/20 4:12 PM
 **/
public class DynamicHeightViewPager extends ViewPager {
    private View mCurrentView;
    private boolean includePaddingInHeight = false;

    public DynamicHeightViewPager(Context context) {
        super(context);
    }

    public DynamicHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCurrentView != null) {
            mCurrentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int height = Math.max(0, mCurrentView.getMeasuredHeight() - (includePaddingInHeight ? (getPaddingStart() + getPaddingEnd()) : 0));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void measureCurrentView(View currentView) {
        mCurrentView = currentView;
        requestLayout();
    }

    public void setIncludePaddingInHeight(boolean includePaddingInHeight) {
        this.includePaddingInHeight = includePaddingInHeight;
    }
}
