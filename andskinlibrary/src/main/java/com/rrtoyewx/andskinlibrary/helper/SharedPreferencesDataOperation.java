package com.rrtoyewx.andskinlibrary.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.rrtoyewx.andskinlibrary.listener.IDataOperation;

import static com.rrtoyewx.andskinlibrary.constant.ConfigConstants.NAME_SHARE_PREFERENCES;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class SharedPreferencesDataOperation implements IDataOperation {
    private static SharedPreferencesDataOperation sAndSkinSharedPreferencesHelper;
    private static SharedPreferences sAndSkinSharedPreferences;

    public SharedPreferencesDataOperation(Context context) {
        sAndSkinSharedPreferences = context.getSharedPreferences(NAME_SHARE_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void saveStringMessage(String key, String message) {
        sAndSkinSharedPreferences.edit().putString(key, message).apply();
    }

    @Override
    public String getStringMessage(String key, String defaultValue) {
        return sAndSkinSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void saveBooleanMessage(String key, boolean message) {
        sAndSkinSharedPreferences.edit().putBoolean(key, message).apply();
    }

    @Override
    public boolean getBooleanMessage(String key, boolean defaultValue) {
        return sAndSkinSharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void saveIntMessage(String key, int message) {
        sAndSkinSharedPreferences.edit().putInt(key, message).apply();
    }

    @Override
    public int getIntMessage(String key, int defaultValue) {
        return sAndSkinSharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public void saveLongMessage(String key, long message) {
        sAndSkinSharedPreferences.edit().putLong(key, message).apply();
    }

    @Override
    public long getLongMessage(String key, long defaultValue) {
        return sAndSkinSharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public void saveFloatMessage(String key, float message) {
        sAndSkinSharedPreferences.edit().putFloat(key, message).apply();
    }

    @Override
    public float getFloatMessage(String key, float defaultValue) {
        return sAndSkinSharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public void saveDoubleMessage(String key, double message) {
        sAndSkinSharedPreferences.edit().putFloat(key, (float) message).apply();
    }

    @Override
    public double getDoubleMessage(String key, double defaultValue) {
        return sAndSkinSharedPreferences.getFloat(key, (float) defaultValue);
    }
}
