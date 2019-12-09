package com.keylinks.android.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseView<P extends BasePersenter,CONTRACT> extends Activity {

    protected   P p;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弱引用
        p=getPresenter();
        //绑定

    }


    //让P层做什么需求
    public abstract CONTRACT getContract();

    //从子类中获取具体的契约
    public  abstract P getPresenter();

    //如果Presenter层出现异常,需要告诉view 层
    public  void error(Exception e){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.unBindView();
    }
}
