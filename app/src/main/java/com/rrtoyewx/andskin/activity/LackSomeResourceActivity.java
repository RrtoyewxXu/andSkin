package com.rrtoyewx.andskin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;

/**
 * 当前activity 存在部分资源没有的，测试当加载这个页面的时候，资源找不到，自动还原上一个主题
 */
public class LackSomeResourceActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lack_some_resource);
    }
}
