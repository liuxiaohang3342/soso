package com.example.lxh.soso.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lxh.soso.R;
import com.example.lxh.soso.base.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/1/6.
 */
public class ListViewFragment extends BaseFragment {

    private ArrayList<String> mDatas;

    public static ListViewFragment newInstance() {
        ListViewFragment listViewFragment = new ListViewFragment();
        return listViewFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.listview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        ListView listview = (ListView) view.findViewById(R.id.listview);
        listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Object getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getActivity());
                textView.setText(mDatas.get(position));
                return textView;
            }
        });
    }

    public void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("listview" + i * 5);
        }

    }


}
