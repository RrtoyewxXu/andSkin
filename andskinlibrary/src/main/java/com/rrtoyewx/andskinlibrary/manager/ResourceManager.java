package com.rrtoyewx.andskinlibrary.manager;

import android.os.Bundle;
import android.text.TextUtils;

import com.rrtoyewx.andskinlibrary.dataresource.DataResource;
import com.rrtoyewx.andskinlibrary.dataresource.LocalDataResources;
import com.rrtoyewx.andskinlibrary.factory.DataOperationFactory;
import com.rrtoyewx.andskinlibrary.factory.DataResourceFactory;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public class ResourceManager {
    private DataResource mDataResource;

    public void createDataResource() {
        mDataResource = DataResourceFactory.newInstance().createDateResource();
    }

    public boolean checkIfReCreateDateResouce() {
        if (mDataResource == null) {
            return true;
        } else if (mDataResource instanceof LocalDataResources) {
            if (!(TextUtils.isEmpty(GlobalManager.getDefault().getPackageName())
                    && GlobalManager.getDefault().getPackageName().equals(DataOperationManager.getDefault().getPluginPackageName()))) {
                return true;
            }
        }

        return false;
    }


}
