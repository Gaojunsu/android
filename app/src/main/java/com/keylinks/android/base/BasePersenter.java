package com.keylinks.android.base;

import java.lang.ref.WeakReference;

public abstract class BasePersenter<V extends BaseView,M extends BaseModel,CONTRACT> {

    protected M m;
    private WeakReference<V> vWeakReference;

    public BasePersenter() {
        this.m=getModel();
    }



    public void bindView(V v){
        vWeakReference=new WeakReference<>(v);
    }

    public void unBindView() {
    if (vWeakReference!=null){
        vWeakReference.clear();
        vWeakReference=null;
        System.gc();
    }
    }

    public V getView(){
        if (vWeakReference!=null){
           return vWeakReference.get();
        }
        return null;
    }
    //
    public abstract CONTRACT getContract();
    public abstract M getModel();

}
