package com.rrtoyewx.andskinlibrary.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.interfaces.IChangeSkin;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.listener.OnInitLoadPluginListener;
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
 * <p>
 * 流程
 * - loadSkin命令
 * - DataManager保存当前的Skin资源相关信息
 * - 生成Resource对象
 * - 通知所有的观察者
 * - 刷新对外暴露的GlobalManager的相关信息
 * 在这其中如果出现error，则通过IDeliver进行分发各个事件
 */

public class SkinLoader implements ILoadSkin {
    private List<IChangeSkin> mChangeSkinObserverList = new ArrayList<>();

    private IDeliver mLoadSkinDeliver;
    private LoadSkinTask mLoadSkinTask;

    private List<OnChangeSkinListener> mOnChangeSkinListenerList = new ArrayList<>();
    private OnInitLoadPluginListener mOnInitLoadSkinResourceListener;

    private boolean mJustRegisteredFlag = false;

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

    public void setOnInitListener(OnInitLoadPluginListener listener) {
        this.mOnInitLoadSkinResourceListener = listener;
    }

    public void register(IChangeSkin changeSkinObserver) {
        if (mChangeSkinObserverList.contains(changeSkinObserver)) {
            throw new IllegalArgumentException(changeSkinObserver.getClass().getSimpleName() + "also register!! please check");
        }
        mChangeSkinObserverList.add(changeSkinObserver);

        mJustRegisteredFlag = true;
        boolean findResourceSuccessFlag = notifyChangeSkinObserverToFindResource(changeSkinObserver);
        if (findResourceSuccessFlag) {
            notifyChangeSkinObserverToApplySkin(changeSkinObserver);
        } else {
            mLoadSkinDeliver.postDataManagerLoadError();
        }
        mJustRegisteredFlag = false;
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
        restoreDefaultSkinInner(true);
    }

    private void restoreDefaultSkinInner(boolean needCallSkinChangeListener) {
        loadSkinInner("", "", "", needCallSkinChangeListener);
    }

    public void restoreLastSkin() {
        restoreLastSkinInner(true);
    }

    private void restoreLastSkinInner(boolean needCallSkinChangeListener) {
        String lastPluginPackageName = GlobalManager.getDefault().getPluginAPKPackageName();
        String lastPluginPath = GlobalManager.getDefault().getPluginAPKPath();
        String lastResourceSuffix = GlobalManager.getDefault().getResourceSuffix();

        loadSkinInner(lastPluginPackageName, lastPluginPath, lastResourceSuffix, needCallSkinChangeListener);
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

    private boolean notifyAllChangeSkinObserverListToFindResource() {
        boolean changedSkinSuccess = true;
        SkinL.d("通知所有的观察者查找资源");
        for (IChangeSkin changeSkin : mChangeSkinObserverList) {
            changedSkinSuccess = changeSkin.findResource();
            if (!changedSkinSuccess) {
                break;
            }
        }
        return changedSkinSuccess;
    }

    private void notifyAllChangeSkinObserverListToApplySKin() {
        SkinL.d("通知所有的组件进行换肤");
        for (IChangeSkin changeSkin : mChangeSkinObserverList) {
            changeSkin.changeSkin();
        }
    }

    private boolean notifyChangeSkinObserverToFindResource(IChangeSkin changeSkinObserver) {
        SkinL.d("通知当前观察者进行查找资源");
        return changeSkinObserver.findResource();
    }

    private void notifyChangeSkinObserverToApplySkin(IChangeSkin changeSkinObserver) {
        SkinL.d("通知当前观察者进行换肤");
        changeSkinObserver.changeSkin();
    }

    private class LoadSkinTask extends AsyncTask<String, Void, Void> {
        private boolean mNeedCallSkinChangeListenerFlag = false;

        void setNeedCallSkinChangeListener(boolean needCallSkinChangeListener) {
            mNeedCallSkinChangeListenerFlag = needCallSkinChangeListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mNeedCallSkinChangeListenerFlag) {
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

            DataManager.getDefault().loadSkin(pluginAPKPackageName, pluginAPKPath, resourceSuffix);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mNeedCallSkinChangeListenerFlag) {
                for (OnChangeSkinListener onChangeSkinListener : mOnChangeSkinListenerList) {
                    onChangeSkinListener.onChangeSkinSuccess();
                }
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            if (mNeedCallSkinChangeListenerFlag) {
                for (OnChangeSkinListener onChangeSkinListener : mOnChangeSkinListenerList) {
                    onChangeSkinListener.onChangeSkinError();
                }
            }
        }
    }

    private class LoadSkinDeliver implements IDeliver {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void postDataManagerLoadSuccess(String pluginPackageName, String pluginPath, String resourceSuffix) {
            SkinL.d("保存本次换肤的相关信息成功");
            ResourceManager.getDefault().loadSkin(pluginPackageName, pluginPath, resourceSuffix);
        }

        @Override
        public void postDataManagerLoadError() {
            SkinL.d("保存本次换肤的相关信息和上次一样");
        }

        @Override
        public void postResourceManagerLoadSuccess(final boolean firstInit, final String pluginPackageName, final String pluginPath, final String resourceSuffix) {
            SkinL.d("生成Resource对象成功");

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (firstInit && mOnInitLoadSkinResourceListener != null) {
                        mOnInitLoadSkinResourceListener.onInitResourceSuccess();
                    } else {
                        boolean changedSkinSuccess = notifyAllChangeSkinObserverListToFindResource();

                        if (changedSkinSuccess) {
                            postGetAllResourceSuccessOnMainThread(pluginPackageName, pluginPath, resourceSuffix);
                        } else {
                            postGetResourceErrorOnMainThread();
                        }
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
                        SkinL.d("还原默认皮肤");
                        restoreDefaultSkinInner(false);

                        if (mOnInitLoadSkinResourceListener != null) {
                            mOnInitLoadSkinResourceListener.onInitResourceError();
                        }
                    } else {
                        SkinL.d("还原上一套皮肤");
                        restoreLastSkinInner(false);
                    }
                }
            });
        }


        @Override
        public void postGetResourceErrorOnMainThread() {
            SkinL.d("查找资源失败");
            if (mJustRegisteredFlag) {
                SkinL.d("还原默认皮肤");
                restoreDefaultSkinInner(false);
            } else {
                SkinL.d("还原上一套皮肤");
                restoreLastSkinInner(false);
            }
        }

        @Override
        public void postGetAllResourceSuccessOnMainThread(String pluginPackageName, String pluginPath, String resourceSuffix) {
            SkinL.d("查找所有资源成功");
            GlobalManager.getDefault().flushPluginInfos(pluginPackageName, pluginPath, resourceSuffix);
            notifyAllChangeSkinObserverListToApplySKin();
        }
    }
}
