package com.keylinks.android;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.skin.skin.SkinActivity;
import com.skin.skin.utils.PreferencesUtils;

public class PlayActivity extends SkinActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        Button skin = findViewById(R.id.skin);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        boolean isNight = PreferencesUtils.getBoolean(this, "isNight");

        if (isNight){
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

                switch (uiMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        PreferencesUtils.putBoolean(PlayActivity.this, "isNight", true);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        PreferencesUtils.putBoolean(PlayActivity.this, "isNight", false);
                        break;
                    default:
                        break;
                }
            }
        });
    }






    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
