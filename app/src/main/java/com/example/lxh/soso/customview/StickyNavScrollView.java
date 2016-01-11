package com.example.lxh.soso.customview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.lxh.soso.R;

/**
 * @author Administrator
 * @ClassName: StickyNavScrollView
 * @Description: 此控件适合多个header和一个viewpager的格局。子控件frameLayoutHeader、pagerSlidingTabStrip
 * 、viewPager必须设置tag，设置的tag必须与values中tags.xml中对应。 此控件现在只对webview、pulltorefreshlistview、gridview 、scrollview做了兼容。
 * 为了兼容这些可滑动的view必须给fragment的根布局设置对应的tag，设置的tag必须与values中tags.xml中对应。
 * @date 2015-4-20 下午5:09:36
 */
public class StickyNavScrollView extends ScrollView {

    private View mHeaderView;

    private View mPagerTabStrip;

    private ViewPager mViewPager;

    private float mLastY;

    private int mTouchSlop;

    private boolean isTopHidden = false;

    private int mTopViewHeight;

    private ViewGroup mInnerScrollView;


    public StickyNavScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewWithTag(getResources().getString(R.string.frameLayoutHeader));
        mPagerTabStrip = findViewWithTag(getResources().getString(R.string.pagerSlidingTabStrip));
        View view = findViewWithTag(getResources().getString(R.string.viewPager));
        if (view == null || !(view instanceof ViewPager)) {
            throw new RuntimeException("id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {// OnGlobalLayoutListener

            // 是ViewTreeObserver的内部类，当一个视图树的布局发生改变时，可以被ViewTreeObserver监听到，这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知
            @Override
            public void onGlobalLayout() {
                if (mHeaderView != null) {
                    mTopViewHeight = mHeaderView.getMeasuredHeight();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        int height = 0;
        if (mPagerTabStrip != null) {
            height += mPagerTabStrip.getMeasuredHeight();
        }
        Log.i("lxh","getMeasuredHeight()"+getMeasuredHeight());
        params.height = getMeasuredHeight() - height;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                if (Math.abs(dy) > mTouchSlop && mInnerScrollView != null) {// 只拦截竖向的事件
                    isTopHidden = getScrollY() >= mTopViewHeight;
                    if (mInnerScrollView instanceof ListView) {
                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(0);
                        if (!isTopHidden || (lv != null && c != null && lv.getTop() == c.getTop() && isTopHidden && dy > 0)
                                || c == null && isTopHidden && dy > 0) {
                            return super.onInterceptTouchEvent(ev);
                        } else {
                            return false;
                        }
                    } else if (mInnerScrollView instanceof WebView) {
                        WebView webview = (WebView) mInnerScrollView;
                        if (!isTopHidden || (webview.getScrollY() == 0 && isTopHidden && dy > 0)) {
                            return super.onInterceptTouchEvent(ev);
                        } else {
                            return false;
                        }
                    } else if (mInnerScrollView instanceof GridView) {
                        GridView gridView = (GridView) mInnerScrollView;
                        View c = gridView.getChildAt(0);
                        if (!isTopHidden || (c != null && 0 == c.getTop() && isTopHidden && dy > 0) || c == null && isTopHidden
                                && dy > 0) {
                            return super.onInterceptTouchEvent(ev);
                        } else {
                            return false;
                        }

                    } else if (mInnerScrollView instanceof ScrollView) {
                        ScrollView scrollview = (ScrollView) mInnerScrollView;
                        if (!isTopHidden || (scrollview.getScrollY() == 0 && isTopHidden && dy > 0)) {
                            return super.onInterceptTouchEvent(ev);
                        } else {
                            return false;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = y;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    public void getCurrentScrollView() {
        int currentItem = mViewPager.getCurrentItem();
        PagerAdapter a = mViewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
            Fragment item = (Fragment) fadapter.instantiateItem(mViewPager, currentItem);
            if (item != null && item.getView() != null) {
                mInnerScrollView = (ViewGroup) item.getView().findViewWithTag(getResources().getString(R.string.stickyChildView));
            }
        }
    }
}
