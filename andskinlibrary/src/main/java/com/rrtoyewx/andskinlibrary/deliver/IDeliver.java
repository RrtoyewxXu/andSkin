package com.rrtoyewx.andskinlibrary.deliver;

import android.support.annotation.MainThread;

/**
 * Created by Rrtoyewx on 2016/10/26.
 * loadSkin过程中可能会发生的一些的事件
 * <p>
 * 分线程
 *
 * @see #postDataManagerLoadSuccess(String, String, String) 存储需要更换皮肤资源的信息成功
 * @see #postDataManagerLoadError() 存储需要更换资源皮肤信息失败，上一次的信息和这个次信息一样的时候
 * @see #postResourceManagerLoadSuccess(boolean, String, String, String) 生成Resource对象成功
 * @see #postResourceManagerLoadError(boolean) 生成Resource失败
 * <p>
 * 主线程
 * @see #postGetAllResourceSuccessOnMainThread(String, String, String) 获取所有的资源文件成功
 * @see #postGetResourceErrorOnMainThread() 获取资源文件失败
 */

public interface IDeliver {
    /**
     * ResourceManager load success event
     */
    void postResourceManagerLoadSuccess(boolean firstInit, String pluginPackageName, String pluginPath, String resourceSuffix);

    /**
     * ResourceManager load error event
     */
    void postResourceManagerLoadError(boolean firstInit);

    /**
     * DataManager load success event
     */
    void postDataManagerLoadSuccess(String pluginPackageName, String pluginPath, String resourceSuffix);

    /**
     * DataManager load error event
     */
    void postDataManagerLoadError();

    /**
     * Resource getResource value error event
     */
    @MainThread
    void postGetResourceErrorOnMainThread();

    @MainThread
    void postGetAllResourceSuccessOnMainThread(String pluginPackageName, String pluginPath, String resourceSuffix);
}
