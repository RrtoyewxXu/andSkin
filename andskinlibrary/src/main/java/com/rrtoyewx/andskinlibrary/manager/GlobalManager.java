package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * GlobalManager : global variables like package name,
 */

public class GlobalManager {
    private String mPackageName;
    private Context mApplicationContext;


    public static GlobalManager getDefault() {
        return GlobalManagerHolder.sGlobalManger;
    }

    private static class GlobalManagerHolder {
        private static GlobalManager sGlobalManger = new GlobalManager();
    }

    public void init(Context context) {
        SkinL.d("------------global manager init begin----");
        mApplicationContext = context;
        SkinL.d("application name :" + mApplicationContext);
        mPackageName = context.getPackageName();
        SkinL.d("package name :" + mPackageName);
        SkinL.d("------------global manager init finish----");
    }

    public String getPackageName() {
        return mPackageName;
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }
}
