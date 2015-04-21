package com.gospry.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gospry.R;
import com.gospry.suggestion.Suggestion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 18/08/14.
 *
 * @author Richard Plangger
 * @year 2014
 * <p/>
 * A linear layout that wraps around it's children if there is not enough space!
 */

public class WindowView extends LinearLayout {



    private List<Window> windowList = new ArrayList<>();
    private List<View> convertViewList = new LinkedList<>();

    public WindowView(Context context) {
        super(context);
    }

    public WindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    public void addWindow(Window tag) {
        int pos = tagList.size();
        tagList.add(tag);
        View view = null;
        if (convertViewList.size() > 0) {
            view = convertViewList.get(0);
        }
        view = getView(pos, view, tag);
        view.setOnClickListener(childClickListener);
        view.setTag(tag);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float dpi = getResources().getDisplayMetrics().density;
        lp.bottomMargin = lp.topMargin = lp.leftMargin = lp.rightMargin = (int) (5 * dpi);

        addView(view, lp);
    }

    private View getView(int pos, View view, Tag tag) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.tag_layout, null);

        view.setTag(tag);

        TextView tv = (TextView) view;
        tv.setText(tag.getText());
        tv.setBackgroundColor(getResources().getColor(tag.getColor()));

        return view;
    }

    public void removeTag(Tag tag) {
        int index = tagList.indexOf(tag);
        if (index == -1) {
            return;
        }
        tagList.remove(index);
        View view = getChildAt(index);
        removeViewAt(index);
        //convertViewList.add(view);
    }

    public void clearTags() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //convertViewList.add(child);
        }
        removeAllViews();
        tagList.clear();
    }

    public List<Window> getWindows() {
        return windowList;
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

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

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

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

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



    public static class Window {
        private List<Suggestion> suggested;
        private List<Suggestion> selected;
        private boolean isSelected;
        private String name;
        private Color color;
        //TODO add icon?
        private OnTagClickListener listener;

        public Window(List<Suggestion> suggested, String name, Color color) {
            this.suggested = suggested;
            this.name = name;
            this.color = color;
        }

        public List<Suggestion>  getSuggestions() {
            return suggested;
        }
        public List<Suggestion>  getSelected() {
            return selected;
        }

        public String getText() {
            return name;
        }

        public Color getColor() {
            return color;
        }

        public void select(Suggestion selectedItem) {
            suggested.remove(selectedItem);
            selected.add(selectedItem);
        }

        public void deselect(Suggestion deselectedItem) {
            suggested.add(deselectedItem);
            selected.remove(deselectedItem);
        }

        public boolean isSelected(){
            return !selected.isEmpty();
        }

        public static interface OnTagClickListener {
            public void onTagClick(Window tag);
        }
        public void setListener(OnTagClickListener listener) {
            this.listener = listener;
        }
        private OnClickListener childClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                TagListView.Tag tag = (Tag) v.getTag();
                if (listener != null) {
                    listener.onTagClick(tag);
                }
            }
        };
    }
}
