package com.rrtoyewx.andskinlibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.layoutInflater.SkinLayoutInflater;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.statusbar.StatusBarCompat;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinActivity:
 *
 * @see #shouldRegister() :控制是否接受SkinLoader的OnChangeSkinEvent
 * @see #shouldChangeStatusBarColor() : 控制是否更改状态栏
 * @see #overrideStatusBarColorResName() : 覆盖statusBar color的资源文件，从而实现不同的activity的中状态栏的颜色不一样
 * @see #getStatusBarColor() : 获取当前的statusBar color的颜色
 */

public class BaseSkinActivity extends AppCompatActivity implements IChangeSkin {
    protected BaseSkinActivity mActivity;
    private StatusBarCompat mStatusBar;
    private SkinLayoutInflater mSkinLayoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;
        if (shouldRegister()) {
            mSkinLayoutInflater = new SkinLayoutInflater(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (shouldRegister()) {
            findLayoutInflaterSkinViews();
            generateStatusBarIfShould();
            SkinLoader.getDefault().register(mActivity);
        }
    }

    private void findLayoutInflaterSkinViews() {
        List<SkinView> layoutInflaterSkinViewList = mSkinLayoutInflater.getLayoutInflaterSkinViewList();
        SkinL.d(mActivity.getClass().getSimpleName() + " skinList : " + layoutInflaterSkinViewList.toString());
    }

    private void generateStatusBarIfShould() {
        if (shouldChangeStatusBarColor()) {
            mStatusBar = new StatusBarCompat(mActivity);

            if (!TextUtils.isEmpty(overrideStatusBarColorResName())) {
                mStatusBar.setStatusBarColorResName(overrideStatusBarColorResName());
            }
            SkinL.d("mStatusBar need change");
        }
    }

    protected boolean shouldRegister() {
        return true;
    }

    protected boolean shouldChangeStatusBarColor() {
        return true;
    }

    protected String overrideStatusBarColorResName() {
        return null;
    }

    public SkinLayoutInflater getSkinLayoutInflater() {
        return mSkinLayoutInflater;
    }

    public View inflaterView(@LayoutRes int resource, ViewGroup parentView) {
        return this.inflaterView(resource, parentView, parentView != null);
    }

    public View inflaterView(@LayoutRes int resource, ViewGroup parentView, boolean attach) {
        return mSkinLayoutInflater.inflaterView(resource, parentView, attach);
    }

    public int getStatusBarColor() {
        if (mStatusBar != null) {
            return mStatusBar.getCurrentStatusColor();
        }
        return -1;
    }

    @Override
    public boolean findResource() {
        boolean findResourceSuccess = true;

        if (mStatusBar != null) {
            findResourceSuccess = mStatusBar.findResource();
        }

        if (!findResourceSuccess) {
            return false;
        }

        List<SkinView> layoutInflaterSkinViewList = mSkinLayoutInflater.getLayoutInflaterSkinViewList();
        for (IChangeSkin skinView : layoutInflaterSkinViewList) {
            findResourceSuccess = skinView.findResource();
            if (!findResourceSuccess) {
                break;
            }
        }

        List<SkinView> dynamicAddSkinViewList = mSkinLayoutInflater.getDynamicAddSkinViewList();
        for (IChangeSkin skinView : dynamicAddSkinViewList) {
            findResourceSuccess = skinView.findResource();
            if (!findResourceSuccess) {
                break;
            }
        }

        return findResourceSuccess;
    }

    @Override
    public void changeSkin() {
        if (mStatusBar != null) {
            mStatusBar.changeSkin();
        }

        List<SkinView> layoutInflaterSkinViewList = mSkinLayoutInflater.getLayoutInflaterSkinViewList();
        for (IChangeSkin skinView : layoutInflaterSkinViewList) {
            skinView.changeSkin();
        }

        List<SkinView> dynamicAddSkinViewList = mSkinLayoutInflater.getDynamicAddSkinViewList();
        for (IChangeSkin skinView : dynamicAddSkinViewList) {
            skinView.changeSkin();
        }
    }

    @Override
    protected void onDestroy() {
        if (shouldRegister()) {
            SkinLoader.getDefault().unRegister(this);
        }
        super.onDestroy();
    }
}
