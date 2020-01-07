package com.keylinks.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.keylinks.android.api.ParameterManager;
import com.keylinks.android.api.RouterManager;
import com.keylinks.android.arouter.annotation.ARouter;
import com.keylinks.android.http.RetrofitManager;
import com.keylinks.android.rxjava.RxJavaManager;
import com.keylinks.android.utils.PreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

@ARouter(path = "/app/TestActivity")
public class TestActivity extends SkinActivity {
    private String skinPath;
    private Button internet,internet1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);





        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        internet = findViewById(R.id.internet);
        internet1 = findViewById(R.id.internet1);

        internet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RetrofitManager.getInstance();

                Call<ResponseBody> index = RetrofitManager.getInstance().index();
                index.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                try {
                                    Log.w("Retrofit",response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.w("Retrofit",t.toString());
                    }
                });
            }
        });


        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<RetrofitManager.AndroidAPI.APIToken> hanb = RetrofitManager.getInstance().login("hanb", "12345");
                hanb.enqueue(new Callback<RetrofitManager.AndroidAPI.APIToken>() {
                    @Override
                    public void onResponse(Call<RetrofitManager.AndroidAPI.APIToken> call, retrofit2.Response<RetrofitManager.AndroidAPI.APIToken> response) {
                                if (response.isSuccessful()){
                                        Log.w("Retrofit",response.body().token);
                                }
                    }

                    @Override
                    public void onFailure(Call<RetrofitManager.AndroidAPI.APIToken> call, Throwable t) {
                        Log.w("Retrofit",t.toString());
                    }
                });

            }
        });


        findViewById(R.id.skinDynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skinDynamic();
            }
        });

        findViewById(R.id.skinDefault).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skinDefault();
            }
        });

        // File.separator含义：拼接 /
        skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "net163.skin";


        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance()
                        .build("/app/MainActivity")
                        .withResultString("call", "I'am comeback!")
                        .navigation(TestActivity.this);
            }
        });


        Log.w("Skin",skinPath);

        // 运行时权限申请（6.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }

        if (("net163").equals(PreferencesUtils.getString(this, "currentSkin"))) {
            skinDynamic(skinPath, R.color.skin_item_color);
        } else {
            defaultSkin(R.color.colorPrimary);
        }
    }


    public void skinDynamic() {
        // 真实项目中：需要先判断当前皮肤，避免重复操作！
        if (!("net163").equals(PreferencesUtils.getString(this, "currentSkin"))) {
            Log.e("netease >>> ", "-------------start-------------");
            long start = System.currentTimeMillis();

            skinDynamic(skinPath, R.color.skin_item_color);
            PreferencesUtils.putString(this, "currentSkin", "net163");

            long end = System.currentTimeMillis() - start;
            Log.e("netease >>> ", "换肤耗时（毫秒）：" + end);
            Log.e("netease >>> ", "-------------end---------------");
        }
    }


    public void skinDefault() {
        if (!("default").equals(PreferencesUtils.getString(this, "currentSkin"))) {
            Log.e("netease >>> ", "-------------start-------------");
            long start = System.currentTimeMillis();

            defaultSkin(R.color.colorPrimary);
            PreferencesUtils.putString(this, "currentSkin", "default");

            long end = System.currentTimeMillis() - start;
            Log.e("netease >>> ", "还原耗时（毫秒）：" + end);
            Log.e("netease >>> ", "-------------end---------------");
        }
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }



    private class TestTask  extends AsyncTask<Void,Response,Response>{


        @Override
        protected void onPreExecute() {
            Log.w("Retrofit","开始");
        }

        @Override
        protected Response doInBackground(Void... voids) {





            return null;
        }


        @Override
        protected void onPostExecute(Response result) {
               internet.setText(result.body().toString());
        }
    }

}
