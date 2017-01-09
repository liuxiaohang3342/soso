package com.example.lxh.soso.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.example.lxh.soso.R;

/**
 * Created by lxh on 2016/1/11.
 */
public class StickyNavLayout extends LinearLayout {

    public interface ChildScrollViewIsTopListener {
        boolean isTop();
    }

    private View mHeaderView;

    private View mPagerTabStrip;

    private ViewPager mViewPager;

    private int mTopViewHeight;

    private boolean isTopHidden;

    private float mLastY;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private boolean mDragging;

    private ChildScrollViewIsTopListener mListener;

    private float mLastScale;
    private float mMaxScale;
    private int mMaxHeaderViewHeight;

    private int mActivePointerId = -1;

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setChildScrollViewIsTopListener(ChildScrollViewIsTopListener listener) {
        this.mListener = listener;
    }

    public void setMaxHeaderViewHeight(int height) {
        mMaxHeaderViewHeight = height;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.sticknav_headerview_id);
        mPagerTabStrip = findViewById(R.id.sticknav_pagertab_id);
        View view = findViewById(R.id.sticknav_viewpager_id);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mHeaderView != null) {
            mTopViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        int height = 0;
        if (mPagerTabStrip != null) {
            height += mPagerTabStrip.getMeasuredHeight();
        }
        params.height = getMeasuredHeight() - height;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                mMaxScale = (getMeasuredHeight() / mTopViewHeight);
                mLastScale = (mHeaderView.getBottom() / mTopViewHeight);
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int index = ev.findPointerIndex(mActivePointerId);
                if (index != -1) {
                    float y = ev.getY(index);
                    float dy = y - mLastY;
                    if (Math.abs(dy) > mTouchSlop) {
                        mDragging = true;
                        if (mListener != null) {
                            if (!isTopHidden || (mListener.isTop() && isTopHidden && dy > 0)) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                mActivePointerId = event.getPointerId(0);
                mLastY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(mActivePointerId);
                if (index != -1) {
                    float y = event.getY(index);
                    float dy = y - mLastY;
                    if (!mDragging && Math.abs(dy) > mTouchSlop) {
                        mDragging = true;
                    }
                    if (mDragging) {
                        scrollBy(0, (int) -dy);
                        if (mHeaderView.getBottom() >= mTopViewHeight) {
                            ViewGroup.LayoutParams localLayoutParams = mHeaderView.getLayoutParams();
                            float f = ((dy + mHeaderView.getBottom()) / mTopViewHeight - this.mLastScale) / 2.0F + mLastScale;
                            if ((mLastScale <= 1.0f) && (f < mLastScale)) {
                                localLayoutParams.height = mTopViewHeight;
                                mHeaderView.setLayoutParams(localLayoutParams);
                                return super.onTouchEvent(event);
                            }
                            mLastScale = Math.min(Math.max(f, 1.0F), mMaxScale);
                            localLayoutParams.height = ((int) (mTopViewHeight * mLastScale));
                            if (localLayoutParams.height > getMeasuredHeight() || localLayoutParams.height > mMaxHeaderViewHeight) {
                                localLayoutParams.height = mMaxHeaderViewHeight;
                            }
                            mHeaderView.setLayoutParams(localLayoutParams);
                        }
                        mLastY = y;
                        return super.onTouchEvent(event);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mDragging) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocityY = (int) mVelocityTracker.getYVelocity();
                    if (Math.abs(velocityY) > mMinimumVelocity) {
                        mScroller.fling(0, getScrollY(), 0, -velocityY, 0, 0, 0, mTopViewHeight);
                        invalidate();
                    }
                    mVelocityTracker.clear();
                }
                reset();
                post(mRunnable);
                mDragging = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onSecondaryPointerUp(event);
                mLastY = event.getY(event
                        .findPointerIndex(mActivePointerId));
                break;
        }
        return super.onTouchEvent(event);
    }

    private void onSecondaryPointerUp(MotionEvent event) {
        int i = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        if (event.getPointerId(i) == this.mActivePointerId && i != 0)
            mLastY = event.getY(0);
            mActivePointerId = event.getPointerId(0);
            return;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            float bottom = mHeaderView.getBottom();
            float scale = bottom / mTopViewHeight;
            if (scale > 1f) {
                float f = scale - 0.02f;
                ViewGroup.LayoutParams localLayoutParams = mHeaderView.getLayoutParams();
                if (f > 1.0F) {
                    localLayoutParams.height = (int) (f * mTopViewHeight);
                    mHeaderView.setLayoutParams(localLayoutParams);
                    StickyNavLayout.this.post(this);
                } else {
                    localLayoutParams.height = mTopViewHeight;
                    mHeaderView.setLayoutParams(localLayoutParams);
                }
            }
        }
    };


    private void reset() {
        mMaxScale = -1.0F;
        mLastScale = -1.0F;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        isTopHidden = getScrollY() == mTopViewHeight;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
