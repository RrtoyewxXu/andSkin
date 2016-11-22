package com.rrtoyewx.andskinlibrary.manager;

import android.text.TextUtils;

import com.rrtoyewx.andskinlibrary.deliver.IDeliver;
import com.rrtoyewx.andskinlibrary.factory.ResourceFactory;
import com.rrtoyewx.andskinlibrary.interfaces.ILoadSkin;
import com.rrtoyewx.andskinlibrary.resource.LocalResource;
import com.rrtoyewx.andskinlibrary.resource.PluginResource;
import com.rrtoyewx.andskinlibrary.resource.Resource;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/25.
 * ResourceManager:
 * 根据pluginAPKPath,pluginAPKPackageName,pluginAPKSuffix来判断是否生成新的Resource
 *
 * @see com.rrtoyewx.andskinlibrary.resource.Resource
 */

public class ResourceManager implements ILoadSkin {
    private Resource mResource;
    private IDeliver mIDeliver;

    private ResourceManager() {
    }

    public static ResourceManager getDefault() {
        return ResourceManagerHolder.sResourceManage;
    }

    private static class ResourceManagerHolder {
        static ResourceManager sResourceManage = new ResourceManager();
    }

    void init(String pluginPackageName, String pluginPath, String pluginSuffix, IDeliver deliver) {
        SkinL.d("------------resource manager init begin----");
        SkinL.d("pluginPackageName: " + pluginPackageName);
        SkinL.d("suffix: " + pluginSuffix);
        SkinL.d("pluginPath " + pluginPath);
        mIDeliver = deliver;
        smartCreateResource(pluginPackageName, pluginPath, pluginSuffix, true);
        SkinL.d("------------resource manager init finish----");
    }

    private boolean smartCreateResource(String pluginPackageName, String pluginPath, String suffix, boolean firstInit) {
        boolean shouldCreate = checkIfReCreateDateResource(pluginPackageName, pluginPath, suffix);
        SkinL.d("should create resource : " + shouldCreate);
        if (shouldCreate) {
            try {
                createDataResource(pluginPackageName, pluginPath, suffix);
                mIDeliver.postResourceManagerLoadSuccess(firstInit, pluginPackageName, pluginPath, suffix);
            } catch (Exception e) {
                e.printStackTrace();
                mIDeliver.postResourceManagerLoadError(firstInit);
            }
        } else {
            mResource.changeResourceSuffix(suffix);
            mIDeliver.postResourceManagerLoadSuccess(false, pluginPackageName, pluginPath, suffix);
        }

        return mResource != null;
    }

    private void createDataResource(String pluginPackageName, String pluginPath, String suffix) throws Exception {
        SkinL.d("create date resource");
        mResource = ResourceFactory.newInstance().createResource(pluginPackageName, pluginPath, suffix, mIDeliver);
    }

    private boolean checkIfReCreateDateResource(String pluginPackageName, String pluginPath, String suffix) {
        if (mResource == null) {
            return true;
        } else if (mResource instanceof LocalResource) {
            return !TextUtils.isEmpty(pluginPackageName);

        } else if (mResource instanceof PluginResource) {
            return !(
                    !TextUtils.isEmpty(pluginPackageName)
                            && pluginPackageName.equals(GlobalManager.getDefault().getPluginAPKPackageName())
                            && !TextUtils.isEmpty(pluginPath)
                            && pluginPath.equals(GlobalManager.getDefault().getPluginAPKPath())
            );
        }

        return true;
    }

    public Resource getDataResource() {
        return mResource;
    }

    @Override
    public void loadSkin(String suffix) {
        loadSkin("", "", suffix);
    }

    @Override
    public void loadSkin(String pluginPackageName, String pluginPath, String suffix) {
        try {
            smartCreateResource(pluginPackageName, pluginPath, suffix, false);
        } catch (Exception e) {
            e.printStackTrace();
            mIDeliver.postResourceManagerLoadError(false);
        }
    }
}
