package com.example.lxh.soso.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.lxh.soso.utils.ScreenUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lxh on 2016/1/15.
 */
public class HorizontalScrollViewPager extends HorizontalScrollView {

    private int MINCURRENTINDEX = 1;
    private int MAXCURRENTINDEX;

    private LinearLayout mContainer;

    private Context mContext;

    private int mCurrentChildViewIndex;

    private int mTouchSlop;
    private Scroller mScroller;

    private float mActionDownX;
    private float mLastX;

    private int mScreenWidth;

    private ViewPageTransformer mTransformer;


    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }


    private void init(Context context) {
        mScreenWidth = ScreenUtils.getScreen(context)[0];
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(params);
        addView(mContainer);
        mTransformer = new ViewPageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position < 0) {
                    page.setScaleX(position + 1);
                    page.setScaleY(position + 1);
                } else if (position > 0) {
                    page.setScaleX(1 - position);
                    page.setScaleY(1 - position);
                }
            }
        };
    }


    public void setTransformer(ViewPageTransformer transformer) {
        mTransformer = transformer;

    }

    public void setData(List<View> mChildViews) {
        mContainer.removeAllViews();
        MAXCURRENTINDEX = mChildViews.size();
        mCurrentChildViewIndex = 1;
        LinearLayout.LayoutParams firstViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        firstViewParams.width = mScreenWidth / 4;
        View firstview = new View(mContext);
        firstview.setLayoutParams(firstViewParams);
        mContainer.addView(firstview);
        for (int i = 0; i < mChildViews.size(); i++) {
            View childview = mChildViews.get(i);
            if (childview != null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.width = mScreenWidth / 2;
                childview.setLayoutParams(layoutParams);
                mContainer.addView(childview);
            }
        }
        View lastview = new View(mContext);
        LinearLayout.LayoutParams lastViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lastViewParams.width = mScreenWidth / 4;
        lastview.setLayoutParams(lastViewParams);
        mContainer.addView(lastview);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float scrollx = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mActionDownX = x;
                mLastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                if ((dx > 0 && getScrollX() == 0) || (dx < 0 && getScrollX() == 2 * mScreenWidth)) { //view并没有移动
                    mActionDownX = x;
                }
                if (Math.abs(scrollx) >= mScreenWidth / 4) {
                    updateCurrentViewIndex(scrollx);
                }
                int offset = (int) scrollx % (mScreenWidth / 2);
                float position = offset * 1f / (mScreenWidth / 2);
//                Log.i("lxh", "offset" + offset);
//                Log.i("lxh", "position" + position);
//                callTransformer(position);
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
                scrollx = x - mActionDownX;
                if (Math.abs(scrollx) >= mScreenWidth / 4) { //切换view
                    updateCurrentViewIndex(scrollx);
                    int[] wh = new int[2];
                    mContainer.getChildAt(mCurrentChildViewIndex).getLocationOnScreen(wh);
                    int left = wh[0];
                    mScroller.startScroll(getScrollX(), 0, left - mScreenWidth / 4, 0);
                    invalidate();
                } else {//回滚到原来的位置
                    mScroller.startScroll(getScrollX(), 0, (int) scrollx, 0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void updateCurrentViewIndex(float dx) {
        if (dx > 0) { //向右滑动，焦点index减一
            mCurrentChildViewIndex--;
            if (Math.abs(dx) > mScreenWidth * 3 / 4) {//滑动的距离可以跳过一个view
                mCurrentChildViewIndex--;
            }
        } else {//向左滑动，焦点index加一
            mCurrentChildViewIndex++;
            if (Math.abs(dx) > mScreenWidth * 3 / 4) {//滑动的距离可以跳过一个view
                mCurrentChildViewIndex++;
            }
        }
        if (mCurrentChildViewIndex < MINCURRENTINDEX) {
            mCurrentChildViewIndex = MINCURRENTINDEX;
        }
        if (mCurrentChildViewIndex > MAXCURRENTINDEX) {
            mCurrentChildViewIndex = MAXCURRENTINDEX;
        }
        Log.i("lxh","mCurrentChildViewIndex:::"+mCurrentChildViewIndex);
    }

    private void callTransformer(float position) {
        //(0,-1) pre (-0.5 ,- 1)  cur ( 0,-0.5) next (0.5 ,0) 左滑
        //(0,1) pre (-0.5 ,0)  cur ( 0,0.5) next (0.5 ,1) //右滑

        if (mTransformer != null) {
            if (mCurrentChildViewIndex - 1 >= MINCURRENTINDEX) {
                View preView = mContainer.getChildAt(mCurrentChildViewIndex - 1);
                mTransformer.transformPage(preView, (position - 1) / 2);
            }
            View currentView = mContainer.getChildAt(mCurrentChildViewIndex);
            mTransformer.transformPage(currentView, position / 2);
            if (mCurrentChildViewIndex + 1 <= MAXCURRENTINDEX) {
                View preView = mContainer.getChildAt(mCurrentChildViewIndex + 1);
                mTransformer.transformPage(preView, (1 + position) / 2);
            }
        }

    }

    @Override
    public void scrollTo(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        if (x > 2 * mScreenWidth) {
            x = 2 * mScreenWidth;
        }
        if (x != getScrollX()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    public abstract class ViewPageTransformer {
        public abstract void transformPage(View page, float position);
    }

}
