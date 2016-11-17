package com.rrtoyewx.andskinlibrary.listener;

import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Rrtoyewx on 2016/10/26.
 * 改变皮肤的监听
 */

public abstract class OnChangeSkinListener {

    private WeakReference<BaseSkinActivity> mBaseSkinActivity;

    public OnChangeSkinListener(BaseSkinActivity baseSkinActivity) {
        mBaseSkinActivity = new WeakReference<BaseSkinActivity>(baseSkinActivity);
    }

    public BaseSkinActivity getBindActivity() {
        return mBaseSkinActivity.get();
    }

    public abstract void onChangeSkinBegin();

    public abstract void onChangeSkinError();

    public abstract void onChangeSkinSuccess();
}
