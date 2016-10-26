package com.rrtoyewx.andskinlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.rrtoyewx.andskinlibrary.attr.SkinView;
import com.rrtoyewx.andskinlibrary.factory.SkinInflaterFactory;
import com.rrtoyewx.andskinlibrary.listener.IChangeSkin;
import com.rrtoyewx.andskinlibrary.manager.SkinLoaderManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public class BaseSkinActivity extends AppCompatActivity implements IChangeSkin {
    private List<SkinView> mSkinList;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (shouldRegister()) {
            mSkinInflaterFactory = new SkinInflaterFactory();
            mSkinInflaterFactory.setAppCompatActivity(this);
            LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);

            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    mSkinList = mSkinInflaterFactory.getSkinViewList();
                    SkinL.d(BaseSkinActivity.this.getClass().getSimpleName() + " skinList : " + mSkinList.toString());
                    SkinLoaderManager.getDefault().register(BaseSkinActivity.this);
                }
            });
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

        for (SkinView skinView : mSkinList) {
            skinView.apply();
        }
    }

    @Override
    protected void onDestroy() {
        if (shouldRegister()) {
            SkinLoaderManager.getDefault().unRegister(this);
        }
        super.onDestroy();
    }
}
