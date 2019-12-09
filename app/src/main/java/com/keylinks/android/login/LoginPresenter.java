package com.keylinks.android.login;

import com.keylinks.android.LoginActivity;
import com.keylinks.android.base.BaseModel;
import com.keylinks.android.base.BasePersenter;
import com.keylinks.android.bean.BaseEntity;
import com.keylinks.android.bean.UserInfo;

public class LoginPresenter extends BasePersenter<LoginActivity,LoginModel,LoginContract.Presenter> {

    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {

            @Override
            public void requestLogin(String name, String pwd){
                try {
                    //交给model处理
                    m.getContract().executeLogin(name,pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {

                //通知view层
                getView().getContract().handler(userInfo);
            }
        };
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }
}
