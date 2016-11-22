package com.rrtoyewx.andskin.applcation;

import com.rrtoyewx.andskinlibrary.base.BaseSkinApplication;
import com.rrtoyewx.andskinlibrary.listener.OnInitLoadPluginListener;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/25.
 */

public class SampleApplication extends BaseSkinApplication {

    @Override
    public void beforeInit() {
        SkinL.d("beforeInit");
        SkinLoader.getDefault().setOnInitListener(new OnInitLoadPluginListener() {
            @Override
            public void onInitResourceError() {
                SkinL.d("init last theme error,ready loading default theme");
            }

            @Override
            public void onInitResourceSuccess() {
                SkinL.d("init last theme success");
            }
        });
    }

    @Override
    public void afterInit() {
        SkinL.d("afterInit");
    }
}
