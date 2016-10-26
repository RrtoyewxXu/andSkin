package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.factory.DataOperationFactory;
import com.rrtoyewx.andskinlibrary.listener.ILoadSkin;
import com.rrtoyewx.andskinlibrary.listener.IDataOperation;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class DataOperationManager implements ILoadSkin {
    private static final String KEY_PLUGIN_PATH = "plugin_path";
    private static final String KEY_RESOURCE_SUFFIX = "plugin_resource_suffix";
    private static final String KEY_PLUGIN_PACKAGE_NAME = "plugin_package_name";

    private IDataOperation mDataOperation;

    public DataOperationManager() {
    }

    private static class DataOperationManagerHolder {
        static DataOperationManager sDataOperationManager = new DataOperationManager();
    }

    public static DataOperationManager getDefault() {
        return DataOperationManagerHolder.sDataOperationManager;
    }

    public void init(Context context) {
        mDataOperation = DataOperationFactory.newInstance().createDataOperation(context);
    }

    public void savePluginPath(String packageName) {
        mDataOperation.saveStringMessage(KEY_PLUGIN_PATH, packageName);
    }

    public String getPluginPath() {
        return mDataOperation.getStringMessage(KEY_PLUGIN_PATH, "");
    }

    public void saveResourceSuffix(String resourceSuffix) {
        mDataOperation.saveStringMessage(KEY_RESOURCE_SUFFIX, resourceSuffix);
    }

    public String getResourceSuffix() {
        return mDataOperation.getStringMessage(KEY_RESOURCE_SUFFIX, "");
    }

    public void savePluginPackageName(String pluginPackageName) {
        mDataOperation.saveStringMessage(KEY_PLUGIN_PACKAGE_NAME, pluginPackageName);
    }

    public String getPluginPackageName() {
        return mDataOperation.getStringMessage(KEY_PLUGIN_PACKAGE_NAME, GlobalManager.getDefault().getPackageName());
    }

    @Override
    public boolean loadSkin(String suffix) {
        loadSkin(GlobalManager.getDefault().getPackageName(), "", suffix);
        return true;
    }

    @Override
    public boolean loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        savePluginPackageName(pluginPackageName);
        savePluginPath(pluginPath);
        saveResourceSuffix(suffix);

        SkinL.d("save plugin information : pluginPackageName " + pluginPackageName + " suffix: " + suffix + " pluginPath: " +  pluginPath);
        return true;
    }
}
