package com.example.lxh.soso.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseActivity;
import com.example.lxh.soso.customview.HorizontalRankViewPager;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRankViewPagerActivity extends BaseActivity {

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.activity_rank_viewpager, root, false);
    }

    @Override
    protected void initView() {
        HorizontalRankViewPager scrollViewPager = (HorizontalRankViewPager) findViewById(R.id.horizonal_viewpager);
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
