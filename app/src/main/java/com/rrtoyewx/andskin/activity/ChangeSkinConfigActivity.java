package com.rrtoyewx.andskin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;

public class ChangeSkinConfigActivity extends BaseSkinActivity {
    private Button mChangeLocalRedSkinBtn;
    private Button mChangeLocalBlueSkinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin_config);
        initView();
        bindEvent();
    }

    private void initView(){
        mChangeLocalRedSkinBtn = (Button) findViewById(R.id.btn_change_skin_local_red);
        mChangeLocalBlueSkinBtn = (Button) findViewById(R.id.btn_change_skin_local_blue);
    }

    private void bindEvent(){
        mChangeLocalRedSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().loadSkin("red");
            }
        });

        mChangeLocalBlueSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinLoader.getDefault().loadSkin("blue");
            }
        });
    }
}
