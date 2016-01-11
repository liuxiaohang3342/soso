package com.example.lxh.soso.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.lxh.soso.R;
import com.example.lxh.soso.customview.CustomTitleBar;

/**
 * Created by lxh on 2016/1/6.
 */
public class BaseActivity extends FragmentActivity {

    protected CustomTitleBar mCustomTitleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_layout);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        FrameLayout titleBar = (FrameLayout) findViewById(R.id.app_title_bar);
        if (setTitleBarVisible()) {
            titleBar.addView(setCustomTitleView(layoutInflater, titleBar));
        } else {
            titleBar.setVisibility(View.GONE);
        }

        FrameLayout contentontainer = (FrameLayout) findViewById(R.id.app_content_container);
        if (setContentViewVisible()) {
            View contentView = setContentView(layoutInflater, contentontainer);
            if (contentView != null) {
                contentontainer.addView(contentView);
            }
        } else {
            contentontainer.setVisibility(View.GONE);
        }

        FrameLayout bottomViewContainer = (FrameLayout) findViewById(R.id.app_bottom_view);
        if (setBottomViewVisible()) {
            View bottomView = setBottomView(layoutInflater, bottomViewContainer);
            if (bottomView != null) {
                bottomViewContainer.addView(bottomView);
            }
        } else {
            bottomViewContainer.setVisibility(View.GONE);
        }
        initView();
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 设置是否需要title,默认显示，不需要可以复写此方法返回false
     *
     * @return
     */
    protected boolean setTitleBarVisible() {
        return true;
    }

    /**
     * 设置是否需要contentview,默认显示，不需要可以复写此方法返回false
     *
     * @return
     */
    protected boolean setContentViewVisible() {
        return true;
    }

    /**
     * 设置是否需要底部view,默认不显示，不需要可以复写此方法返回false
     *
     * @return
     */
    protected boolean setBottomViewVisible() {
        return false;
    }

    /**
     * 需要自定义titlebar，覆盖此方法
     *
     * @return
     */
    protected View setCustomTitleView(LayoutInflater layoutInflater, ViewGroup root) {
        mCustomTitleBar = new CustomTitleBar(this);
        return mCustomTitleBar;
    }

    /**
     * contentview
     *
     * @return
     */
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return null;
    }

    /**
     * bottomview
     *
     * @return
     */
    protected View setBottomView(LayoutInflater layoutInflater, ViewGroup root) {
        return null;
    }
}
