package com.keylinks.android.bsdiff;

public class BsPatcher {

    static {
        System.loadLibrary("native-lib");
    }
    /**
     * 合成安装包
     * @param oldapk 旧版本安装包，如1.1.1版本
     * @param patch  差分包，Patch文件
     * @param output 合成后新版本apk的输出路径
     */
    public static native void bsPatch(String oldapk,String patch,String output);

}
