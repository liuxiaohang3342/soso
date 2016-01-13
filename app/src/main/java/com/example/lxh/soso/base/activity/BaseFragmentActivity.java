package com.example.lxh.soso.base.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 *
 */
public class BaseFragmentActivity extends BaseActivity {

    /**
     * 添加fragment,如果fragment存在就show出来
     * <p>
     *
     * @param fragment
     * @param tag
     */
    protected void addFragment(int resId, Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(resId, fragment, tag);
        }
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 根据resId替换成fragment
     * <p>
     *
     * @param fragment
     * @param tag
     */
    protected void replaceFragment(int resId, Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(resId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 隐藏已经添加的fragment
     *
     * @param fragment
     */
    protected void hideFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }


}
