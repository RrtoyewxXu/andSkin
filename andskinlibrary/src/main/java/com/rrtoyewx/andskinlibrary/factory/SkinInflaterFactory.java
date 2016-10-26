package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.base.BaseSkinAttr;
import com.rrtoyewx.andskinlibrary.constant.ConfigConstants;
import com.rrtoyewx.andskinlibrary.manager.DataOperationManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public class SkinInflaterFactory implements LayoutInflaterFactory {
    private static final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    private List<SkinView> mSkinViewList = new ArrayList<>();
    private AppCompatActivity mAppCompatActivity;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
        View view = delegate.createView(parent, name, context, attrs);

        boolean isSkinEnable = attrs.getAttributeBooleanValue(ConfigConstants.NAMES_SPACE, ConfigConstants.ATTR_SKIN_ENABLE, false);

        if (!isSkinEnable) {
            if (view == null) {
                view = createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            parseSkinAttr(context, attrs, view);
        }
        return view;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        SkinL.d("createViewFromTag name :" + name + " begin ");
        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            View view = null;
            if (-1 == name.indexOf('.')) {
                view = createView(context, name, "android.view.");
                if (view == null) {
                    view = createView(context, name, "android.widget.");
                }
                if (view == null) {
                    view = createView(context, name, "android.webkit.");
                }

                return view;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
            SkinL.d("createViewFromTag name :" + name + " finish ");
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<BaseSkinAttr> viewAttrs = new ArrayList<BaseSkinAttr>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            SkinL.d("attrName:" + attrName + "--- attrValue:" + attrValue);

            if (!AttrFactory.getDefault().isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    SkinL.d("entryName:" + entryName + "--- typeName:" + typeName);
                    String suffix = DataOperationManager.getDefault().getResourceSuffix();
                    BaseSkinAttr skinAttr = AttrFactory.getDefault().createSkinAttr(typeName, attrName, entryName, suffix);
                    if (skinAttr != null) {
                        viewAttrs.add(skinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (viewAttrs != null && !viewAttrs.isEmpty()) {
            SkinView skinView = new SkinView();
            skinView.setSkinView(view);
            skinView.setSkinAttrList(viewAttrs);

            mSkinViewList.add(skinView);
        }
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    public List<SkinView> getSkinViewList() {
        return mSkinViewList;
    }
}
