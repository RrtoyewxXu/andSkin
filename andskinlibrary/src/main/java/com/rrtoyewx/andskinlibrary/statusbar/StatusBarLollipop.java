package com.rrtoyewx.andskinlibrary.statusbar;

import android.annotation.TargetApi;
import android.view.WindowManager;

import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.resource.Resource;

/**
 * Created by Rrtoyewx on 2016/11/17.
 * SDK.VERSION:>21
 */
@TargetApi(21)
public class StatusBarLollipop extends StatusBar {
    public StatusBarLollipop(BaseSkinActivity baseSkinActivity) {
        super(baseSkinActivity);
    }

    @Override
    void initEnvironment() {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public boolean findResource() {
        resetFindColor();
        mFindColor = getColor();

        return mFindColor != Resource.VALUE_ERROR_COLOR;
    }

    @Override
    public void changeSkin() {
        if (mFindColor != Resource.VALUE_ERROR_COLOR) {
            mWindow.setStatusBarColor(mFindColor);
        }
        resetFindColor();
    }
}
