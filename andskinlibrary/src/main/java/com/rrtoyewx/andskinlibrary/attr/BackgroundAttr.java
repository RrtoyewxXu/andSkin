package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;

import com.rrtoyewx.andskinlibrary.base.BaseSkinAttr;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class BackgroundAttr extends BaseSkinAttr {

    public BackgroundAttr(String mAttrType, String mAttrName, String mAttrValueRef, String mSuffix) {
        super(mAttrType, mAttrName, mAttrValueRef, mSuffix);
    }

    @Override
    public void apply(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)) {
            view.setBackgroundDrawable(null);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef + mAttrValueSuffix);
        } else if (TYPE_ATTR_COLOR.equals(mAttrType)) {
            view.setBackgroundColor(-1);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef + mAttrValueSuffix);
        }
    }
}
