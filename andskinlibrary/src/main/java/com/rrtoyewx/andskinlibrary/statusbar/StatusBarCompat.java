package com.rrtoyewx.andskinlibrary.statusbar;

import android.annotation.TargetApi;
import android.os.Build;

import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;

/**
 * Created by Rrtoyewx on 2016/11/17.
 * SDK.VERSION:>=19
 */
@TargetApi(19)
public class StatusBarCompat implements IChangeSkin {
    private final StatusBar mStatusBar;

    public StatusBarCompat(BaseSkinActivity baseSkinActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBar = new StatusBarLollipop(baseSkinActivity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar = new StatusBarKitKat(baseSkinActivity);
        } else {
            mStatusBar = null;
        }
    }

    @Override
    public void onChangeSkin() {
        if (mStatusBar != null) {
            mStatusBar.onChangeSkin();
        }
    }

    public int getCurrentStatusColor() {
        if (mStatusBar != null) {
            return mStatusBar.getColor();
        }
        return -1;
    }

    public void setStatusBarColorResName(String statusBarColorResName) {
        if (mStatusBar != null) {
            mStatusBar.setStatusBarColorName(statusBarColorResName);
        }
    }
}
