package com.rrtoyewx.andskinlibrary.resource;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.io.File;
import java.lang.reflect.Method;

import static com.rrtoyewx.andskinlibrary.constant.ConfigConstants.PATH_EXTERNAL_PLUGIN;

/**
 * Created by Rrtoyewx on 2016/10/26.
 * 插件APK的资源管理类
 */

public class PluginResource extends Resource {

    public PluginResource(Context baseSkinActivity, String pluginPackageName, String pluginPath, String resourcesSuffix) throws Exception {
        super(baseSkinActivity, pluginPackageName, pluginPath, resourcesSuffix);
        loadPlugin();
    }

    private void loadPlugin() throws Exception {
        File file = new File(PATH_EXTERNAL_PLUGIN + "/" + mPluginPath);
        SkinL.d(file.getAbsolutePath());
        if (mPluginPath == null || !file.exists()) {
            throw new IllegalArgumentException("plugin skin not exit, please check");
        }

        AssetManager assetManager = null;

        assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, file.getAbsolutePath());

        Resources superRes = mContext.getResources();
        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        SkinL.d("加载外部插件的皮肤成功");
    }

    @Override
    public int getColorByName(String colorResName) {
        int trueColor = VALUE_ERROR_COLOR;
        colorResName = appendSuffix(colorResName);
        SkinL.d("getColorByName colorResName:" + colorResName);

        try {
            int localOtherColorId = mResources.getIdentifier(colorResName, "color", mPluginPackageName);
            trueColor = mResources.getColor(localOtherColorId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trueColor;
    }

    @Override
    public Drawable getDrawableByName(String drawableResName) {
        Drawable trueDrawable = null;
        drawableResName = appendSuffix(drawableResName);
        SkinL.d("getDrawableByName drawableResName:" + drawableResName);

        try {
            int trueDrawableId = mResources.getIdentifier(drawableResName, "drawable", mPluginPackageName);
            trueDrawable = mResources.getDrawable(trueDrawableId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trueDrawable;
    }

    @Override
    public ColorStateList getColorStateListByName(String colorStateListResName) {
        ColorStateList trueColorState = null;
        String trueColorStateName = appendSuffix(colorStateListResName);
        SkinL.d("getColorStateListByName trueColorStateName:" + trueColorStateName);

        try {
            int trueColorId = mResources.getIdentifier(trueColorStateName, "color", mPluginPackageName);
            trueColorState = mResources.getColorStateList(trueColorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trueColorState;
    }
}
