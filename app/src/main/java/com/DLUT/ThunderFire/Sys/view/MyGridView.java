package com.DLUT.ThunderFire.Sys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView  extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(getMeasuredWidth()*0.90),getMeasuredHeight());
    }
}
