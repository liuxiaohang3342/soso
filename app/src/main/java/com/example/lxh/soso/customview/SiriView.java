package com.example.lxh.soso.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxh on 2017/3/7.
 */

public class SiriView extends View {

    private Path mRedPath;
    private Path mBluePath;
    private Path mGreenPath;
    private Paint mRedPaint;


    public SiriView(Context context) {
        super(context);
        init();
    }

    public SiriView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SiriView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRedPath = new Path();
        mBluePath = new Path();
        mGreenPath = new Path();
        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStrokeWidth(1);// 画线宽度
        mRedPaint.setStyle(Paint.Style.STROKE);//空心效果
        mRedPaint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        float period = 4.0f;// 区域内，正弦波的周期
        // 将绘图原点移动到区域中心
        int width = getWidth();
        int height = getHeight();
        float midWidth = width / 2.0f;
        float midHeight = height / 4.0f;
        canvas.translate(midWidth, midHeight);
        // 初始化线条
        mRedPath.moveTo(-midWidth, 0);

        // 计算线条
        for (float x = -midWidth; x < midWidth; x++) {
            double scaling = 1 - Math.pow(x / midWidth, 2);
            double sine = Math.sin(2 * Math.PI * period * (x / width));//计算该点上的正弦值
            float y = (float) (midHeight * sine * scaling) + midHeight;// 将正弦值限定到绘图区的高度上
            mRedPath.lineTo(x, y);
        }
        canvas.drawPath(mRedPath, mRedPaint);
    }
}
