package com.rrtoyewx.andskinlibrary.base;

import android.app.Application;

import com.rrtoyewx.andskinlibrary.manager.GlobalManager;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinApplication:base application init something
 */

public class BaseSkinApplication extends Application {

    @Override
    public void onCreate() {
        GlobalManager.getDefault().init(this);
        super.onCreate();
    }
}
