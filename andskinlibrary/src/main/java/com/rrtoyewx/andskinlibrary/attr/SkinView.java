package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * 存储这个view的下面的在换肤的操作的下所需要更改的属性。
 * 需要换肤的view必须申明的在xml文件必须要有id属性，不然即使设置了skin:enable = "true",也会选择放弃。注意log
 */

public class SkinView implements IChangeSkin {
    private int mViewId;
    private List<BaseSkinAttr> mSkinAttrList;
    private View mView;

    public SkinView() {
        this(-1);
    }

    public SkinView(int viewId) {
        this(viewId, null);
    }

    public SkinView(int viewId, View view) {
        this.mViewId = viewId;
        this.mSkinAttrList = new ArrayList<>();
        this.mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setSkinView(View skinView) {
        this.mView = skinView;
    }

    public void setSkinAttrList(List<BaseSkinAttr> skinAttrList) {
        this.mSkinAttrList = skinAttrList;
    }

    public void setViewId(int viewId) {
        this.mViewId = viewId;
    }

    public int getViewId() {
        return mViewId;
    }

    @Override
    public String toString() {
        return "SkinView{" +
                "mSkinAttrList=" + mSkinAttrList +
                ", mView=" + mView +
                '}';
    }

    @Override
    public boolean onChangeSkin() {
        boolean changed = true;
        for (BaseSkinAttr attr : mSkinAttrList) {
            changed = attr.applySkin(mView);
            if (!changed) {
                break;
            }
        }

        return changed;
    }
}
