package com.example.lxh.soso.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseActivity;
import com.example.lxh.soso.customview.HorizontalScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HorizontalViewPagerActivity extends BaseActivity {

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.activity_horizontal_viewpager, root, false);
    }

    @Override
    protected void initView() {
        HorizontalScrollViewPager scrollViewPager = (HorizontalScrollViewPager) findViewById(R.id.horizonal_viewpager);
        scrollViewPager.setData(initData());

    }

    private List<View> initData() {
        ArrayList<View> datas = new ArrayList<View>();
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.image1 + i);
            datas.add(imageView);
        }
        return datas;
    }
}
