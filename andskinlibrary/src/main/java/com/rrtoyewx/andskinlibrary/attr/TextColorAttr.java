package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;
import android.widget.TextView;

import com.rrtoyewx.andskinlibrary.base.BaseSkinAttr;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * TextColorAttr : text color attr
 */

public class TextColorAttr extends BaseSkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextView && TYPE_ATTR_COLOR.equals(mAttrType)) {
            // TODO: 2016/10/24 set color
            ((TextView) view).setTextColor(-1);
            SkinL.d(view + " : " + mAttrName + " apply " + mAttrValueRef + mAttrValueSuffix);
        }
    }
}
