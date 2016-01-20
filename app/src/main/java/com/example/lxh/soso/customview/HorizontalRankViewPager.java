package com.example.lxh.soso.customview;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.lxh.soso.R;
import com.example.lxh.soso.utils.ScreenUtils;

import java.util.List;

/**
 * Created by lxh on 2016/1/15.
 * <p/>
 * 人已经傻了了了，排序暂时没有好的思路
 */
public class HorizontalRankViewPager extends HorizontalScrollView implements View.OnLongClickListener {

    private int MINCURRENTINDEX = 1;
    private int MAXCURRENTINDEX;

    private LinearLayout mContainer;

    private Context mContext;

    private int mCurrentChildViewIndex = 1;

    private Scroller mScroller;

    private float mActionDownX;
    private float mMoveStartX; //记录移动距离超出mItemWidth之后的x值(下个移动周期的起始点)

    private int mScreenWidth;

    private ViewPageTransformer mTransformer;

    private int mItemWidth; //每一个view的宽度

    private int mMaxScrollX; //最大的偏移距离

    private List<View> mChildViews;

    private View mMoveView;

    private float mLastX;
    private float mLastY;

    public HorizontalRankViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }


    private void init(Context context) {
        setClipChildren(false);
        mScreenWidth = ScreenUtils.getScreen(context)[0];
        mItemWidth = mScreenWidth / 2;
        mScroller = new Scroller(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer = new LinearLayout(context);
        mContainer.setClipChildren(false);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(params);
        addView(mContainer);
        mTransformer = new ViewPageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position <= 0) {
                    page.setScaleX(1 + position / 2);
                    page.setScaleY(1 + position / 2);
                } else if (position > 0) {
                    page.setScaleX(1 - position / 2);
                    page.setScaleY(1 - position / 2);
                }
            }
        };
    }

    /**
     * 设置数据
     *
     * @param childViews
     */
    public void setData(List<View> childViews) {
        mChildViews = childViews;
        notifyDataChange();
    }

    /**
     * 集合数据发生变化
     */
    public void notifyDataChange() {
        createChildView(mChildViews);
        reValuation();
    }

    /**
     * 从新计算变量
     */
    private void reValuation() {
        MAXCURRENTINDEX = mChildViews.size();
        mMaxScrollX = (mContainer.getChildCount() - 1) * mItemWidth - mScreenWidth;
    }


    /**
     * 根据数据添加childview
     *
     * @param childViews
     */
    private void createChildView(List<View> childViews) {
        mContainer.removeAllViews();
        LinearLayout.LayoutParams firstViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        firstViewParams.width = mScreenWidth / 4;
        View firstview = new View(mContext);
        firstview.setLayoutParams(firstViewParams);
        mContainer.addView(firstview);
        for (int i = 0; i < childViews.size(); i++) {
            View childview = childViews.get(i);
            if (childview != null) {
                childview.setOnLongClickListener(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.width = mItemWidth;
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


    /**
     * 设置transformer
     *
     * @param transformer
     */
    public void setTransformer(ViewPageTransformer transformer) {
        mTransformer = transformer;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //重新layout之后需要初始化动画
        onScrollChanged(l, t, 0, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTransformer != null && mContainer != null) {
            final int scrollX = getScrollX();
            final int childCount = mContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = mContainer.getChildAt(i);
                final float transformPos = (float) (child.getLeft() - mScreenWidth / 4 - scrollX) / mItemWidth;
                mTransformer.transformPage(child, transformPos);
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mContainer.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float scrollx = 0;
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mActionDownX = x;
                mMoveStartX = x;
                mLastX = x;
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mMoveView != null) {
                    float dx = x - mLastX;
                    float dy = y - mLastY;
                    mMoveView.setTranslationX(mMoveView.getTranslationX() + dx);
                    mMoveView.setTranslationY(mMoveView.getTranslationY() + dy);
                    mLastY = y;
                    mLastX = x;
                    return true;
                }
                float dx = x - mMoveStartX;
                scrollx = x - mActionDownX;
                if ((dx > 0 && getScrollX() == 0) || (dx < 0 && getScrollX() == mMaxScrollX)) { //view并没有移动
                    mActionDownX = x;
                    mMoveStartX = x;
                    return super.onTouchEvent(ev);
                }
                if (Math.abs(dx) >= mItemWidth) { //移动距离超过view的宽度，需要更新角标
                    updateCurrentViewIndex(scrollx);
                    mMoveStartX = x;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mMoveView = null;
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_UP:
                if (mMoveView != null) {
                    if (Math.abs(mMoveView.getTranslationY()) > mItemWidth / 2) {
                        removeMoveView();
                    }
                    mMoveView = null;
                    return super.onTouchEvent(ev);
                }
                scrollx = x - mActionDownX;
                if (Math.abs(scrollx) >= mScreenWidth / 4) { //切换view
                    if (Math.abs(scrollx) < mItemWidth) {
                        if (scrollx > 0) {
                            mCurrentChildViewIndex--;
                        } else {
                            mCurrentChildViewIndex++;
                        }
                        if (mCurrentChildViewIndex < MINCURRENTINDEX) {
                            mCurrentChildViewIndex = MINCURRENTINDEX;
                        }
                        if (mCurrentChildViewIndex > MAXCURRENTINDEX) {
                            mCurrentChildViewIndex = MAXCURRENTINDEX;
                        }
                    }
                    int left = mContainer.getChildAt(mCurrentChildViewIndex).getLeft();
                    mScroller.startScroll(getScrollX(), 0, left - mScreenWidth / 4 - getScrollX(), 0);
                    invalidate();
                } else {//回滚到原来的位置
                    mScroller.startScroll(getScrollX(), 0, (int) scrollx, 0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 更新焦点item的脚标
     *
     * @param dx
     */
    private void updateCurrentViewIndex(float dx) {
        if (dx > 0) { //向右滑动，焦点index减一
            mCurrentChildViewIndex--;
            if (Math.abs(dx) >= mScreenWidth * 3 / 4) {//滑动的距离可以跳过一个view
                mCurrentChildViewIndex--;
            }
        } else {//向左滑动，焦点index加一
            mCurrentChildViewIndex++;
            if (Math.abs(dx) >= mScreenWidth * 3 / 4) {//滑动的距离可以跳过一个view
                mCurrentChildViewIndex++;
            }
        }
        if (mCurrentChildViewIndex < MINCURRENTINDEX) {
            mCurrentChildViewIndex = MINCURRENTINDEX;
        }
        if (mCurrentChildViewIndex > MAXCURRENTINDEX) {
            mCurrentChildViewIndex = MAXCURRENTINDEX;
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

    @Override
    public boolean onLongClick(View v) {
        if (mContainer != null) {
            mMoveView = v;
            ScreenUtils.vibrate(mContext, 100);
        }
        return false;
    }

    private void removeMoveView() {
        mMoveView.startAnimation(getOutAnimation(mMoveView));
        mContainer.removeView(mMoveView);
        mChildViews.remove(mMoveView);
        reValuation();
        invalidate();
    }

    private AnimationSet getOutAnimation(View view) {
        int[] screen = new int[2];
        view.getLocationOnScreen(screen);
        float toXDelta = mScreenWidth / 4 - screen[0];
        float toYDelta = screen[1] + view.getHeight();
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0);
        animationSet.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, toXDelta, 0, -toYDelta);
        animationSet.addAnimation(translateAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(500);
        return animationSet;
    }

    public abstract class ViewPageTransformer {
        public abstract void transformPage(View page, float position);
    }

}
