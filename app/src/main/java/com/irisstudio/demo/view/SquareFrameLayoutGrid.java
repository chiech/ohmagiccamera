package com.irisstudio.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareFrameLayoutGrid extends RelativeLayout {
    public SquareFrameLayoutGrid(Context context) {
        super(context);
    }

    public SquareFrameLayoutGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }
}
