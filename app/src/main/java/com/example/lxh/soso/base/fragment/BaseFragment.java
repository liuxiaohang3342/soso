package com.example.lxh.soso.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lxh on 2016/1/6.
 */
public class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container);
        if (view != null) {
            ViewGroup viewParent = (ViewGroup) view.getParent();
            if (viewParent != null) {
                viewParent.removeView(view);
            }
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
