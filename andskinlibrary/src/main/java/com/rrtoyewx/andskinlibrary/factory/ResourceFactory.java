package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;
import android.text.TextUtils;

import com.rrtoyewx.andskinlibrary.constant.ConfigConstants;
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
        return new DataResourceFactoryImp();
    }

    public abstract Resource createDateResource(String pluginPath, String suffix);


    static class DataResourceFactoryImp extends ResourceFactory {
        private DataResourceFactoryImp() {

        }

        @Override
        public Resource createDateResource(String pluginPath, String suffix) {
            String packageName = GlobalManager.getDefault().getPackageName();
            Context context = GlobalManager.getDefault().getApplicationContext();

            if (!TextUtils.isEmpty(pluginPath)
                    && !packageName.equals(pluginPath)) {

                File file = new File(ConfigConstants.PATH_EXTERNAL_PLUGIN + "/" + pluginPath);
                if (file.exists()) {
                    SkinL.d("create new remote plugin data resource");
                    return null;
                }
                SkinL.d("remote plugin file not exists");
            }
            SkinL.d("create new local data resource");
            return new LocalResources(context, suffix);
        }
    }

}
