package com.example.lxh.soso.gridview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.activity.BaseActivity;
import com.example.lxh.soso.customview.CustomGridView;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/2/1.
 */
public class GridViewActivity extends BaseActivity {

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup root) {
        return layoutInflater.inflate(R.layout.gridview_layout, root, false);
    }

    @Override
    protected void initView() {
        ArrayList<String> data = new ArrayList<>();
        int i = 0;
        while (i < 20) {
            data.add("标签" + i);
            i++;
        }
        CustomGridView gridView = (CustomGridView) findViewById(R.id.custom_gridview);
        gridView.setData(data);
    }
}
