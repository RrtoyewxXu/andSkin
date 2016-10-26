package com.rrtoyewx.andskinlibrary.base;

import android.view.View;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinAttr: 属性的封装类
 */

public abstract class BaseSkinAttr {
    protected static final String TYPE_ATTR_COLOR = "color";
    protected static final String TYPE_ATTR_DRAWABLE = "drawable";

    protected String mAttrType;
    protected String mAttrName;
    protected String mAttrValueRef;
    protected String mAttrValueSuffix;

    public BaseSkinAttr() {
    }

    public BaseSkinAttr(String mAttrType, String mAttrName, String mAttrValueRef, String mSuffix) {
        this.mAttrType = mAttrType;
        this.mAttrName = mAttrName;
        this.mAttrValueRef = mAttrValueRef;
        this.mAttrValueSuffix = mSuffix;
    }

    @Override
    public String toString() {
        return "BaseSkinAttr{" +
                "mAttrType='" + mAttrType + '\'' +
                ", mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRef='" + mAttrValueRef + '\'' +
                ", mAttrValueSuffix='" + mAttrValueSuffix + '\'' +
                '}';
    }

    public abstract void apply(View view);
}
