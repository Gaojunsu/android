package com.keylinks.android.login;

import com.keylinks.android.base.BaseModel;
import com.keylinks.android.bean.UserInfo;

public class LoginModel extends BaseModel<LoginPresenter,LoginContract.Model> {
    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) throws Exception {
                    //处理登录网络请求
                      if (true){

                            //通知presenter结果
                            p.getContract().responseResult(new UserInfo("",""));
                      } else{
                            p.getContract().responseResult(null);
                      }


            }
        };
    }
}
