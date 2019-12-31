package com.keylinls.android.personal;



import android.os.Bundle;
import android.util.Log;


import com.keylinks.android.api.ParameterManager;
import com.keylinks.android.arouter.annotation.ARouter;
import com.keylinks.android.arouter.annotation.Parameter;
import com.keylinks.android.base.BaseActivity;
import com.keylinks.android.user.BaseUser;
import com.keylinks.android.user.IUser;
import com.keylinks.android.utils.Cons;

@ARouter(path = "/personal/PersonalActivityMainActivity")
public class PersonalActivityMainActivity extends BaseActivity {

    @Parameter(name = "/app/getUserInfo")
    IUser iUser;

    @Parameter
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);


        Log.e(Cons.TAG, "personal/Personal_MainActivity");

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        Log.e(Cons.TAG, "接收参数值：" + username);

        BaseUser userInfo = iUser.getUserInfo();
        if (userInfo != null) {
            Log.e(Cons.TAG, userInfo.getName() + " / "
                    + userInfo.getAccount() + " / "
                    + userInfo.getPassword());
        }


    }
}
