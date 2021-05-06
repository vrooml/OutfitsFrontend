package com.example.outfits.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ScrollableViewPager extends ViewPager {

    private boolean isScroll = false;
    private boolean isAnimate = false;


    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScroll && super.onTouchEvent(ev);
    }


    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, isAnimate);
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }


    public boolean isAnimate() {
        return isAnimate;
    }

    public void setAnimate(boolean animate) {
        isAnimate = animate;
    }
}
