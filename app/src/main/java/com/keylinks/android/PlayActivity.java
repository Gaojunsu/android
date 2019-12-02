package com.keylinks.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    public static final String PARAM_MUSIC_LIST = "PARAM_MUSIC_LIST";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }
}
