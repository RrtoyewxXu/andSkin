package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/10/25.
 * SkinLoader：
 * 1.初始化DataManager,GlobalManager,ResourceManager
 * 2.负责调度DataManager和ResourceManager在加载皮肤的时候的关系
 * 3.维护需要更改皮肤的IChangeSkin的对象
 */

public class SkinLoader implements ILoadSkin {
    private List<IChangeSkin> mChangeSkinObserverList = new ArrayList<>();
    private OnChangeSkinListener mOnChangeSkinListener;
    private IDeliver mLoadSkinDeliver;
    private LoadSkinTask mLoadSkinTask;

    private SkinLoader() {

    }

    static class SkinLoaderHolder {
        private static SkinLoader sSkinLoaderManager = new SkinLoader();
    }

    public static SkinLoader getDefault() {
        return SkinLoaderHolder.sSkinLoaderManager;
    }

    public void init(Context context) {
        mLoadSkinDeliver = new LoadSkinDeliver();
        DataManager.getDefault().init(context, mLoadSkinDeliver);

        String pluginAPKPackageName = DataManager.getDefault().getPluginPackageName();
        String pluginAPKPath = DataManager.getDefault().getPluginPath();
        String pluginAPKSuffix = DataManager.getDefault().getResourceSuffix();

        GlobalManager.getDefault().init(context, pluginAPKPackageName, pluginAPKPath, pluginAPKSuffix);
        ResourceManager.getDefault().init(pluginAPKPackageName, pluginAPKPath, pluginAPKSuffix, mLoadSkinDeliver);
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
    public void loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        cancelLoadSkinTask();
        startLoadSkinTask(pluginPackageName, pluginPath, suffix);
    }

    @Override
    public void loadSkin(String suffix) {
        loadSkin("", "", suffix);
    }

    public void restoreSkin() {
        loadSkin("");
    }

    private void cancelLoadSkinTask() {
        if (mLoadSkinTask != null
                && mLoadSkinTask.getStatus() != AsyncTask.Status.FINISHED
                && !mLoadSkinTask.isCancelled()) {
            mLoadSkinTask.cancel(false);
        }

        mLoadSkinTask = null;
    }

    private void startLoadSkinTask(String pluginAPKPackageName, String pluginAPKPath, String resourceSuffix) {
        mLoadSkinTask = new LoadSkinTask();
        mLoadSkinTask.execute(pluginAPKPackageName, pluginAPKPath, resourceSuffix);
    }

    private class LoadSkinTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String pluginAPKPackageName = params[0];
            String pluginAPKPath = params[1];
            String resourceSuffix = params[2];

            ResourceManager.getDefault().loadSkin(pluginAPKPackageName, pluginAPKPath, resourceSuffix);
            return null;
        }
    }

    class LoadSkinDeliver implements IDeliver {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void postResourceManagerLoadSuccess(String pluginPackageName, String pluginPath, String resourceSuffix) {
            SkinL.d("生成Resource对象成功");
            DataManager.getDefault().loadSkin(pluginPackageName, pluginPath, resourceSuffix);
        }

        @Override
        public void postResourceManagerLoadError() {
            SkinL.d("生成Resource对象失败,重新还原本地主题");
            //当需要生成的pluginResource对象的失败后，ResourceManager中的Resource对象为null,虽然做过try...catch , 但是最好还原主题。
            //todo:还原上次的皮肤设置
            restoreSkin();
        }

        @Override
        public void postDataManagerLoadSuccess(String pluginPackageName, String pluginPath, String resourceSuffix) {
            SkinL.d("保存本次换肤的相关信息成功");
            GlobalManager.getDefault().flushPluginInfos(pluginPackageName, pluginPath, resourceSuffix);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (IChangeSkin changeSkin : mChangeSkinObserverList) {
                        changeSkin.onChangeSkin();
                    }
                }
            });
        }

        @Override
        public void postDataManagerLoadError() {
            SkinL.d("保存本次换肤的相关信息和上次一样");
        }
    }
}
