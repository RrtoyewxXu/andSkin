package com.rrtoyewx.andskinlibrary.attr;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.rrtoyewx.andskinlibrary.manager.ResourceManager;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * TextColorAttr : text color attr
 */

public class TextColorAttr extends BaseSkinAttr {

    public TextColorAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public boolean applySkin(View view) {
        if (view instanceof TextView && TYPE_ATTR_COLOR.equals(mAttrType)) {
            ColorStateList colorStateList = ResourceManager.getDefault().getDataResource().getColorStateListByName(mAttrValueRef);
            if (colorStateList != null) {
                ((TextView) view).setTextColor(colorStateList);
                SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
            }
            return colorStateList != null;
        }
        return true;
    }
}
