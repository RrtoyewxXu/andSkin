package com.rrtoyewx.andskinlibrary.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

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
        int originColorId = mResources.getIdentifier(colorResName, "color", GlobalManager.getDefault().getPackageName());
        int originColor = mResources.getColor(originColorId);
        SkinL.d("getColorByName colorResName : " + colorResName);
        int trueColor = originColor;
        if (!TextUtils.isEmpty(mResourcesSuffix)) {
            colorResName = appendSuffix(colorResName);
            SkinL.d("getColorByName colorResName:" + colorResName);

            try {
                int localOtherColorId = mResources.getIdentifier(colorResName, "color", GlobalManager.getDefault().getPackageName());
                trueColor = mResources.getColor(localOtherColorId);
            } catch (Exception e) {
                trueColor = originColor;
                e.printStackTrace();
            }
        }
        return trueColor;
    }

    @Override
    public Drawable getDrawableByName(String drawableResName) {
        int originDrawableId = mResources.getIdentifier(drawableResName, "drawable", GlobalManager.getDefault().getPackageName());
        Drawable originDrawable = mResources.getDrawable(originDrawableId);
        SkinL.d("getDrawableByName colorResName:" + drawableResName);
        Drawable trueDrawable = originDrawable;
        if (!TextUtils.isEmpty(mResourcesSuffix)) {
            drawableResName = appendSuffix(drawableResName);
            SkinL.d("getDrawableByName colorResName:" + drawableResName);

            try {
                int trueDrawableId = mResources.getIdentifier(drawableResName, "drawable", GlobalManager.getDefault().getPackageName());
                trueDrawable = mResources.getDrawable(trueDrawableId);
            } catch (Exception e) {
                e.printStackTrace();
                trueDrawable = originDrawable;
            }
        }

        return trueDrawable;
    }

    @Override
    public ColorStateList getColorStateListByName(String colorStateListResName) {
        int originColorId = mResources.getIdentifier(colorStateListResName, "color", GlobalManager.getDefault().getPackageName());
        ColorStateList originColor = mResources.getColorStateList(originColorId);
        SkinL.d("getColorStateListByName originColorStateListResName : " + colorStateListResName);
        ColorStateList trueColorState = originColor;
        if (!TextUtils.isEmpty(mResourcesSuffix)) {
            String trueColorStateName = appendSuffix(colorStateListResName);
            try {
                int trueColorId = mResources.getIdentifier(trueColorStateName, "color", GlobalManager.getDefault().getPackageName());
                trueColorState = mResources.getColorStateList(trueColorId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return trueColorState;
    }
}
