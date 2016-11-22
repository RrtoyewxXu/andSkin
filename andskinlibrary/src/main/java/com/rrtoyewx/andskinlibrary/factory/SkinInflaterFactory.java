package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.attr.BaseSkinAttr;
import com.rrtoyewx.andskinlibrary.constant.ConfigConstants;
import com.rrtoyewx.andskinlibrary.manager.GlobalManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/25.
 * SkinInflaterFactory: 不负责生成View
 *
 * @see #onCreateView(View, String, Context, AttributeSet) return null;
 * 只负责解析 skin:enable = "true"的View属性，并且View必须设定id值
 * 从而避免通过SkinInflaterFactory生成View失败后View无法换肤的。
 */

public class SkinInflaterFactory implements LayoutInflaterFactory {
    private List<SkinView> mSkinViewList = new ArrayList<>();

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(ConfigConstants.SKIN_NAMES_SPACE, ConfigConstants.ATTR_SKIN_ENABLE, false);
        String attrList = attrs.getAttributeValue(ConfigConstants.SKIN_NAMES_SPACE, ConfigConstants.ATTR_SKIN_LIST);
        if (isSkinEnable) {
            try {
                if (TextUtils.isEmpty(attrList)) {
                    parseSkinAttr(context, attrs, name);
                } else {
                    attrList = attrList.trim();
                    parseSkinAttrByAttrList(context, attrs, attrList, name);
                }
            } catch (Exception e) {
                e.printStackTrace();
                SkinL.e("解析xml文件失败，请检查xml文件");
            }
        }
        return null;
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, String name) throws Exception {
        List<BaseSkinAttr> viewAttrs = new ArrayList<>();
        SkinView skinView = new SkinView();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            SkinL.d("attrName:" + attrName + "--- attrValue:" + attrValue);

            if ("id".equals(attrName)) {
                String idValue = attrValue.substring(1);
                skinView.setViewId(Integer.parseInt(idValue));
                continue;
            }

            if (!AttrFactory.getDefault().isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                int attrId = Integer.parseInt(attrValue.substring(1));
                String entryName = context.getResources().getResourceEntryName(attrId);
                String typeName = context.getResources().getResourceTypeName(attrId);
                SkinL.d("entryName:" + entryName + "--- typeName:" + typeName);

                String suffix = GlobalManager.getDefault().getResourceSuffix();
                BaseSkinAttr skinAttr = AttrFactory.getDefault().createSkinAttr(typeName, attrName, entryName, suffix);
                if (skinAttr != null) {
                    viewAttrs.add(skinAttr);
                }
            }
        }

        if (!viewAttrs.isEmpty()) {
            if (skinView.getViewId() != -1) {
                skinView.setSkinAttrList(viewAttrs);
                mSkinViewList.add(skinView);
            } else {
                SkinL.e("类型为:" + name + "并没有设定id的属性值，请设定!!!");
            }
        }
    }


    private void parseSkinAttrByAttrList(Context context, AttributeSet attributeSet, String attrStr, String name) {
        List<BaseSkinAttr> viewAttrs = new ArrayList<>();
        SkinView skinView = new SkinView();

        String id = attributeSet.getAttributeValue(ConfigConstants.ANDROID_NAMES_SPACE, "id");
        if (!TextUtils.isEmpty(id)) {
            String idValue = id.substring(1);
            skinView.setViewId(Integer.parseInt(idValue));

            String[] split = attrStr.split("\\|");
            for (int i = 0; i < split.length; i++) {
                String attrsName = split[i];
                String attrsValue = attributeSet.getAttributeValue(ConfigConstants.ANDROID_NAMES_SPACE, attrsName);
                SkinL.d("attrsName:" + attrsName + "----" + "attrsValue:" + attrsValue);
                if (!AttrFactory.getDefault().isSupportedAttr(attrsName)) {
                    continue;
                }

                if (attrsValue.startsWith("@")) {
                    int attrId = Integer.parseInt(attrsValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(attrId);
                    String typeName = context.getResources().getResourceTypeName(attrId);
                    SkinL.d("entryName:" + entryName + "--- typeName:" + typeName);

                    String suffix = GlobalManager.getDefault().getResourceSuffix();
                    BaseSkinAttr skinAttr = AttrFactory.getDefault().createSkinAttr(typeName, attrsName, entryName, suffix);
                    if (skinAttr != null) {
                        viewAttrs.add(skinAttr);
                    }
                }
            }
            if (!viewAttrs.isEmpty()) {
                skinView.setSkinAttrList(viewAttrs);
                mSkinViewList.add(skinView);
            }

        } else {
            SkinL.e("类型为:" + name + "并没有设定id的属性值，请设定!!!");
        }

    }


    public List<SkinView> getSkinViewList() {
        return mSkinViewList;
    }
}
