package com.example.lxh.soso.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected boolean setBottomViewVisible() {
        return true;
    }

    @Override
    protected View setBottomView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.main_activity_botton_view, root, false);
    }

}
