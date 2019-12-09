package com.keylinks.android.base;

public abstract class BaseModel<P extends BasePersenter,CONTRACT> {

    public P p;

    public BaseModel(P p) {this.p=p; }
    public abstract CONTRACT getContract();
}
