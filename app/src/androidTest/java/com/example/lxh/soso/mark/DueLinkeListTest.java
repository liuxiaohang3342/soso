package com.example.lxh.soso.mark;


import android.util.Log;

import org.junit.Test;


/**
 * Created by lxh on 2017/3/28.
 */
public class DueLinkeListTest {

    private static final String TAG = DueLinkeListTest.class.getName();

    @Test
    public void add() throws Exception {
        DueLinkeList<Integer> linkedList = new DueLinkeList<>();
        linkedList.add(3);
        linkedList.insert(5, 0);
        linkedList.insert(7, 1);
        Log.i(TAG, "linkedList" + linkedList.toString());
    }

}