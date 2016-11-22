package com.rrtoyewx.andskinlibrary.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.rrtoyewx.andskinlibrary.constant.ConfigConstants;
import com.rrtoyewx.andskinlibrary.interfaces.IApplySkin;
import com.rrtoyewx.andskinlibrary.manager.ResourceManager;
import com.rrtoyewx.andskinlibrary.resource.Resource;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinAttr: 属性的封装类
 */

public abstract class BaseSkinAttr implements IApplySkin {
    protected static final String TYPE_ATTR_COLOR = "color";
    protected static final String TYPE_ATTR_DRAWABLE = "drawable";

    protected String mAttrType;
    protected String mAttrName;
    protected String mAttrValueRef;

    protected Drawable mFindDrawable;
    protected int mFindColor;
    protected ColorStateList mFindColorStateList;

    public BaseSkinAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        this.mAttrType = mAttrType;
        this.mAttrName = mAttrName;
        this.mAttrValueRef = mAttrValueRef;
    }

    final Resource getResource(){
        return ResourceManager.getDefault().getDataResource();
    }

    final void resetResourceValue(){
        mFindColor = Resource.VALUE_ERROR_COLOR;
        mFindDrawable = null;
        mFindColorStateList = null;
    }

    @Override
    public String toString() {
        return "BaseSkinAttr{" +
                "mAttrType='" + mAttrType + '\'' +
                ", mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRef='" + mAttrValueRef + '\'' +
                '}';
    }
}
