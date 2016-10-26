package com.rrtoyewx.andskinlibrary.constant;

import com.rrtoyewx.andskinlibrary.manager.GlobalManager;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class ConfigConstants {
    //-------------------------------
    public static final String NAMES_SPACE = "http://schemas.android.com/android/andSkin";
    public static final String ATTR_SKIN_ENABLE = "skin";

    //--------------------------shared preferences name---------------------
    public static final String NAME_SHARE_PREFERENCES = "and_skin";

    //--------------------------- external plugin path----------------------
    public static final String PATH_EXTERNAL_PLUGIN = GlobalManager.getDefault().getApplicationContext().getCacheDir().getAbsolutePath();


}
