package com.rrtoyewx.andskinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.rrtoyewx.andskinlibrary.manager.ResourceManager;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class BackgroundAttr extends BaseSkinAttr {

    public BackgroundAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public boolean applySkin(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)) {
            Drawable drawable = ResourceManager.getDefault().getDataResource().getDrawableByName(mAttrValueRef);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
                SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
            }

            return drawable != null;

        } else if (TYPE_ATTR_COLOR.equals(mAttrType)) {
            int color = ResourceManager.getDefault().getDataResource().getColorByName(mAttrValueRef);
            if (color != Resource.VALUE_ERROR_COLOR) {
                view.setBackgroundColor(color);
                SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
            }
            return color != Resource.VALUE_ERROR_COLOR;
        }

        return true;
    }
}
