package com.keylinks.android.utils;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewCalculateUtil {
    //对页面某个元素赋值
    public static void setViewLayoutParam(View view, int width, int height, int topMargin, int bottomMargin, int lefMargin, int rightMargin){

       RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams!=null){

            //判断布局
            if (width!=RelativeLayout.LayoutParams.MATCH_PARENT&&width!=RelativeLayout.LayoutParams.WRAP_CONTENT&&width!=RelativeLayout.LayoutParams.FILL_PARENT){
                layoutParams.width=Uiutils.getInstance().getWidth(width);
            }else{
                layoutParams.width=width;
            }

            if (height!=RelativeLayout.LayoutParams.MATCH_PARENT&&height!=RelativeLayout.LayoutParams.WRAP_CONTENT&&height!=RelativeLayout.LayoutParams.FILL_PARENT){
                layoutParams.width=Uiutils.getInstance().getHeight(height);
            }else{
                layoutParams.height=height;
            }

            layoutParams.topMargin = Uiutils.getInstance( ).getHeight(topMargin);
            layoutParams.bottomMargin = Uiutils.getInstance( ).getHeight(bottomMargin);
            layoutParams.leftMargin = Uiutils.getInstance( ).getWidth(lefMargin);
            layoutParams.rightMargin = Uiutils.getInstance( ).getWidth(rightMargin);
            view.setLayoutParams(layoutParams);


        }


    }

    public static void setTextSize(TextView view, int size)
    {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, Uiutils.getInstance().getHeight(size));
    }


    public static void setViewLinearLayoutParam(View view, int width, int height)
    {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT && width != RelativeLayout.LayoutParams.WRAP_CONTENT && width != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.width = Uiutils.getInstance( ).getWidth(width);
        }
        else
        {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT && height != RelativeLayout.LayoutParams.WRAP_CONTENT && height != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.height = Uiutils.getInstance( ).getHeight(height);
        }
        else
        {
            layoutParams.height = height;
        }

        view.setLayoutParams(layoutParams);
    }
    public static void setViewGroupLayoutParam(View view, int width, int height)
    {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT && width != RelativeLayout.LayoutParams.WRAP_CONTENT && width != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.width = Uiutils.getInstance( ).getWidth(width);
        }
        else
        {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT && height != RelativeLayout.LayoutParams.WRAP_CONTENT && height != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.height = Uiutils.getInstance( ).getHeight(height);
        }
        else
        {
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    public static void setViewLinearLayoutParam(View view, int width, int height, int topMargin, int bottomMargin, int lefMargin,
                                                int rightMargin)
    {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT && width != RelativeLayout.LayoutParams.WRAP_CONTENT && width != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.width = Uiutils.getInstance( ).getWidth(width);
        }
        else
        {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT && height != RelativeLayout.LayoutParams.WRAP_CONTENT && height != RelativeLayout.LayoutParams.FILL_PARENT)
        {
            layoutParams.height = Uiutils.getInstance( ).getHeight(height);
        }
        else
        {
            layoutParams.height = height;
        }

        layoutParams.topMargin = Uiutils.getInstance( ).getHeight(topMargin);
        layoutParams.bottomMargin = Uiutils.getInstance( ).getHeight(bottomMargin);
        layoutParams.leftMargin = Uiutils.getInstance( ).getWidth(lefMargin);
        layoutParams.rightMargin = Uiutils.getInstance( ).getWidth(rightMargin);
        view.setLayoutParams(layoutParams);
    }

}
