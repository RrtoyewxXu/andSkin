package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.listener.IChangeSkin;
import com.rrtoyewx.andskinlibrary.listener.ILoadSkin;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public class SkinLoaderManager implements ILoadSkin {
    private List<IChangeSkin> mChangeSkinObserverList = new ArrayList<>();
    private OnChangeSkinListener mOnChangeSkinListener;


    private SkinLoaderManager() {
    }

    static class SkinLoaderManagerHolder {
        private static SkinLoaderManager sSkinLoaderManager = new SkinLoaderManager();
    }

    public static SkinLoaderManager getDefault() {
        return SkinLoaderManagerHolder.sSkinLoaderManager;
    }

    public void init(Context context) {
        DataOperationManager.getDefault().init(context);
        GlobalManager.getDefault().init(context);
        ResourceManager.getDefault().init();
    }

    public void setOnChangeSkinListener(OnChangeSkinListener listener) {
        this.mOnChangeSkinListener = listener;
    }

    public void register(IChangeSkin changeSkinObserver) {
        if (mChangeSkinObserverList.contains(changeSkinObserver)) {
            throw new IllegalArgumentException(changeSkinObserver.getClass().getSimpleName() + "also register!! please check");
        }
        mChangeSkinObserverList.add(changeSkinObserver);
        changeSkinObserver.onChangeSkin();
    }

    public void unRegister(IChangeSkin changeSkinObserver) {
        if (!mChangeSkinObserverList.contains(changeSkinObserver)) {
            throw new IllegalArgumentException(changeSkinObserver.getClass().getSimpleName() + "not register!! please check");
        }
        mChangeSkinObserverList.remove(changeSkinObserver);
    }

    @Override
    public boolean loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        if (mOnChangeSkinListener != null) {
            mOnChangeSkinListener.onChangeSkinBegin();
        }

        boolean loadSuccess = ResourceManager.getDefault().loadSkin(pluginPackageName, pluginPath, suffix);

        if (loadSuccess) {

            for (IChangeSkin changeSkinObserver : mChangeSkinObserverList) {
                changeSkinObserver.onChangeSkin();
            }

            if (mOnChangeSkinListener != null) {
                mOnChangeSkinListener.onChangeSkinSuccess();
            }
        } else {
            if (mOnChangeSkinListener != null) {
                mOnChangeSkinListener.onChangeSkinError();
            }
        }

        return loadSuccess;
    }

    @Override
    public boolean loadSkin(String suffix) {
        boolean loadSuccess = ResourceManager.getDefault().loadSkin(suffix);
        SkinL.d("load local skin " + suffix + " " + loadSuccess);
        if (loadSuccess) {

            for (IChangeSkin changeSkinObserver : mChangeSkinObserverList) {
                changeSkinObserver.onChangeSkin();
            }

            if (mOnChangeSkinListener != null) {
                mOnChangeSkinListener.onChangeSkinSuccess();
            }
        } else {
            if (mOnChangeSkinListener != null) {
                mOnChangeSkinListener.onChangeSkinError();
            }
        }

        return loadSuccess;
    }

    public void restoreSkin() {
        loadSkin("");
    }
}
