package com.keylinks.android.application;

import android.app.Application;

import com.keylinks.android.SkinManager;


public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }

}
