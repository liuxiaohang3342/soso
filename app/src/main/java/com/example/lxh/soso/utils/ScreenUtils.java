package com.example.lxh.soso.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by lxh on 2016/1/15.
 */
public class ScreenUtils {

    /**
     * 屏幕尺寸
     *
     * @param context
     * @return
     */
    public static int[] getScreen(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels, dm.heightPixels};
    }


    /**
     * 手机震动
     *
     * @param context
     * @param milliseconds
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
}
