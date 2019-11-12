package com.irisstudio.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageViewList extends ImageView {
    public SquareImageViewList(Context context) {
        super(context);
    }

    public SquareImageViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);
    }
}
