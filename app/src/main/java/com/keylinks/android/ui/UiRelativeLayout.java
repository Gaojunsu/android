package com.keylinks.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class UiRelativeLayout extends RelativeLayout {

    private boolean flag=true;

    public UiRelativeLayout(Context context) {
        super(context);
    }

    public UiRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UiRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (flag){
            flag=false;




        }



    }
}
