package com.getbro.meetmeandroid.touch;
/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.getbro.meetmeandroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link View.OnTouchListener} that makes the list items in a {@link ListView}
 * dismissable. {@link ListView} is given special treatment because by default it handles touches
 * for its list items... i.e. it's in charge of drawing the pressed state (the list selector),
 * handling list item clicks, etc.
 *
 * <p>After creating the listener, the caller should also call
 * {@link ListView#setOnScrollListener(AbsListView.OnScrollListener)}, passing
 * in the scroll listener returned by {@link #makeScrollListener()}. If a scroll listener is
 * already assigned, the caller should still pass scroll changes through to this listener. This will
 * ensure that this {@link SwipeDismissListViewTouchListener} is paused during list view
 * scrolling.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>
 * SwipeDismissListViewTouchListener touchListener =
 *         new SwipeDismissListViewTouchListener(
 *                 listView,
 *                 new SwipeDismissListViewTouchListener.OnDismissCallback() {
 *                     public void onDismiss(ListView listView, int[] reverseSortedPositions) {
 *                         for (int position : reverseSortedPositions) {
 *                             adapter.remove(adapter.getItem(position));
 *                         }
 *                         adapter.notifyDataSetChanged();
 *                     }
 *                 });
 * listView.setOnTouchListener(touchListener);
 * listView.setOnScrollListener(touchListener.makeScrollListener());
 * </pre>
 *
 * <p>This class Requires API level 12 or later due to use of {@link
 * ViewPropertyAnimator}.</p>
 *
 */
public class SwipeDismissListViewTouchListener implements View.OnTouchListener {
    private static enum SwipeState {
        LEFT, RIGHT, SCROLL, NONE;
    }
    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    // Fixed properties
    private ListView mListView;
    private SwipeCallback mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private int mDismissAnimationRefCount = 0;
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private int mDownPosition;
    private View mDownView;
    private View mParentView;
    private boolean mPaused;
    private SwipeState mState = SwipeState.NONE;
    private int mChildIndex;


    /**
     * The distance when the touch cancels
     */
    private float mThresholdDistance = 250;

    /**
     * The callback interface used by {@link SwipeDismissListViewTouchListener} to inform its client
     * about a successful dismissal of one or more list item positions.
     */
    public interface SwipeCallback {
        void accept(int index);

        void maybe(int index);

        void decline(int index);

        void detail(int index);
    }

    /**
     * Constructs a new swipe-to-dismiss touch listener for the given list view.
     *
     * @param listView  The list view whose items should be dismissable.
     * @param callbacks The callback to trigger when the user has indicated that she would like to
     *                  dismiss one or more list items.
     */
    public SwipeDismissListViewTouchListener(ListView listView, SwipeCallback callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = listView.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mListView = listView;
        mCallbacks = callbacks;
    }

    /**
     * Enables or disables (pauses or resumes) watching for swipe-to-dismiss gestures.
     *
     * @param enabled Whether or not to watch for gestures.
     */
    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }

    /**
     * Returns an {@link AbsListView.OnScrollListener} to be added to the {@link
     * ListView} using {@link ListView#setOnScrollListener(AbsListView.OnScrollListener)}.
     * If a scroll listener is already assigned, the caller should still pass scroll changes through
     * to this listener. This will ensure that this {@link SwipeDismissListViewTouchListener} is
     * paused during list view scrolling.</p>
     *
     * @see SwipeDismissListViewTouchListener
     */
    public AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mViewWidth < 2) {
            mViewWidth = mListView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) {
                    return false;
                }

                mState = SwipeState.NONE;

                // Find the child view that was touched (perform a hit test)
                Rect rect = new Rect();
                int childCount = mListView.getChildCount();
                int[] listViewCoords = new int[2];
                mListView.getLocationOnScreen(listViewCoords);
                int x = (int) motionEvent.getRawX() - listViewCoords[0];
                int y = (int) motionEvent.getRawY() - listViewCoords[1];
                View child;
                for (int i = 0; i < childCount; i++) {
                    child = mListView.getChildAt(i);
                    child.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        mChildIndex = mListView.pointToPosition(x,y);
                        mDownView = child;
                        if (mDownView.findViewById(R.id.cell) != null) {
                            mDownView = mDownView.findViewById(R.id.cell);
                        }
                        mParentView = child.findViewById(R.id.text1);
                        mParentView = child.findViewById(R.id.background);
                        break;
                    }
                }

                if (mDownView != null) {
                    mDownX = motionEvent.getRawX();
                    mDownY = motionEvent.getRawY();
                    mDownPosition = mListView.getPositionForView(mDownView);
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null || mPaused) {
                    break;
                }

                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(1000);

                final float deltaX = motionEvent.getRawX() - mDownX;
                float deltaY = motionEvent.getRawY() - mDownY;
                if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) * 2) {
                    mState = SwipeState.LEFT;
                    mSwiping = true;
                    mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
                    mListView.requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (motionEvent.getActionIndex()
                                    << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                    mSwipeRefreshLayout.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                } else {
                    mState = SwipeState.SCROLL;
                }

                if (mSwiping) {
                    onSwipe(deltaX);
                    return true;
                }

                break;
            }

            case MotionEvent.ACTION_UP: {
                if (mVelocityTracker == null) {
                    break;
                }

                final float velocityX = Math.abs(mVelocityTracker.getXVelocity());
                final float deltaX = motionEvent.getRawX() - mDownX;
                if (deltaX < 0 && Math.abs(deltaX) > 100) {
                    mState = deltaX > 0 ? SwipeState.LEFT : SwipeState.RIGHT;
                    swipe(mChildIndex,false, Math.abs(deltaX) < mThresholdDistance);
                } else if (deltaX > 0 && velocityX > mMinFlingVelocity && velocityX < mMaxFlingVelocity) {
                    mState = deltaX > 0 ? SwipeState.LEFT : SwipeState.RIGHT;
                    swipe(mChildIndex,true, false);
                } else {
                    resetTranslation();
                }

                if (mDownView != null) {
                    Rect rect = new Rect();
                    int[] listViewCoords = new int[2];
                    mListView.getLocationOnScreen(listViewCoords);
                    int x = (int) motionEvent.getRawX() - listViewCoords[0];
                    int y = (int) motionEvent.getRawY() - listViewCoords[1];
                    View parent = (View) mDownView.getParent();
                    mDownView.getHitRect(rect);
                    rect.top += parent.getTop();
                    rect.bottom += parent.getTop();
                    if (rect.contains(x, y)) {
                        if (mState == SwipeState.NONE) {
                            mCallbacks.detail(mChildIndex);
                        }
                    }
                }

                resetSwipe();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                if (mVelocityTracker == null) {
                    break;
                }
                mState = SwipeState.SCROLL;

                if (mDownView != null && mSwiping) {
                    // cancel
                    resetTranslation();
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mDownX = 0;
                mDownY = 0;
                mDownView = null;
                mParentView = null;
                mDownPosition = ListView.INVALID_POSITION;
                mSwiping = false;
                break;
            }
        }
        return false;
    }

    private void onSwipe(float deltaX) {
        mDownView.setTranslationX(deltaX - mSwipingSlop);
        mParentView.setBackgroundColor(getColor(deltaX, mDownView.getWidth()*((float)3/4)));
    }

    private int getColor(float deltaX, float threshold) {
        int color = Color.GREEN;
        int from = Color.WHITE;
        if (deltaX < 0) {
            if (Math.abs(deltaX) > mThresholdDistance) {
                color = Color.RED;
            } else {
                color = 0xfffa5000;
            }
            from = Color.YELLOW;
        }

        float fraction = Math.abs(deltaX) / threshold;
        if (fraction > 1) {
            fraction = 1;
        }
        ArgbEvaluator eval = new ArgbEvaluator();
        return (Integer) eval.evaluate(fraction, from, color);
    }

    private void resetTranslation() {
        if (mDownView == null) {
            return;
        }
        mDownView.animate()
                .translationX(0)
                .alpha(1)
                .setDuration(mAnimationTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mParentView.setBackgroundColor(getColor(0, 1));
                    }
                });
    }

    private void resetSwipe() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mDownX = 0;
        mDownY = 0;
        mDownView = null;
        mDownPosition = ListView.INVALID_POSITION;
        mSwiping = false;
    }

    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public int compareTo(PendingDismissData other) {
            // Sort by descending position
            return other.position - position;
        }
    }

    private void swipe(final int index, final boolean left, final boolean half) {
        final View view = mDownView;
        final View parentView = mParentView;
        final ViewGroup.LayoutParams lp = parentView.getLayoutParams();
        if (!left && !half) {
            ValueAnimator heightAnim = ValueAnimator.ofInt(parentView.getHeight(), 1).setDuration(mAnimationTime);
            heightAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (left) {
                        mCallbacks.accept(index);
                    } else {
                        if (half) {
                            mCallbacks.maybe(index);
                        } else {
                            mCallbacks.decline(index);
                        }
                    }
                }
            });
            heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.height = (Integer) valueAnimator.getAnimatedValue();
                    parentView.setLayoutParams(lp);
                }
            });
            heightAnim.start();

            final float width = view.getWidth();
            view.animate()
                    .translationX(width)
                    .setDuration(mAnimationTime);
        } else {
            resetTranslation();
            if (left) {
                mCallbacks.accept(index);
            } else {
                mCallbacks.maybe(index);
            }
        }

    }


    public void setmSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }
}


