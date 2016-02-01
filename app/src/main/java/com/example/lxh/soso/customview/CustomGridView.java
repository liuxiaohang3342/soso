package com.example.lxh.soso.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxh.soso.R;

import java.util.ArrayList;

/**
 * Created by lxh on 2016/2/1.
 */
public class CustomGridView extends ViewGroup {

    private Context mContext;

    private int mColumn;

    private int mItemHeight;

    private int mItemSpace;

    private int mItemBackGround;

    private int mItemWidth;

    private int mSurplusWidth;//除不尽的情况下剩余的像素点

    private int mItemTextSize;

    private int mItemTextColor;


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
        mItemBackGround = array.getColor(R.styleable.CustomGridView_itemBackground, Color.BLACK);
        array.recycle();
    }

    public void setData(ArrayList<String> data) {
//        addView(createMainView(data.get(0)));
        for (int i = 0; i < data.size(); i++) {
            int offset = 0;
            if (i % mColumn < mSurplusWidth) { //把剩余的像素添加到前几个view当中
                offset = 1;
            }
            addView(createItemView(data.get(i), offset));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - (mColumn - 1) * mItemSpace;
        mItemWidth = width / mColumn;
        mSurplusWidth = width % mColumn;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
//            if (i == 0) {
//                childView.layout(l, t, right, t + childView.getMeasuredHeight());
//                continue;
//            }

            int left = l + i % mColumn * (mItemSpace + mItemWidth);
            int right;
            if (i % mColumn <= mSurplusWidth) { //需要考虑多余的像素点
                left = left + i % mColumn;
                right = left + mItemWidth + 1;
            } else {
                left += mSurplusWidth;
                right = left + mItemWidth;
            }
            int top = t + i / mColumn * (mItemHeight + mItemSpace);
            childView.layout(left, top, right, top + mItemHeight);
        }
    }


    private View createItemView(String text, int offset) {
        TextView itemView = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mItemWidth + offset, mItemHeight);
        itemView.setLayoutParams(params);
        itemView.setGravity(Gravity.CENTER);
        itemView.setBackgroundColor(mItemBackGround);
        itemView.setTextSize(mItemTextSize);
        itemView.setTextColor(mItemTextColor);
        itemView.setText(text);
        return itemView;
    }

    private View createMainView(String text) {
        TextView itemView = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mItemWidth, mItemHeight * 2 + mItemSpace);
        itemView.setLayoutParams(params);
        itemView.setGravity(Gravity.CENTER);
        itemView.setBackgroundColor(mItemBackGround);
        itemView.setTextSize(mItemTextSize);
        itemView.setTextColor(mItemTextColor);
        itemView.setText(text);
        return itemView;
    }

}
