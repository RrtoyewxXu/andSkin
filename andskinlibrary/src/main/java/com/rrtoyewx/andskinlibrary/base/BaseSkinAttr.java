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

    public BaseSkinAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        this.mAttrType = mAttrType;
        this.mAttrName = mAttrName;
        this.mAttrValueRef = mAttrValueRef;
    }

    @Override
    public String toString() {
        return "BaseSkinAttr{" +
                "mAttrType='" + mAttrType + '\'' +
                ", mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRef='" + mAttrValueRef + '\'' +
                '}';
    }

    public abstract void apply(View view);
}
