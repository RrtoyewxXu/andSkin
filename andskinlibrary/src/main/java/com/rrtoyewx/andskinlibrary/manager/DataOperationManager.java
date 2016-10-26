package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.helper.SharedPreferencesDataOperation;
import com.rrtoyewx.andskinlibrary.listener.IDataOperation;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class DataOperationManager {
    private static final String KEY_PLUGIN_PACKAGE_NAME = "plugin_package_name";
    private static final String KEY_RESOURCE_SUFFIX = "plugin_resource_suffix";

    private IDataOperation mDataOperation;

    public DataOperationManager(Context context) {
        mDataOperation = new SharedPreferencesDataOperation(context);
    }

    public void savePluginPackageName(String packageName) {
        mDataOperation.saveStringMessage(KEY_PLUGIN_PACKAGE_NAME, packageName);
    }

    public String getPluginPackageName() {
        return mDataOperation.getStringMessage(KEY_PLUGIN_PACKAGE_NAME, GlobalManager.getDefault().getPackageName());
    }

    public void saveResourceSuffix(String resourceSuffix) {
        mDataOperation.saveStringMessage(KEY_RESOURCE_SUFFIX, resourceSuffix);
    }

    public String getResourceSuffix() {
        return mDataOperation.getStringMessage(KEY_RESOURCE_SUFFIX, "");
    }
}
