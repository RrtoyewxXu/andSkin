package com.rrtoyewx.andskinlibrary.listener;

/**
 * Created by Rrtoyewx on 2016/11/17.
 * 初始化的监听
 * 宿主APK打开的时候，选择加载上次的保存的资源皮肤，然后加载外部的资源皮肤的时候由于外部资源皮肤APK的被移除造成初始化失败
 * 处理这块的策略为：加载默认的皮肤
 * 用户可以通过OnInitListener的来更改这样的策略
 */

public interface OnInitListener {
    void onInitError();

    void onInitSuccess();
}
