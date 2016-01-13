package com.example.lxh.soso.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.fragment.BaseFragment;
import com.example.lxh.soso.customview.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class PersonalFragment extends BaseFragment {

    private ArrayList<String> mTitles;

    private ArrayList<Fragment> mFragments;

    public static PersonalFragment newInstance() {
        PersonalFragment personalFragment = new PersonalFragment();
        return personalFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.personal_fragment_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        ImageView headerView = (ImageView) view.findViewById(R.id.header_view);
        headerView.setBackgroundResource(R.drawable.personal_recm_big_bg);
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.brand_tabPage);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

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
