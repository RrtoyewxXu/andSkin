package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.listener.OnInitListener;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.util.ArrayList;
import java.util.Iterator;
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

    private IDeliver mLoadSkinDeliver;
    private LoadSkinTask mLoadSkinTask;

    private List<OnChangeSkinListener> mOnChangeSkinListenerList = new ArrayList<>();
    private OnInitListener mOnInitListener;

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

    public void addOnChangeSkinListener(OnChangeSkinListener listener) {
        mOnChangeSkinListenerList.add(listener);
    }

    public void setOnInitListener(OnInitListener listener) {
        this.mOnInitListener = listener;
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

        //解决内存泄露问题
        Iterator<OnChangeSkinListener> iterator = mOnChangeSkinListenerList.iterator();
        while (iterator.hasNext()) {
            OnChangeSkinListener onChangeSkinListener = iterator.next();
            if (changeSkinObserver == onChangeSkinListener.getBindActivity()
                    || onChangeSkinListener.getBindActivity() == null) {
                iterator.remove();
            }
        }
    }

    @Override
    public void loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        loadSkinInner(pluginPackageName, pluginPath, suffix, true);
    }

    private void loadSkinInner(String pluginPackageName, String pluginPath, String suffix, boolean needCallSkinChangeListener) {
        cancelLoadSkinTask();
        startLoadSkinTask(pluginPackageName, pluginPath, suffix, needCallSkinChangeListener);
    }

    @Override
    public void loadSkin(String suffix) {
        loadSkin("", "", suffix);
    }

    public void restoreDefaultSkin() {
        restoreLastSkinInner(true);
    }

    private void restoreDefaultSkinInner(boolean needCallSkinChangeListener) {
        loadSkinInner("", "", "", needCallSkinChangeListener);
    }

    public void restoreLastSkin() {
        restoreLastSkinInner(true);
    }

    private void restoreLastSkinInner(boolean needCallSkinChangeListener) {
        String pluginPackageName = DataManager.getDefault().getPluginPackageName();
        String pluginPath = DataManager.getDefault().getPluginPath();
        String resourceSuffix = DataManager.getDefault().getResourceSuffix();

        loadSkinInner(pluginPackageName, pluginPath, resourceSuffix, needCallSkinChangeListener);
    }

    private void cancelLoadSkinTask() {
        if (mLoadSkinTask != null
                && mLoadSkinTask.getStatus() != AsyncTask.Status.FINISHED
                && !mLoadSkinTask.isCancelled()) {
            mLoadSkinTask.cancel(false);
        }

        mLoadSkinTask = null;
    }

    private void startLoadSkinTask(String pluginAPKPackageName, String pluginAPKPath, String resourceSuffix, boolean needCallSkinChangeListener) {
        mLoadSkinTask = new LoadSkinTask();
        mLoadSkinTask.setNeedCallSkinChangeListener(needCallSkinChangeListener);
        mLoadSkinTask.execute(pluginAPKPackageName, pluginAPKPath, resourceSuffix);
    }

    private class LoadSkinTask extends AsyncTask<String, Void, Void> {
        private boolean mNeedCallSkinChangeListener = false;

        void setNeedCallSkinChangeListener(boolean needCallSkinChangeListener) {
            mNeedCallSkinChangeListener = needCallSkinChangeListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mNeedCallSkinChangeListener) {
                for (OnChangeSkinListener onChangeSkinListener : mOnChangeSkinListenerList) {
                    onChangeSkinListener.onChangeSkinBegin();
                }
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            String pluginAPKPackageName = params[0];
            String pluginAPKPath = params[1];
            String resourceSuffix = params[2];

            ResourceManager.getDefault().loadSkin(pluginAPKPackageName, pluginAPKPath, resourceSuffix);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mNeedCallSkinChangeListener) {
                for (OnChangeSkinListener onChangeSkinListener : mOnChangeSkinListenerList) {
                    onChangeSkinListener.onChangeSkinSuccess();
                }
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            if (mNeedCallSkinChangeListener) {
                for (OnChangeSkinListener onChangeSkinListener : mOnChangeSkinListenerList) {
                    onChangeSkinListener.onChangeSkinError();
                }
            }
        }
    }

    private class LoadSkinDeliver implements IDeliver {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void postResourceManagerLoadSuccess(final boolean firstInit, String pluginPackageName, String pluginPath, String resourceSuffix) {
            SkinL.d("生成Resource对象成功");
            DataManager.getDefault().loadSkin(pluginPackageName, pluginPath, resourceSuffix);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (firstInit && mOnInitListener != null) {
                        mOnInitListener.onInitSuccess();
                    }
                }
            });
        }

        @Override
        public void postResourceManagerLoadError(final boolean firstInit) {
            SkinL.d("生成Resource对象失败,重新还原主题");

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (firstInit) {
                        restoreDefaultSkinInner(false);

                        if (mOnInitListener != null) {
                            mOnInitListener.onInitError();
                        }
                    } else {
                        restoreLastSkinInner(false);
                    }
                }
            });
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
