package com.rrtoyewx.andskinlibrary.attr;

import android.view.View;

import com.rrtoyewx.andskinlibrary.base.BaseSkinAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class SkinView {
    private List<BaseSkinAttr> mSkinAttrList;
    private View mSkinView;

    public SkinView() {
        this(null);
    }

    public SkinView(View view) {
        this.mSkinAttrList = new ArrayList<>();
        this.mSkinView = view;
    }

    public View getmSkinView() {
        return mSkinView;
    }

    public void setSkinView(View skinView) {
        this.mSkinView = skinView;
    }

    public void setSkinAttrList(List<BaseSkinAttr> skinAttrList) {
        this.mSkinAttrList = skinAttrList;
    }

    public void apply() {
        for (BaseSkinAttr attr : mSkinAttrList) {
            attr.apply(mSkinView);
        }
    }

    @Override
    public String toString() {
        return "SkinView{" +
                "mSkinAttrList=" + mSkinAttrList +
                ", mSkinView=" + mSkinView +
                '}';
    }
}
