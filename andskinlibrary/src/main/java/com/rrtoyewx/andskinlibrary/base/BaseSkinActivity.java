package com.rrtoyewx.andskinlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.factory.SkinInflaterFactory;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.manager.SkinLoaderManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class BaseSkinActivity extends AppCompatActivity implements IChangeSkin {
    private List<SkinView> mSkinList;
    protected BaseSkinActivity mActivity;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;

        if (shouldRegister()) {
            mSkinInflaterFactory = new SkinInflaterFactory();
            LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
            getWindow().getDecorView().post(new FindViewRunnable());
        }

        super.onCreate(savedInstanceState);
    }

    protected boolean shouldRegister() {
        return true;
    }

    @Override
    public void onChangeSkin() {
        if (mSkinList == null || mSkinList.isEmpty()) {
            return;
        }

        for (IChangeSkin skinView : mSkinList) {
            skinView.onChangeSkin();
        }
    }

    @Override
    protected void onDestroy() {
        if (shouldRegister()) {
            SkinLoaderManager.getDefault().unRegister(this);
        }
        super.onDestroy();
    }

    class FindViewRunnable implements Runnable {
        @Override
        public void run() {
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
            SkinLoaderManager.getDefault().register(mActivity);
        }
    }
}
