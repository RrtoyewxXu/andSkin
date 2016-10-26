package com.rrtoyewx.andskinlibrary.listener;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public interface ILoadSkin {
    /**
     * load local skon
     * @param suffix
     */
    void changeSkin(String suffix);

    /**
     * load remote plugin
     * @param pluginName
     * @param suffix
     */
    void changeSkin(String pluginName, String suffix);
}
