package com.example.lxh.soso.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private MainFragment mMainFragment;

    @Override
    protected boolean setBottomViewVisible() {
        return true;
    }

    @Override
    protected View setBottomView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.main_activity_botton_view, root, false);
    }

    @Override
    protected void initView() {
        findViewById(R.id.home_main).setOnClickListener(this);
        findViewById(R.id.home_list).setOnClickListener(this);
        findViewById(R.id.home_scroll).setOnClickListener(this);
        findViewById(R.id.home_personal).setOnClickListener(this);
        if (mMainFragment == null) {
            mMainFragment = MainFragment.newInstance();
        }
        addFragment(R.id.app_content_container, mMainFragment, MainFragment.class.getName(), false);
    }

    @Override
    public void onClick(View v) {
        hideAllFragment();
        switch (v.getId()) {
            case R.id.home_main:
                if (mMainFragment == null) {
                    mMainFragment = MainFragment.newInstance();
                }
                addFragment(R.id.app_content_container, mMainFragment, MainFragment.class.getName(), false);
                break;
            case R.id.home_list:

                break;
            case R.id.home_scroll:
                break;
            case R.id.home_personal:
                break;
        }
    }

    private void hideAllFragment() {

        if (mMainFragment != null) {
            hideFragment(mMainFragment);
        }
    }
}
