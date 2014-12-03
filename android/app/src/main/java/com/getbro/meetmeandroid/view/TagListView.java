package com.getbro.meetmeandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbro.meetmeandroid.R;
import com.getbro.meetmeandroid.suggestion.Suggestion;

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

public class TagListView extends LinearLayout {

    private OnTagClickListener listener;
    private OnClickListener childClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Tag tag = (Tag) v.getTag();
            if (listener != null) {
                listener.onTagClick(tag);
            }
        }
    };
    private List<Tag> tagList = new ArrayList<>();
    private List<View> convertViewList = new LinkedList<>();
    public TagListView(Context context) {
        super(context);
    }

    public TagListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setListener(OnTagClickListener listener) {
        this.listener = listener;
    }

    public void addTag(Tag tag) {
        int pos = tagList.size();
        tagList.add(tag);
        View view = null;
        if (convertViewList.size() > 0) {
            view = convertViewList.get(0);
        }
        view = getView(pos, view, tag);
        view.setOnClickListener(childClickListener);
        view.setTag(tag);

        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    public List<Tag> getTags() {
        return tagList;
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

            LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();

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

            LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();

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

    public static interface OnTagClickListener {
        public void onTagClick(Tag tag);
    }

    public static class Tag {
        private Suggestion object;
        private boolean activated;

        public Tag(Suggestion object) {
            this.object = object;
        }

        public Suggestion getObject() {
            return object;
        }

        public String getText() {
            String prefix = "";
            //FIXME: Prefixes auskommentiert, brauch ma die?
            /*if (object.getType() == SuggestionTypes.PLACE) {
                prefix = "@";
            } else if (object.getType() == SuggestionTypes.DATETIME) {
                prefix = "%";
            } else if (object.getType() == SuggestionTypes.TAG) {
                prefix = "#";
            }*/
            return prefix + object.getValue();
        }

        public int getColor() {
            if (isActivated()) {
                return R.color.gray;
            }
            return object.getType().getColorRes();
        }

        public boolean isActivated() {
            return activated;
        }

        public void setActivated(boolean activated) {
            this.activated = activated;
        }

    }
}
