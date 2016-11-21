package com.rrtoyewx.andskinlibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.factory.SkinInflaterFactory;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.statusbar.StatusBarCompat;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 * BaseSkinActivity:
 *
 * @see #shouldRegister() :控制是否接受SkinLoader的OnChangeSkinEvent
 * @see #shouldChangeStatusBarColor() : 控制是否更改状态栏
 * @see GenerateAllSkinChangeObserverRunnable :根据id去查询当前需要更要的View，判断是否生成statusbar observer
 * @see #overrideStatusBarColorResName() : 覆盖statusBar color的资源文件，从而实现不同的activity的中状态栏的颜色不一样
 * @see #getStatusBarColor() : 获取当前的statusBar color的颜色
 */

public class BaseSkinActivity extends AppCompatActivity implements IChangeSkin {
    protected BaseSkinActivity mActivity;
    private List<SkinView> mSkinList;
    private StatusBarCompat mStatusBar;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;

        if (shouldRegister()) {
            mSkinInflaterFactory = new SkinInflaterFactory();
            LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
            getWindow().getDecorView().post(new GenerateAllSkinChangeObserverRunnable());
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        SkinL.d("setContentView");
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        SkinL.d("setContentView");
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        SkinL.d("setContentView");
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

    public int getStatusBarColor() {
        if (mStatusBar != null) {
            return mStatusBar.getCurrentStatusColor();
        }
        return -1;
    }

    @Override
    public void onChangeSkin() {
        if (mSkinList == null || mSkinList.isEmpty()) {
            return;
        }

        for (IChangeSkin skinView : mSkinList) {
            skinView.onChangeSkin();
        }

        if (mStatusBar != null) {
            mStatusBar.onChangeSkin();
        }
    }

    @Override
    protected void onDestroy() {
        if (shouldRegister()) {
            SkinLoader.getDefault().unRegister(this);
        }
        super.onDestroy();
    }

    class GenerateAllSkinChangeObserverRunnable implements Runnable {
        @Override
        public void run() {
            findChangeSkinViews();
            generateStatusBarIfShould();
            SkinLoader.getDefault().register(mActivity);
        }

        private void findChangeSkinViews() {
            mSkinList = mSkinInflaterFactory.getSkinViewList();
            if (mSkinList != null && !mSkinList.isEmpty()) {
                View view = null;
                Iterator<SkinView> iterator = mSkinList.iterator();
                while (iterator.hasNext()) {
                    SkinView skinView = iterator.next();

                    int viewId = skinView.getViewId();
                    view = findViewById(viewId);

                    if (view != null) {
                        skinView.setSkinView(view);
                    } else {
                        SkinL.d("未找到id value:" + viewId + ",请检查" + BaseSkinActivity.this.getClass().getSimpleName() + "xml文件");
                        iterator.remove();
                    }
                }
            }

            SkinL.d(mActivity.getClass().getSimpleName() + " skinList : " + mSkinList.toString());
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
    }
}
