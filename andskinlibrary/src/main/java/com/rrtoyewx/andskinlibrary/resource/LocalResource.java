package com.rrtoyewx.andskinlibrary.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.rrtoyewx.andskinlibrary.manager.GlobalManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * 本地资源的管理类
 */

public class LocalResource extends Resource {

    public LocalResource(Context baseSkinActivity, String pluginPackageName, String pluginPath, String resourcesSuffix) {
        super(baseSkinActivity, pluginPackageName, pluginPath, resourcesSuffix);
        mResources = baseSkinActivity.getResources();
    }

    @Override
    public int getColorByName(String colorResName) {
        int trueColor = VALUE_ERROR_COLOR;
        colorResName = appendSuffix(colorResName);
        SkinL.d("getColorByName colorResName:" + colorResName);

        try {
            int localOtherColorId = mResources.getIdentifier(colorResName, "color", GlobalManager.getDefault().getPackageName());
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
            int trueDrawableId = mResources.getIdentifier(drawableResName, "drawable", GlobalManager.getDefault().getPackageName());
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
            int trueColorId = mResources.getIdentifier(trueColorStateName, "color", GlobalManager.getDefault().getPackageName());
            trueColorState = mResources.getColorStateList(trueColorId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trueColorState;
    }
}
