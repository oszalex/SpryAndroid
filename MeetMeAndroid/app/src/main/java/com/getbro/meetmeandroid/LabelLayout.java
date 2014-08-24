package com.getbro.meetmeandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 18/08/14.
 * @author Richard Plangger
 * @year 2014
 *
 * A linear layout that wraps around it's children if there is not enough space!
 */




public class LabelLayout extends LinearLayout {


    public LabelLayout(Context context) {
        super(context);
    }

    public LabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);

        int maxWidth = getMeasuredWidth();
        int maxHeight = -1;

        final int count = getChildCount();

        int L = l - this.getLeft(), T = t - this.getTop();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            LayoutParams lp =  (LinearLayout.LayoutParams) child.getLayoutParams();

            int R = L + lp.leftMargin + child.getMeasuredWidth();
            int B = T + lp.topMargin + child.getMeasuredHeight();

            final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }

            if (R > maxWidth) {
                T += maxHeight;
                maxHeight = childHeight;
                L = l - this.getLeft();

                R = L + child.getMeasuredWidth() + lp.leftMargin;
                B = T + child.getMeasuredHeight() + lp.topMargin;
            }

            child.layout(L + lp.leftMargin, T + lp.topMargin, R, B);

            L = R + lp.rightMargin;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int count = getChildCount();

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        int totalWidth = 0;
        int totalHeight = 0;
        int maxHeight = 0;
        int maxWidth = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp =  (LinearLayout.LayoutParams) child.getLayoutParams();

            final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            final int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            maxHeight = Math.max(childHeight, maxHeight);

            totalWidth += childWidth;
            if (totalWidth > width) {
                totalHeight += maxHeight;
                maxHeight = childHeight;
                totalWidth = childWidth;
            }
            maxWidth = Math.max(totalWidth, maxWidth);
        }

        if (maxHeight != 0) {
            totalHeight += maxHeight;
        }

        final int widthState = MeasureSpec.getMode(widthMeasureSpec);
        final int heightState = MeasureSpec.getMode(heightMeasureSpec);

        int heightSpec = heightMeasureSpec; // MeasureSpec.makeMeasureSpec(height,heightState);
        if (height > totalHeight) {
            heightSpec = MeasureSpec.makeMeasureSpec(totalHeight, heightState);
        }

        int widthSpec = widthMeasureSpec;
        if (widthState == MeasureSpec.UNSPECIFIED) {
            widthSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(widthSpec, heightSpec);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
