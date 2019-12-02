package com.keylinks.android.utils;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

//工具类
public class Uiutils {

    private static Uiutils instance;
    //实际设备信息
    public static float displayMetricsWidth;
    public static float displayMetricsHeight;
    //状态栏
    public static float systemBarHeight;
    // ios  标准
    public static final float STANDARD_WIDTH=1080f;
    public static final float STANDARD_HEIGHT=1920f;


    //    activity
    public static Uiutils getInstance(Context context) {
        if (instance == null) {
            instance=new Uiutils(context);
        }
        return instance;
    }

    public static Uiutils notityInstance(Context context){
        instance=new Uiutils(context);
        return instance;
    }

    //    activity
    public static Uiutils getInstance() {
        if (instance == null) {
            throw new RuntimeException("UiUtil应该先调用含有构造方法进行初始化");
        }
        return instance;
    }




    private Uiutils(Context context) {

        //计算缩放系数
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //设备的真实值
        if (displayMetricsWidth==0.0f||displayMetricsHeight==0.0f)
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
             systemBarHeight=getSystemBarHeight(context);
             if (displayMetrics.widthPixels>displayMetrics.heightPixels){
                 //横屏
                 this.displayMetricsWidth=(float)(displayMetrics.heightPixels);
                 this.displayMetricsHeight=(float)(displayMetrics.widthPixels-systemBarHeight);


             }else{
                 //竖屏
                 this.displayMetricsWidth=(float)(displayMetrics.widthPixels);
                 this.displayMetricsHeight=(float)(displayMetrics.heightPixels-systemBarHeight);
             }



    }


    private int getSystemBarHeight(Context context){
        return getValue(context,"com.android.internal.R$dimen","system_bar_height",48);
    }


    //获取缩放值
    public float getHorizontalScaleValue(){
        return  ((float)(displayMetricsWidth)) / STANDARD_WIDTH;
    }
    public float getVerticalScaleValue(){
        return ((float)(displayMetricsHeight))/(STANDARD_HEIGHT-systemBarHeight);
    }

    //获取指定宽高的缩放值
    public int getWidth(int width) {
        return Math.round((float)width * this.displayMetricsWidth / STANDARD_WIDTH);
    }


    public int getHeight(int height) {
        return Math.round((float)height * this.displayMetricsHeight / (STANDARD_HEIGHT-systemBarHeight));
    }

    //获取状态栏的高度
    private int getValue(Context context, String dimeClass, String system_bar_height, int defualValue) {
        try {
            Class<?> clz=Class.forName(dimeClass);
            Object instance = clz.newInstance();
            Field field = clz.getField(system_bar_height);
            int id = Integer.parseInt(field.get(instance).toString());
            return context.getResources().getDimensionPixelSize(id);


        } catch ( Exception e) {
            e.printStackTrace();
        }
        return  defualValue;
    }






}
