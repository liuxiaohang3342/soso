package com.example.lxh.soso.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;
import com.example.lxh.soso.scroll.ScrollViewActivity;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private MainFragment mMainFragment;

    private PersonalFragment mPersonalFragment;

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
                Intent intent = new Intent(this, ScrollViewActivity.class);
                startActivity(intent);
                break;
            case R.id.home_personal:
                if (mPersonalFragment == null) {
                    mPersonalFragment = PersonalFragment.newInstance();
                }
                addFragment(R.id.app_content_container, mPersonalFragment, PersonalFragment.class.getName(), false);
                break;
        }
    }

    private void hideAllFragment() {

        if (mMainFragment != null) {
            hideFragment(mMainFragment);
        }

        if (mPersonalFragment != null) {
            hideFragment(mPersonalFragment);
        }
    }
}
