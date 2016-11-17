package com.rrtoyewx.andskinlibrary.base;

import android.app.Application;

import com.rrtoyewx.andskinlibrary.manager.SkinLoader;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinApplication:base application init something
 * SkinLoaderManager初始化
 */

public abstract class BaseSkinApplication extends Application {

    @Override
    public void onCreate() {
        beforeInit();
        SkinLoader.getDefault().init(this);
        afterInit();
        super.onCreate();
    }

    /**
     * SkinLoader ：初始SkinLoader之前
     * 注意：此时 DataManager,ResourceManager,GlobalManager此时并未初始化
     * 此时可以设定OnInitListener 来检测初始化成功还是失败。这里的成功和失败，是加载上一次插件皮肤资源包的成功和失败。
     */
    public abstract void beforeInit();

    /**
     * SkinLoader:初始化SkinLoader之后
     */
    public abstract void afterInit();
}
