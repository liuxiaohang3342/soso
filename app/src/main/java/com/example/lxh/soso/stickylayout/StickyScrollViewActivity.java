package com.example.lxh.soso.stickylayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;
import com.example.lxh.soso.customview.PagerSlidingTabStrip;
import com.example.lxh.soso.customview.StickyNavLayout;
import com.example.lxh.soso.home.ListViewFragment;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class StickyScrollViewActivity extends BaseFragmentActivity {

    private ArrayList<String> mTitles;

    private ArrayList<Fragment> mFragments;

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.personal_fragment_view, root, false);
    }

    @Override
    protected void initView() {
        initData();
        ImageView headerView = (ImageView) findViewById(R.id.header_view);
        headerView.setBackgroundResource(R.drawable.personal_recm_big_bg);
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.brand_tabPage);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

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
        });
        viewPager.setCurrentItem(0);
        slidingTabStrip.setViewPager(viewPager);
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
