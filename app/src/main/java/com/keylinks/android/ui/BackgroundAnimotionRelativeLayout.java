package com.keylinks.android.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.keylinks.android.R;

public class BackgroundAnimotionRelativeLayout extends RelativeLayout {
    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;
    public BackgroundAnimotionRelativeLayout(Context context) {
        super(context);
    }

    public BackgroundAnimotionRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackgroundAnimotionRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        Drawable backgroundDrawable = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];
        //前景与背景一致
        drawables[0]=backgroundDrawable;
        drawables[1]=backgroundDrawable;
        layerDrawable = new LayerDrawable(drawables);

        //动画
        objectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1.0f);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        //设置监听
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后 更新背景图
                layerDrawable.setDrawable(0,layerDrawable.getDrawable(1));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int foregroundAlpha= (int) ((float) animation.getAnimatedValue() * 255);
                layerDrawable.getDrawable(1).setAlpha(foregroundAlpha);
                setBackground(layerDrawable);
            }
        });

    }
    public void setForeground(Drawable drawable) {
        layerDrawable.setDrawable(1, drawable);
        objectAnimator.start();
    }

}
