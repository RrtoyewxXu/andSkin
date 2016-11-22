package com.rrtoyewx.andskinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Rrtoyewx on 2016/11/22.
 * 图片的src属性
 */

public class SrcAttr extends BaseSkinAttr {

    public SrcAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public boolean applySkin(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType) && view instanceof ImageView) {
            Drawable drawable = getResource().getDrawableByName(mAttrValueRef);
            if (drawable != null) {
                ((ImageView) view).setImageDrawable(drawable);
            }
            return drawable != null;
        }
        return true;
    }

}
