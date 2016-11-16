package com.rrtoyewx.andskinlibrary.deliver;

import com.rrtoyewx.andskinlibrary.resource.Resource;

/**
 * Created by Rrtoyewx on 2016/10/26.
 * loadSkin过程中可能会发生的一些的事件
 */

public interface IDeliver {
    /**
     * ResourceManager load success event
     */
    void postResourceManagerLoadSuccess(String pluginPackageName, String pluginPath, String resourceSuffix);

    /**
     * ResourceManager load error event
     */
    void postResourceManagerLoadError();

    /**
     * DataManager load success event
     */
    void postDataManagerLoadSuccess(String pluginPackageName,String pluginPath,String resourceSuffix);

    /**
     * DataManager load error event
     */
    void postDataManagerLoadError();
}
