package com.keylinks.android;

import android.os.Bundle;
import android.view.View;

import com.keylinks.android.api.ParameterManager;
import com.keylinks.android.api.RouterManager;
import com.keylinks.android.arouter.annotation.ARouter;

@ARouter(path = "/app/PlayActivity")
public class PlayActivity extends SkinActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);
        findViewById(R.id.jump_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance()
                        .build("/personal/PersonalActivityMainActivity")
                        .withString("username", "simon")
                        .navigation(getParent(), 163);
            }
        });



    }
    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
