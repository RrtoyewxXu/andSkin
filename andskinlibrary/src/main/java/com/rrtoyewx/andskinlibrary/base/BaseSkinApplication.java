package com.rrtoyewx.andskinlibrary.base;

import android.app.Application;

import com.rrtoyewx.andskinlibrary.manager.SkinLoaderManager;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinApplication:base application init something
 * SkinLoaderManager初始化
 */

public class BaseSkinApplication extends Application {

    @Override
    public void onCreate() {
        SkinLoaderManager.getDefault().init(this);
        super.onCreate();
    }
}
