package com.rrtoyewx.andskinlibrary.layoutInflater;

import android.support.annotation.LayoutRes;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/11/30.
 */

public class SkinLayoutInflater {
    private SkinInflaterFactory mSkinInflaterFactory;
    private BaseSkinActivity mBaseSkinActivity;

    //activity/fragment inflater init skinView
    private List<SkinView> mLayoutInflaterSkinViewList;
    //dynamic add View
    private List<SkinView> mDynamicAddSkinViewList;

    public SkinLayoutInflater(BaseSkinActivity baseSkinActivity) {
        this.mBaseSkinActivity = baseSkinActivity;
        mSkinInflaterFactory = new SkinInflaterFactory();

        mDynamicAddSkinViewList = new ArrayList<>();
        mLayoutInflaterSkinViewList = new ArrayList<>();

        LayoutInflaterCompat.setFactory(mBaseSkinActivity.getLayoutInflater(), mSkinInflaterFactory);
    }

    private SkinInflaterFactory getSkinInflaterFactory() {
        return mSkinInflaterFactory;
    }

    public View inflaterView(@LayoutRes int resource, ViewGroup parentView) {
        return this.inflaterView(resource, parentView, parentView != null);
    }

    public View inflaterView(@LayoutRes int resource, ViewGroup parentView, boolean attach) {
        mSkinInflaterFactory.clearSkinViewList();

        View inflateView = LayoutInflater.from(mBaseSkinActivity).inflate(resource, parentView, attach);
        List<SkinView> dynamicAddSkinViewList = mSkinInflaterFactory.getSkinViewList();
        if (!dynamicAddSkinViewList.isEmpty()) {
            findSkinView(inflateView, dynamicAddSkinViewList);
            mDynamicAddSkinViewList.addAll(dynamicAddSkinViewList);
            SkinL.d("inflate view parse change skin:" + dynamicAddSkinViewList.toString());
            SkinLoader.getDefault().dynamicAddSkinViewInObserver(dynamicAddSkinViewList, mBaseSkinActivity);
        }
        mSkinInflaterFactory.clearSkinViewList();
        return inflateView;
    }

    private void findSkinView(View root, List<SkinView> skinViewList) {
        View view = null;
        Iterator<SkinView> iterator = skinViewList.iterator();
        while (iterator.hasNext()) {
            SkinView skinView = iterator.next();

            int viewId = skinView.getViewId();
            view = root.findViewById(viewId);

            if (view != null) {
                skinView.setSkinView(view);
            } else {
                SkinL.d("未找到id value:" + viewId + ",请检查" + root.getClass().getSimpleName() + "xml文件");
                iterator.remove();
            }
        }
    }

    public List<SkinView> getLayoutInflaterSkinViewList() {
        List<SkinView> skinViewList = mSkinInflaterFactory.getSkinViewList();
        if (!skinViewList.isEmpty()) {
            findSkinView(mBaseSkinActivity.getWindow().getDecorView(), skinViewList);
            mLayoutInflaterSkinViewList.addAll(skinViewList);
            mSkinInflaterFactory.clearSkinViewList();
        }

        return mLayoutInflaterSkinViewList;
    }

    public List<SkinView> getDynamicAddSkinViewList() {
        return mDynamicAddSkinViewList;
    }
}
