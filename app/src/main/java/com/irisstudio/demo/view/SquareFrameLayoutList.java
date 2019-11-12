package com.irisstudio.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareFrameLayoutList extends FrameLayout {
    public SquareFrameLayoutList(Context context) {
        super(context);
    }

    public SquareFrameLayoutList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }
}
