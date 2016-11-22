package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;
import android.text.TextUtils;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.resource.PluginResource;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.resource.LocalResource;
import com.rrtoyewx.andskinlibrary.manager.GlobalManager;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/25.
 * 创建资源工厂类
 */

public abstract class ResourceFactory {
    private ResourceFactory() {
    }

    public static ResourceFactory newInstance() {
        return new ResourceFactoryImp();
    }

    public abstract Resource createResource(String pluginPackageName, String pluginPath, String suffix, IDeliver deliver) throws Exception;


    static class ResourceFactoryImp extends ResourceFactory {
        private ResourceFactoryImp() {

        }

        @Override
        public Resource createResource(String pluginPackageName, String pluginPath, String suffix, IDeliver deliver) throws Exception {
            String packageName = GlobalManager.getDefault().getPackageName();
            Context context = GlobalManager.getDefault().getApplicationContext();

            if (!TextUtils.isEmpty(pluginPackageName) && !pluginPackageName.equals(packageName)) {
                SkinL.d("create new plugin resource");
                return new PluginResource(context, pluginPackageName, pluginPath, suffix);
            }
            SkinL.d("create new local data resource");
            return new LocalResource(context, pluginPackageName, pluginPath, suffix);
        }
    }

}
