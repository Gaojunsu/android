package com.keylinks.android;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.keylinks.android.annotation.ClickBehavior;
import com.keylinks.android.db.DBOperation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LoginActivity  extends AppCompatActivity implements DBOperation{

    private DBOperation db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db= (DBOperation) Proxy.newProxyInstance(DBOperation.class.getClassLoader(),new Class[]{DBOperation.class},new DBHander(this));
        jump();
    }

    @Override
    public void insert() {
        Log.i("proxy","insert");
    }

    @Override
    public void delete() {
        Log.i("proxy","delete");
    }

    @Override
    public void serach() {
        Log.i("proxy","serach");
    }

    @Override
    public void upload() {
        Log.i("proxy","upload");
    }

    @Override
    public void save() {
        Log.i("proxy","save");
    }


    class DBHander implements InvocationHandler {


        private DBOperation db;

        public DBHander(DBOperation db) {

            this.db=db;
        }

         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


            if (db!=null){
                save();
                return method.invoke(db,args);
            }

             return null;
         }
     }
    @ClickBehavior("面向切面编程")
     private void jump(){

        db.delete();

     }


}
