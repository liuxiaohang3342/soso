package com.example.lxh.soso.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxh on 2016/1/15.
 */
public class AnimationViewPager extends ViewPager {

    public AnimationViewPager(Context context) {
        super(context);
        init();
    }

    public AnimationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new ViewPageTransformer());
    }


    class ViewPageTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            page.setRotationY(45 * -position);
        }
    }

}
