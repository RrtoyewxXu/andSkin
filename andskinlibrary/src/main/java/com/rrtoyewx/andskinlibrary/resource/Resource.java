package com.rrtoyewx.andskinlibrary.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public abstract class Resource {
    protected Context mContext;
    protected Resources mResources;
    protected String mResourcesSuffix;

    protected String mPluginPath;
    protected String mPluginPackageName;

    public Resource(Context baseSkinActivity, String pluginPackageName, String pluginPath, String resourcesSuffix) {
        this.mContext = baseSkinActivity;
        this.mPluginPackageName = pluginPackageName;
        this.mResourcesSuffix = resourcesSuffix;
        this.mPluginPath = pluginPath;
    }

    public abstract int getColorByName(String colorResName);

    public abstract Drawable getDrawableByName(String drawableResName);

    public abstract ColorStateList getColorStateListByName(String colorStateListResName);

    final String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mResourcesSuffix)) {
            return name + "_" + mResourcesSuffix;
        }

        return name;
    }

    public String getResourcesSuffix() {
        return mResourcesSuffix;
    }

    public void changeResourceSuffix(String resourcesSuffix) {
        this.mResourcesSuffix = resourcesSuffix;
    }

    public String getPluginPath() {
        return mPluginPath;
    }

    public void setPluginPath(String mPluginPath) {
        this.mPluginPath = mPluginPath;
    }
}
