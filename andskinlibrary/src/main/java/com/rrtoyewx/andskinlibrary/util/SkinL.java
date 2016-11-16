package com.rrtoyewx.andskinlibrary.util;

import android.util.Log;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class SkinL {
    private static final String TAG = "and_skin";

    private static boolean sDebugFlag = true;

    public static void switchDebug(boolean debug) {
        sDebugFlag = debug;
    }

    public static void e(String errorMsg) {
        Log.e(TAG, errorMsg);
    }

    public static void d(String debugMsg) {
        if (sDebugFlag) {
            Log.e(TAG, debugMsg);
        }
    }

    public static void i(String infoMsg) {
        if (sDebugFlag) {
            Log.i(TAG, infoMsg);
        }
    }

    public static void w(String warnMsg) {
        if (sDebugFlag) {
            Log.i(TAG, warnMsg);
        }
    }

    public static void v(String verboseMsg) {
        if (sDebugFlag) {
            Log.v(TAG, verboseMsg);
        }
    }
}
