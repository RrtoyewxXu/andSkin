package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;

import com.rrtoyewx.andskinlibrary.base.BaseSkinAttr;
import com.rrtoyewx.andskinlibrary.manager.ResourceManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class BackgroundAttr extends BaseSkinAttr {

    public BackgroundAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public void apply(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)) {
            view.setBackgroundDrawable(ResourceManager.getDefault().getDataResource().getDrawableByName(mAttrValueRef));
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
        } else if (TYPE_ATTR_COLOR.equals(mAttrType)) {
            view.setBackgroundColor(ResourceManager.getDefault().getDataResource().getColorByName(mAttrValueRef));
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
        }
    }
}
