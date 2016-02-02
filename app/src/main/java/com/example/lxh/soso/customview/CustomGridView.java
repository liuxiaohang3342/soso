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
        if (getChildCount() > 0) { //走复用逻辑
            handConverView(data);
            return;
        }
        addView(createMainView(data.get(0)));
        for (int i = 1; i < data.size(); i++) {
            addView(createItemView(data.get(i)));
        }
    }

    /**
     * 在listview情况下，处理复用逻辑
     *
     * @param data
     */
    private void handConverView(ArrayList<String> data) {
        int childCount = getChildCount();
        int size = data.size();
        if (childCount > size) { //复用时候已有的view较多需要删除一部分
            for (int i = size; i < childCount; i++) {
                removeViewAt(i);
            }
        } else {//复用时候已有的view较少需要添加一部分
            for (int i = childCount; i < size; i++) {
                addView(createItemView(data.get(i)));
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int height;
        if ((childCount + 1) % mColumn == 0) {
            height = (childCount + 1) / mColumn * (mItemHeight + mItemSpace) - mItemSpace;
        } else {
            height = ((childCount + 1) / mColumn + 1) * (mItemHeight + mItemSpace) - mItemSpace;
        }
        int hightM = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, hightM); //高度需要重新测量，否则在listview等中显示不完全

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - (mColumn - 1) * mItemSpace;
        mItemWidth = width / mColumn;
        mSurplusWidth = width % mColumn;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int hight = mItemHeight;
            if (i == 0) {
                hight = mItemHeight * 2 + mItemSpace;
            }
            int wMeasureSpec = MeasureSpec.makeMeasureSpec(mItemWidth, MeasureSpec.EXACTLY);
            int hMeasureSpec = MeasureSpec.makeMeasureSpec(hight, MeasureSpec.EXACTLY);
            childView.measure(wMeasureSpec, hMeasureSpec);
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
