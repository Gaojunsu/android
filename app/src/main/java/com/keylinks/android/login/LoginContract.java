package com.keylinks.android.login;

import com.keylinks.android.bean.BaseEntity;

public interface LoginContract {

    interface Model{
        void executeLogin(String name,String pwd)throws Exception;
    }
    interface View<T extends BaseEntity>{
        void handler(T t);
    }
    interface Presenter<T extends BaseEntity>{
        void requestLogin(String name,String pwd);
        void responseResult(T t);
    }
}
