package com.example.lxh.soso.scroll;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseActivity;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;
import com.example.lxh.soso.customview.PagerSlidingTabStrip;
import com.example.lxh.soso.customview.StickyNavLayout;
import com.example.lxh.soso.customview.StickyNavScrollView;
import com.example.lxh.soso.home.ListViewFragment;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class ScrollViewActivity extends BaseFragmentActivity {

    private ArrayList<String> mTitles;

    private ArrayList<Fragment> mFragments;

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.scroll_activity_view, root, false);
    }

    @Override
    protected void initView() {
        initData();
        ImageView headerView = (ImageView) findViewById(R.id.sticknav_headerview_id);
        headerView.setImageResource(R.drawable.personal_recm_big_bg);
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.sticknav_pagertab_id);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.sticknav_viewpager_id);
        final FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        slidingTabStrip.setViewPager(viewPager);
        StickyNavLayout stickyNavLayout = (StickyNavLayout) findViewById(R.id.sticknavlayout);
        stickyNavLayout.setMaxHeaderViewHeight(720);
        stickyNavLayout.setChildScrollViewIsTopListener(new StickyNavLayout.ChildScrollViewIsTopListener() {
            @Override
            public boolean isTop() {
                Fragment item = (Fragment) pagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
                if (item != null && item.getView() != null) {
                    ListView listView = (ListView) item.getView().findViewWithTag(getResources().getString(R.string.stickyChildView));
                    if (listView != null) {
                        View c = listView.getChildAt(0);
                        if (c != null && c.getTop() == listView.getTop()) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void initData() {
        mTitles = new ArrayList<String>();
        mTitles.add("主页");
        mTitles.add("主页");
        mTitles.add("主页");
        mTitles.add("主页");
        mFragments = new ArrayList<Fragment>();
        mFragments.add(ListViewFragment.newInstance());
        mFragments.add(ListViewFragment.newInstance());
        mFragments.add(ListViewFragment.newInstance());
        mFragments.add(ListViewFragment.newInstance());
    }

}
