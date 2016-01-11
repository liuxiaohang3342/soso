package com.example.lxh.soso.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.fragment.BaseFragment;
import com.example.lxh.soso.customview.RotateView;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.main_fragment_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RotateView rotateView = (RotateView) view.findViewById(R.id.rotate_view);
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
