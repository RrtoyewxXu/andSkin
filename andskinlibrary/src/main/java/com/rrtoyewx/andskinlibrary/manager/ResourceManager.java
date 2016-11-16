package com.rrtoyewx.andskinlibrary.manager;

import com.rrtoyewx.andskinlibrary.dataresource.PluginResource;
import com.rrtoyewx.andskinlibrary.dataresource.Resource;
import com.rrtoyewx.andskinlibrary.dataresource.LocalResources;
import com.rrtoyewx.andskinlibrary.factory.ResourceFactory;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public class ResourceManager implements ILoadSkin {
    private Resource mDataResource;

    private ResourceManager() {
    }

    public static ResourceManager getDefault() {
        return ResourceManagerHolder.sResourceManage;
    }

    private static class ResourceManagerHolder {
        static ResourceManager sResourceManage = new ResourceManager();
    }

    public void init() {
        String pluginPackageName = DataManager.getDefault().getPluginPackageName();
        String suffix = DataManager.getDefault().getResourceSuffix();
        String pluginPath = DataManager.getDefault().getPluginPath();
        SkinL.d("------------resource manager init begin----");
        SkinL.d("resource manager init pluginPackageName: " + pluginPackageName + " suffix: " + suffix + "" + " pluginPath " + pluginPath);
        SkinL.d("------------resource manager init finish----");
        smartCreateResource(pluginPackageName, pluginPath, suffix);
    }

    private boolean smartCreateResource(String pluginPackageName, String pluginPath, String suffix) {
        boolean shouldCreate = checkIfReCreateDateResource(pluginPackageName, pluginPath, suffix);
        SkinL.d("should create resource : " + shouldCreate);
        if (shouldCreate) {
            createDataResource(pluginPackageName, pluginPath, suffix);
        } else {
            mDataResource.setResourcesSuffix(suffix);
        }

        return mDataResource != null;
    }

    private void createDataResource(String pluginPackageName,String pluginPath, String suffix) {
        SkinL.d("create date resource");
        mDataResource = ResourceFactory.newInstance().createDateResource(pluginPackageName,pluginPath, suffix);
    }

    private boolean checkIfReCreateDateResource(String pluginPackageName, String pluginPath, String suffix) {
        if (mDataResource == null) {
            return true;
        } else if (mDataResource instanceof LocalResources) {
            // last resource is LocalResources and this resource is LocalResources do not need create
            return !GlobalManager.getDefault().getPackageName().equals(pluginPackageName);

        } else if (mDataResource instanceof PluginResource) {
            // last resource is PluginResource and this resources same as last do not need create
            return !(!GlobalManager.getDefault().getPackageName().equals(pluginPackageName)
                    && DataManager.getDefault().getPluginPackageName().equals(pluginPackageName)
                    && DataManager.getDefault().getPluginPath().equals(pluginPath)
                    && DataManager.getDefault().getResourceSuffix().equals(suffix));
        }

        return true;
    }

    public Resource getDataResource() {
        return mDataResource;
    }
    
    @Override
    public boolean loadSkin(String suffix) {
        boolean loadSuccess = smartCreateResource(GlobalManager.getDefault().getPackageName(), "", suffix);
        if (loadSuccess) {
            DataManager.getDefault().loadSkin(suffix);
        }

        return loadSuccess;
    }

    @Override
    public boolean loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        boolean loadSuccess = smartCreateResource(pluginPackageName, pluginPath, suffix);
        if (loadSuccess) {
            DataManager.getDefault().loadSkin(pluginPackageName, pluginPath, suffix);
        }

        return loadSuccess;
    }
}
