package com.example.lxh.soso.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseActivity;

/**
 * Created by lxh on 2016/1/15.
 */
public class ViewPagerActivity extends BaseActivity {

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.viewpager_layout, root, false);
    }

    @Override
    protected void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.animation_viewpager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(ViewPagerActivity.this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setImageResource(R.drawable.image1 + position);
                imageView.setLayoutParams(params);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (object instanceof ImageView) {
                    container.removeView((ImageView) object);
                }
            }
        });

    }
}
