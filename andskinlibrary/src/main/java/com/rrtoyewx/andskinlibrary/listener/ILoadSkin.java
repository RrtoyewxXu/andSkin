package com.rrtoyewx.andskinlibrary.listener;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public interface ILoadSkin {
    /**
     * load local skin
     *
     * @param suffix :the suffix of resource of name
     */
    boolean loadSkin(String suffix);

    /**
     * load remote plugin
     *
     * @param pluginPackageName : the name of plugin
     * @param suffix            : the suffix of resource of name
     * @param pluginPath        : the path of plugin
     */
    boolean loadSkin(String pluginPackageName, String pluginPath, String suffix);
}
