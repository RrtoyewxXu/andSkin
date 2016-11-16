package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * GlobalManager:暴漏给用户，但是只能获取这些信息，不能更改这些信息
 * 存储全局的信息
 * 1,当前宿主的APK的包名
 * 2,当前宿主的Application对象
 * 3,当前换肤PluginAPK的包名
 * 4,当前换肤PluginApk的后缀名
 * 5,当前换肤PluginApk的文件路径
 */

public class GlobalManager {
    private String mPackageName;
    private Context mApplicationContext;
    private String mPluginAPKPackageName;
    private String mPluginAPKPath;
    private String mResourceSuffix;

    public static GlobalManager getDefault() {
        return GlobalManagerHolder.sGlobalManger;
    }

    private static class GlobalManagerHolder {
        private static GlobalManager sGlobalManger = new GlobalManager();
    }

    public void init(Context context, String pluginAPKPackagerName, String pluginAPKPath, String pluginAPKSuffix) {
        SkinL.d("------------global manager init begin----");
        mApplicationContext = context;
        SkinL.d("application name :" + mApplicationContext);

        mPackageName = context.getPackageName();
        SkinL.d("package name :" + mPackageName);

        mPluginAPKPackageName = pluginAPKPackagerName;
        SkinL.d("plugin apk package name :" + mPluginAPKPackageName);

        mPluginAPKPath = pluginAPKPath;
        SkinL.d("plugin apk path :" + mPluginAPKPath);

        mResourceSuffix = pluginAPKSuffix;
        SkinL.d("plugin apk suffix:" + mResourceSuffix);

        SkinL.d("------------global manager init finish----");
    }

    public String getPackageName() {
        return mPackageName;
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public String getPluginAPKPackageName() {
        return mPluginAPKPackageName;
    }

    public String getPluginAPKPath() {
        return mPluginAPKPath;
    }

    public String getResourceSuffix() {
        return mResourceSuffix;
    }

    void flushPluginInfos(String pluginPackageName, String pluginPath, String resourceSuffix) {
        this.mPluginAPKPackageName = pluginPackageName;
        this.mPluginAPKPath = pluginPath;
        this.mResourceSuffix = resourceSuffix;
    }

}
