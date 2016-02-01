package com.example.lxh.soso.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lxh on 2016/1/21.
 *
 * 让画布滚动，所有的
 */
public class BarrageView extends TextView {

    private static final int ITEM_OFFSET = 20;

    private List<String> mDatas;

    private int mCurrentDataIndex;

    private int mFirstLineLastX;

    private Paint mPaint;

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarrageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.WHITE);
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDatas != null) {
            mFirstLineLastX = Math.max(mFirstLineLastX, getScrollX());
            float startX = 0;
            for (String data : mDatas) {
                canvas.drawText(data, startX, 60, mPaint);
                mFirstLineLastX += mPaint.measureText(data, 0, data.length());
                mFirstLineLastX += ITEM_OFFSET;
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scrollBy(5, 0);
            postDelayed(runnable, 100);
        }
    };

    public void startBarrage() {
        post(runnable);
    }
}
