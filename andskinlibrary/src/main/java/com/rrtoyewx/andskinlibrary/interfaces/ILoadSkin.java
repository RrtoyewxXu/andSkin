package com.rrtoyewx.andskinlibrary.interfaces;

/**
 * Created by Rrtoyewx on 2016/10/25.
 * ILoadSkin:
 * 1,加载本地的皮肤
 * 2,加载外部APK的皮肤
 */

public interface ILoadSkin {
    /**
     * load local skin
     *
     * @param suffix :the suffix of resource of name
     */
    void loadSkin(String suffix);

    /**
     * load remote plugin
     *
     * @param pluginPackageName : the name of plugin
     * @param suffix            : the suffix of resource of name
     * @param pluginPath        : the path of plugin
     */
    void loadSkin(String pluginPackageName, String pluginPath, String suffix);
}
