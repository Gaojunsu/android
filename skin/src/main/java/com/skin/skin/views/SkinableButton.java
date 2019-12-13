package com.skin.skin.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.skin.skin.R;
import com.skin.skin.core.ViewsMatch;
import com.skin.skin.model.AttrsBean;

public class SkinableButton extends AppCompatButton implements ViewsMatch {

    private AttrsBean attrsBean;

    public SkinableButton(Context context) {
        this(context,null);
    }

    public SkinableButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public SkinableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean=new AttrsBean();
        //自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableButton, defStyleAttr, 0);
        attrsBean.saveViewResource(typedArray,R.styleable.SkinnableButton);
        //回收
        typedArray.recycle();

    }

    @Override
    public void skinnableView() {
        // 根据自定义属性，获取styleable中的background属性
        int key =R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background];
        // 根据styleable获取控件某属性的resourceId
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            // 兼容包转换
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            // 控件自带api，这里不用setBackgroundColor()因为在9.0测试不通过
            // setBackgroundDrawable本来过时了，但是兼容包重写了方法
            setBackgroundDrawable(drawable);
        }
        // 根据自定义属性，获取styleable中的textColor属性
        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            ColorStateList color = ContextCompat.getColorStateList(getContext(), textColorResourceId);
            setTextColor(color);
        }

    }
}
