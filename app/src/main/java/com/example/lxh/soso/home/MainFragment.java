package com.example.lxh.soso.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.fragment.BaseFragment;
import com.example.lxh.soso.stickylayout.RotateViewActivity;
import com.example.lxh.soso.stickylayout.StickyNavLayoutActivity;
import com.example.lxh.soso.stickylayout.StickyScrollViewActivity;

/**
 * Created by lxh on 2016/1/6.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.main_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.tv_rotate_view).setOnClickListener(this);
        view.findViewById(R.id.tv_stick_scroll).setOnClickListener(this);
        view.findViewById(R.id.tv_stick_nav_layout).setOnClickListener(this);
        view.findViewById(R.id.tv_3d_viewpager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rotate_view:
                openActivity(RotateViewActivity.class);
                break;
            case R.id.tv_stick_nav_layout:
                openActivity(StickyNavLayoutActivity.class);
                break;
            case R.id.tv_stick_scroll:
                openActivity(StickyScrollViewActivity.class);
                break;
            case R.id.tv_3d_viewpager:
                break;
        }
    }

    private void openActivity(Class clazz) {
        Intent intent = new Intent(getContext(), clazz);
        startActivity(intent);
    }
}
