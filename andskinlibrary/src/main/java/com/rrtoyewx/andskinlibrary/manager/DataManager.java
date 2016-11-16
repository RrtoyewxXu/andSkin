package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.factory.DataOperationFactory;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.interfaces.IDataManipulation;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * DataManager：
 * 只负责存储/获取上次更换皮肤的信息。不暴露给用户使用这些信息
 * 1.插件文件路径
 * 2.插件文件的后缀
 * 3.插件APK的包名
 */

public class DataManager implements ILoadSkin {
    private static final String KEY_PLUGIN_PATH = "plugin_path";
    private static final String KEY_RESOURCE_SUFFIX = "plugin_resource_suffix";
    private static final String KEY_PLUGIN_PACKAGE_NAME = "plugin_package_name";

    private IDeliver mDeliver;

    private IDataManipulation mDataOperation;

    private DataManager() {
    }

    private static class DataManagerHolder {
        static DataManager sDataOperationManager = new DataManager();
    }

    static DataManager getDefault() {
        return DataManagerHolder.sDataOperationManager;
    }

    void init(Context context, IDeliver deliver) {
        mDataOperation = DataOperationFactory.newInstance().createDataOperation(context);
        mDeliver = deliver;
    }

    void savePluginPath(String packageName) {
        mDataOperation.saveStringMessage(KEY_PLUGIN_PATH, packageName);
    }

    String getPluginPath() {
        return mDataOperation.getStringMessage(KEY_PLUGIN_PATH, "");
    }

    void saveResourceSuffix(String resourceSuffix) {
        mDataOperation.saveStringMessage(KEY_RESOURCE_SUFFIX, resourceSuffix);
    }

    String getResourceSuffix() {
        return mDataOperation.getStringMessage(KEY_RESOURCE_SUFFIX, "");
    }

    void savePluginPackageName(String pluginPackageName) {
        mDataOperation.saveStringMessage(KEY_PLUGIN_PACKAGE_NAME, pluginPackageName);
    }

    String getPluginPackageName() {
        return mDataOperation.getStringMessage(KEY_PLUGIN_PACKAGE_NAME, "");
    }

    @Override
    public void loadSkin(String suffix) {
        loadSkin("", "", suffix);
    }

    @Override
    public void loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        if (pluginPackageName != null && pluginPackageName.equals(getPluginPackageName())
                && pluginPath != null && pluginPath.equals(getPluginPath())
                && suffix != null && suffix.equals(getResourceSuffix())) {

            mDeliver.postDataManagerLoadError();
        } else {
            savePluginPackageName(pluginPackageName);
            savePluginPath(pluginPath);
            saveResourceSuffix(suffix);

            SkinL.d("save plugin information : pluginPackageName " + pluginPackageName + " suffix: " + suffix + " pluginPath: " + pluginPath);
            mDeliver.postDataManagerLoadSuccess(pluginPackageName, pluginPath, suffix);
        }
    }
}
