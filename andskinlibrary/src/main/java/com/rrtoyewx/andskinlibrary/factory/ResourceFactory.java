package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;
import android.text.TextUtils;

import com.rrtoyewx.andskinlibrary.constant.ConfigConstants;
import com.rrtoyewx.andskinlibrary.dataresource.PluginResource;
import com.rrtoyewx.andskinlibrary.dataresource.Resource;
import com.rrtoyewx.andskinlibrary.dataresource.LocalResources;
import com.rrtoyewx.andskinlibrary.manager.GlobalManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.io.File;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public abstract class ResourceFactory {
    private ResourceFactory() {
    }

    public static ResourceFactory newInstance() {
        return new ResourceFactoryImp();
    }

    public abstract Resource createDateResource(String pluginPackageName, String pluginPath, String suffix);


    static class ResourceFactoryImp extends ResourceFactory {
        private ResourceFactoryImp() {

        }

        @Override
        public Resource createDateResource(String pluginPackageName, String pluginPath, String suffix) {
            String packageName = GlobalManager.getDefault().getPackageName();
            Context context = GlobalManager.getDefault().getApplicationContext();

            if (!TextUtils.isEmpty(pluginPath) && !packageName.equals(pluginPath)) {
                SkinL.d("create new plugin resource");
                return new PluginResource(context, pluginPackageName, pluginPath, suffix);
            }
            SkinL.d("create new local data resource");
            return new LocalResources(context, pluginPackageName, pluginPath, suffix);
        }
    }

}
