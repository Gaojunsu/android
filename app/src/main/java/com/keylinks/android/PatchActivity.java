package com.keylinks.android;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;

import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.SimpleCallback;

public class PatchActivity extends AppCompatActivity implements SimpleCallback {


    static {
        System.loadLibrary("bspatcher");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch);
        //当前版本大与6.0
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            //权限请求
            PermissionManager.Builder().permission(PermissionEnum.WRITE_EXTERNAL_STORAGE).callback(this).ask(this);
        }

        Button update = findViewById(R.id.button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


    }



    public void update() {
        // 从服务器下载 patch 到用户手机， SDCard 里面
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                // 获取旧版本路径（正在运行的apk路径）
//                String oldApk = getApplicationInfo().sourceDir;
                String patch = new File(Environment.getExternalStorageDirectory(), "patch").getAbsolutePath();


                String oldApk = new File(Environment.getExternalStorageDirectory(), "old.apk").getAbsolutePath();
                Log.i("EX",oldApk);
                String output = createNewApk().getAbsolutePath();

                if (!new File(patch).exists()) {
                    Log.i("EX",patch);
                    return null;
                }
                bsPatch(oldApk, patch, output);
                return new File(output);
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                // 已经合成了，调用该方法，安装新版本apk
                if (file != null) {
                    if (!file.exists()) return;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Uri fileUri = FileProvider.getUriForFile(PatchActivity.this, PatchActivity.this.getApplicationInfo().packageName + ".fileprovider", file);
                        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    }
                    PatchActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(PatchActivity.this, "差分包不存在！", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    /**
     * 创建合成后的新版本apk文件
     *
     * @return
     */
    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "bsdiff.apk");
        if (!newApk.exists()) {
            try {
                newApk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newApk;
    }



    @Override
    public void result(boolean allPermissionsGranted) {
                //未授权,退出当前页
                if (!allPermissionsGranted){
                    this.finish();
                }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);
    }



    public  native void bsPatch(String oldapk,String patch,String output);
}






