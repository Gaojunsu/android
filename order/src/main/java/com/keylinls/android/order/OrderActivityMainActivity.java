package com.keylinls.android.order;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.keylinks.android.api.ParameterManager;
import com.keylinks.android.api.RouterManager;
import com.keylinks.android.arouter.annotation.ARouter;



@ARouter(path = "/order/OrderActivityMainAActivity")
public class OrderActivityMainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);
        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        findViewById(R.id.jump_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance()
                        .build("/app/MainActivity")
                        .withResultString("call", "I'am comeback!")
                        .navigation(getParent());
            }
        });

        findViewById(R.id.jump_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance()
                        .build("/personal/PersonalActivityMainActivity")
                        .withResultString("call", "I'am comeback!")
                        .navigation(getParent());
            }
        });

    }





}
