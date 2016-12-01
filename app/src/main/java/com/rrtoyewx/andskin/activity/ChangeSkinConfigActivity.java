package com.rrtoyewx.andskin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;

public class ChangeSkinConfigActivity extends BaseSkinActivity {
    private Button mChangeLocalRedSkinBtn;
    private Button mChangeLocalGreenSkinBtn;
    private Button mChangePluginBlueSkinBtn;
    private Button mRestoreDefaultBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin_config);
        initView();
        bindEvent();
    }

    private void initView() {
        mChangeLocalRedSkinBtn = (Button) findViewById(R.id.btn_change_skin_local_red);
        mChangeLocalGreenSkinBtn = (Button) findViewById(R.id.btn_change_skin_local_green);
        mChangePluginBlueSkinBtn = (Button) findViewById(R.id.btn_change_skin_plugin_blue);
        mRestoreDefaultBtn = (Button) findViewById(R.id.btn_restore_default_skin);
    }

    private void bindEvent() {
        mChangeLocalRedSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().loadSkin("red");
            }
        });

        mChangeLocalGreenSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().loadSkin("green");
            }
        });

        mChangePluginBlueSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().loadSkin("com.rrtoyewx.plugin","plugin-debug.apk","blue");
            }
        });

        mRestoreDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().restoreDefaultSkin();
            }
        });

        SkinLoader.getDefault().addOnChangeSkinListener(new OnChangeSkinListener(mActivity) {
            @Override
            public void onChangeSkinBegin() {
                Toast.makeText(mActivity, R.string.change_skin_begin, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeSkinError() {
                Toast.makeText(mActivity, R.string.change_skin_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeSkinSuccess() {
                Toast.makeText(mActivity, R.string.change_skin_success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
