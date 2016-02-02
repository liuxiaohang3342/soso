package com.example.lxh.soso.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lxh.soso.R;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/2/1.
 */
public class CustomGridView extends LinearLayout {

    private Context mContext;

    private int mColumn = 1; //列数

    private int mItemHeight;

    private int mItemSpace;

    private int mItemBackGround = Color.TRANSPARENT;

    private int mItemWidth;

    private int mSurplusWidth;//除不尽的情况下剩余的像素点

    private int mItemTextSize = 16;

    private int mItemTextColor = Color.WHITE;


    public CustomGridView(Context context) {
        super(context);
        mContext = context;
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomGridView);
        mColumn = array.getInt(R.styleable.CustomGridView_column, 1);
        mItemHeight = array.getDimensionPixelOffset(R.styleable.CustomGridView_itemHight, 0);
        mItemSpace = array.getDimensionPixelOffset(R.styleable.CustomGridView_itemSpace, 0);
        mItemTextSize = array.getDimensionPixelOffset(R.styleable.CustomGridView_itemTextSize, 16);
        mItemTextColor = array.getColor(R.styleable.CustomGridView_itemTextColor, getResources().getColor(R.color.color_white));
        mItemBackGround = array.getColor(R.styleable.CustomGridView_itemBackground, getResources().getColor(R.color.home_bottom_button));
        array.recycle();
    }

    public void setData(ArrayList<String> data) {
        addView(createMainView(data.get(0)));
        for (int i = 0; i < data.size(); i++) {
            addView(createItemView(data.get(i)));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - (mColumn - 1) * mItemSpace;
        mItemWidth = width / mColumn;
        mSurplusWidth = width % mColumn;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int hight = mItemHeight;
            if (i == 0) {
                hight = mItemHeight * 2 + mItemSpace;
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, hight);
            childView.setLayoutParams(params);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int index = i;
            if (i >= mColumn) { //由于第一个view占据两个位置，第二行开始所有的index错位一
                index++;
            }
            int left = l + index % mColumn * (mItemSpace + mItemWidth);
            if (index % mColumn <= mSurplusWidth) { //需要考虑多余的像素点
                left = left + index % mColumn;
            } else {
                left += mSurplusWidth;
            }
            int top = t + index / mColumn * (mItemHeight + mItemSpace);
            childView.layout(left, top, left + childView.getMeasuredWidth(), top + childView.getMeasuredHeight());
        }
    }


    private View createItemView(String text) {
        TextView itemView = new TextView(mContext);
        itemView.setBackgroundColor(mItemBackGround);
        itemView.setTextSize(mItemTextSize);
        itemView.setGravity(Gravity.CENTER);
        itemView.setTextColor(mItemTextColor);
        itemView.setText(text);
        return itemView;
    }

    private View createMainView(String text) {
        TextView itemView = new TextView(mContext);
        itemView.setBackgroundColor(mItemBackGround);
        itemView.setTextSize(mItemTextSize);
        itemView.setTextColor(mItemTextColor);
        itemView.setGravity(Gravity.CENTER);
        itemView.setText(text);
        return itemView;
    }

}
