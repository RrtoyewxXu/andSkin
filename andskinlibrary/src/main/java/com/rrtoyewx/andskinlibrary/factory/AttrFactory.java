package com.rrtoyewx.andskinlibrary.factory;

import com.rrtoyewx.andskinlibrary.attr.BackgroundAttr;
import com.rrtoyewx.andskinlibrary.attr.TextColorAttr;
import com.rrtoyewx.andskinlibrary.attr.BaseSkinAttr;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public abstract class AttrFactory {
    private static final String TYPE_BACKGROUND = "background";
    private static final String TYPE_TEXT_COLOR = "textColor";

    private static AttrFactory mInstance = new AttrFactoryImp();

    public abstract boolean isSupportedAttr(String attrName);

    public abstract BaseSkinAttr createSkinAttr(String attrType, String attrName, String attrValueRef, String suffix);

    public static AttrFactory getDefault() {
        return mInstance;
    }

    private static class AttrFactoryImp extends AttrFactory {

        @Override
        public boolean isSupportedAttr(String attrName) {
            if (TYPE_BACKGROUND.equals(attrName)) {
                return true;
            }

            if (TYPE_TEXT_COLOR.equals(attrName)) {
                return true;
            }
            return false;
        }

        @Override
        public BaseSkinAttr createSkinAttr(String attrType, String attrName, String attrValueRef, String suffix) {
            if (TYPE_BACKGROUND.equals(attrName)) {
                return new BackgroundAttr(attrType, attrName, attrValueRef);
            }

            if (TYPE_TEXT_COLOR.equals(attrName)) {
                return new TextColorAttr(attrType, attrName, attrValueRef);
            }

            return null;
        }
    }

}
