package com.keylinks.android;

import android.os.Bundle;


public class PlayActivity extends SkinActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);





    }
    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
