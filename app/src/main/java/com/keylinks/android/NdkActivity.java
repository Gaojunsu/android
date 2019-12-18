package com.keylinks.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class NdkActivity extends AppCompatActivity {


    {
        System.loadLibrary("hello-jni");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
