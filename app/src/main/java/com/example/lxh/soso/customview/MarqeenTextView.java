package com.example.lxh.soso.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lxh on 2015/12/4.
 */
public class MarqeenTextView extends TextView {

    public MarqeenTextView(Context context) {
        super(context);
    }

    public MarqeenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqeenTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
