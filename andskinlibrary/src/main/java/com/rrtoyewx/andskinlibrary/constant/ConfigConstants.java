package com.rrtoyewx.andskinlibrary.constant;

import com.rrtoyewx.andskinlibrary.manager.GlobalManager;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class ConfigConstants {
    //-------------------------------
    public static final String SKIN_NAMES_SPACE = "http://schemas.android.com/android/andSkin";
    public static final String ANDROID_NAMES_SPACE = "http://schemas.android.com/apk/res/android";
    public static final String ATTR_SKIN_ENABLE = "enable";
    public static final String ATTR_SKIN_LIST = "attrs";

    //--------------------------shared preferences name---------------------
    public static final String NAME_SHARE_PREFERENCES = "and_skin";

    //--------------------------- external plugin path----------------------
    public static final String PATH_EXTERNAL_PLUGIN = GlobalManager.getDefault().getApplicationContext().getExternalCacheDir().getAbsolutePath();

    //-------------------------------status bar color name-------------------------
    public static final String NAME_STATUS_BAR_COLOR = "status_bar_color";

}
