package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;

import com.rrtoyewx.andskinlibrary.manager.ResourceManager;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * 背景的属性
 */

public class BackgroundAttr extends BaseSkinAttr {

    public BackgroundAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public boolean findResource() {
        resetResourceValue();

        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)) {
            mFindDrawable = ResourceManager.getDefault().getDataResource().getDrawableByName(mAttrValueRef);
            return mFindDrawable != null;

        } else if (TYPE_ATTR_COLOR.equals(mAttrType)) {
            mFindColor = ResourceManager.getDefault().getDataResource().getColorByName(mAttrValueRef);
            return mFindColor != Resource.VALUE_ERROR_COLOR;

        }
        return true;
    }

    @Override
    public void applySkin(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType) && mFindDrawable != null) {
            view.setBackgroundDrawable(mFindDrawable);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);

        } else if (TYPE_ATTR_COLOR.equals(mAttrType) && mFindColor != Resource.VALUE_ERROR_COLOR) {
            view.setBackgroundColor(mFindColor);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
        }

        resetResourceValue();
    }
}
