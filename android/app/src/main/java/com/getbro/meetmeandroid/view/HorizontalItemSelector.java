package com.getbro.meetmeandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbro.meetmeandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rich on 20.11.14.
 */
public class HorizontalItemSelector extends HorizontalScrollView {

    public static interface SelectionCallback {
        void onSelection(int index, Object object);
    }

    public List<String> item = new ArrayList<>();
    private View loff;
    private View roff;
    private LinearLayout layout;
    private int xDistance = 0;
    private int index = 0;
    private boolean selectedOne = false;
    private SelectionCallback callback;

    public HorizontalItemSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalItemSelector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        loff = new View(getContext());
        roff = new View(getContext());
    }

    public void addAll(String ... args) {
        for (String str : args) {
            item.add(str);
        }

        fillView(item);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(xDistance - ev.getRawX()) > 40) {
                selectNearestItem();
            }
            int w_2 = getWidth()/2;
            if (ev.getX() < w_2) {
                selectNextBy(-1);
            } else if (ev.getX() > w_2) {
                selectNextBy(1);
            }
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            xDistance = (int) ev.getRawX();
        }
        return super.onTouchEvent(ev);
    }

    private void selectNextBy(int inc) {
        index += inc;
        if (index < 0) {
            index = 0;
        } else if (index >= item.size()) {
            index = item.size()-1;
        }

        selectItem(index, true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        loff.setLayoutParams(new LinearLayout.LayoutParams(w/2,h));
        roff.setLayoutParams(new LinearLayout.LayoutParams(w / 2, h));
    }

    private void fillView(List<String> item) {
        layout = (LinearLayout) findViewById(R.id.content_view);
        layout.removeAllViews();

        layout.addView(loff);

        for (String str : item) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.selection_cell, null);
            SelectionViewHolder holder = new SelectionViewHolder(view);
            view.setTag(holder);
            layout.addView(view);
            holder.setText(str);
        }

        layout.addView(roff);
    }

    public void selectItem(int index) {
        selectItem(index, true);
    }
    public void selectItem(int index, boolean animated) {
        if (item.size() == 0) {
            return;
        }
        View item = layout.getChildAt(index + 1);
        int w_2 = getWidth()/2;
        int l = item.getLeft();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) item.getLayoutParams();
        int iw_2 = item.getWidth()/2 + lp.leftMargin;

        int x = (l - w_2) + iw_2;
        if (animated) {
            smoothScrollTo(x, 0);
        } else {
            scrollTo(x, 0);
        }

        this.index = index;
    }

    private void selectNearestItem() {
        int scrollX = getScrollX();
        int w_2 = getWidth()/2;
        int index = -1;
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (scrollX >= (child.getLeft()-w_2) && scrollX <= (child.getRight()-w_2)) {
                index = i-1;
                break;
            }
        }

        if (index == -1) {
            index = 0;
        }

        selectItem(index, true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (!selectedOne) {
            selectItem(0);
        }
    }

    private static class SelectionViewHolder {
        private final View target;
        private final TextView textView;

        public SelectionViewHolder(View view) {
            this.target = view;
            this.textView = (TextView)view.findViewById(android.R.id.text1);
        }

        public void setText(String str) {
            textView.setText(str);
        }
    }

    public void setCallback(SelectionCallback callback) {
        this.callback = callback;
    }
}
