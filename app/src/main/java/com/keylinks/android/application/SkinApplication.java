package com.keylinks.android.application;

import android.app.Application;


import com.keylinks.android.MainActivity;
import com.keylinks.android.RecordPathManager;
import com.keylinks.android.SkinManager;
import com.keylinls.android.order.OrderActivityMainActivity;
import com.keylinls.android.personal.PersonalActivityMainActivity;


public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
        // 如果项目有100个Activity，这种加法会不会太那个？
        RecordPathManager.joinGroup("app", "MainActivity", MainActivity.class);
        RecordPathManager.joinGroup("order", "OrderActivityMainAActivity", OrderActivityMainActivity.class);
        RecordPathManager.joinGroup("personal", "PersonalActivityMainActivity", PersonalActivityMainActivity.class);
  }

}
