package com.example.lxh.soso.rotate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseFragmentActivity;
import com.example.lxh.soso.customview.RotateView;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class RotateViewActivity extends BaseFragmentActivity {

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.main_fragment_view, root, false);
    }

    @Override
    protected void initView() {
        RotateView rotateView = (RotateView) findViewById(R.id.rotate_view);
        rotateView.setData(initDatas());
        rotateView.start();
    }

    private ArrayList<Bitmap> initDatas() {
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading_01));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading_02));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading_03));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading_04));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading_05));
        return bitmaps;
    }
}
