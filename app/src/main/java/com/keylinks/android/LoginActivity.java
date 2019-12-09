package com.keylinks.android;




import android.os.Bundle;
import android.support.annotation.Nullable;

import com.keylinks.android.base.BasePersenter;
import com.keylinks.android.base.BaseView;
import com.keylinks.android.bean.BaseEntity;
import com.keylinks.android.bean.UserInfo;
import com.keylinks.android.login.LoginContract;
import com.keylinks.android.login.LoginPresenter;


public class LoginActivity  extends BaseView<LoginPresenter, LoginContract.View> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    // 点击事件
    public void doLoginAction() {

        // 发起需求，让Presenter处理
            p.getContract().requestLogin("name", "password");

    }


    @Override
    public LoginContract.View getContract() {
        return new LoginContract.View<UserInfo>() {

            @Override
            public void handler(UserInfo userInfo) {

            }
        };
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
}
