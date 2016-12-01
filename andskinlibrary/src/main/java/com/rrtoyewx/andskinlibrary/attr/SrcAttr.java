package com.rrtoyewx.andskinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/11/22.
 * 图片的src属性
 */

public class SrcAttr extends BaseSkinAttr {
    private Drawable mFindDrawable;

    public SrcAttr(String mAttrType, String mAttrName, String mAttrValueRef) {
        super(mAttrType, mAttrName, mAttrValueRef);
    }

    @Override
    public boolean findResource() {
        resetResourceValue();
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)) {
            mFindDrawable = getResource().getDrawableByName(mAttrValueRef);
            return mFindDrawable != null;
        }
        return true;
    }


    @Override
    public void applySkin(View view) {
        if (TYPE_ATTR_DRAWABLE.equals(mAttrType)
                && view instanceof ImageView
                && mFindDrawable != null) {

            ((ImageView) view).setImageDrawable(mFindDrawable);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef);
        }

        resetResourceValue();
    }
}
