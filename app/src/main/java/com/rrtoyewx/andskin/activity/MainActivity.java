package com.rrtoyewx.andskin.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {
    TextView mContentTv;

    Button mChangeBlueSkinByLocalBtn;
    Button mChangeRedSkinByLocalBtn;

    Button mChangYellowSkinPluginBtn;
    Button mChangeGreenSkinPluginBtn;

    Button mRestoreSkinBtn;

    Button mErrorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        bindEvent();
    }

    private void initView() {
        mContentTv = (TextView) findViewById(R.id.tv_main_content);

        mChangeBlueSkinByLocalBtn = (Button) findViewById(R.id.btn_main_change_local_blue);
        mChangeRedSkinByLocalBtn = (Button) findViewById(R.id.btn_main_change_local_red);

        mChangYellowSkinPluginBtn = (Button) findViewById(R.id.btn_main_change_plugin_yellow);
        mChangeGreenSkinPluginBtn = (Button) findViewById(R.id.btn_main_change_plugin_green);

        mRestoreSkinBtn = (Button) findViewById(R.id.btn_main_restore_skin);
        mErrorBtn = (Button) findViewById(R.id.btn_main_error);
    }

    private void initData() {
        String content = geFileFromAssets(this, "Article.txt");
        mContentTv.setText(content);
    }

    private void bindEvent() {
        mChangeBlueSkinByLocalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin local blue");
                SkinLoader.getDefault().loadSkin("blue");
            }
        });

        mChangeRedSkinByLocalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin local red");
                SkinLoader.getDefault().loadSkin("red");
            }
        });

        mChangYellowSkinPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin plugin yellow");
                SkinLoader.getDefault().loadSkin("com.rrtoyewx.plugin", "plugin-debug.apk", "yellow");
            }
        });

        mChangeGreenSkinPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin plugin green");
                SkinLoader.getDefault().loadSkin("com.rrtoyewx.plugin", "plugin-debug.apk", "green");
            }
        });

        mRestoreSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("restore skin");
                SkinLoader.getDefault().restoreDefaultSkin();
            }
        });

        mErrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("click error");
                SkinLoader.getDefault().loadSkin("com.rrtoyewx.plugin", "plugin.apk", "green");
            }
        });

        SkinLoader.getDefault().addOnChangeSkinListener(new OnChangeSkinListener(this) {
            @Override
            public void onChangeSkinBegin() {
                SkinL.d("onChangeSkinBegin");
            }

            @Override
            public void onChangeSkinError() {
                SkinL.d("onChangeSkinError");
            }

            @Override
            public void onChangeSkinSuccess() {
                SkinL.d("onChangeSkinSuccess");
            }
        });
    }

    private String geFileFromAssets(Context context, String fileName) {
        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
                s.append("\n");
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
