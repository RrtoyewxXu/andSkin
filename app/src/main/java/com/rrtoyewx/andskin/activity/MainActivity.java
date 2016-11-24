package com.rrtoyewx.andskin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskin.adapter.MainListAdapter;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;
import com.rrtoyewx.andskinlibrary.listener.OnChangeSkinListener;
import com.rrtoyewx.andskinlibrary.manager.SkinLoader;
import com.rrtoyewx.andskinlibrary.util.SkinL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseSkinActivity {
    private ListView mContentListView;

    private MainListAdapter mListAdapter;
    private List<String> mHintMessageStrList;

    static String[] sHintMessage = {
            "更换皮肤"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        bindEvent();
    }

    private void initView() {
        mContentListView = (ListView) findViewById(R.id.lv_main_content);
    }

    private void initData() {
        mHintMessageStrList = new ArrayList<>();
        mHintMessageStrList.addAll(Arrays.asList(sHintMessage));

        mListAdapter = new MainListAdapter(this);
        mListAdapter.setMessageStrList(mHintMessageStrList);
        mContentListView.setAdapter(mListAdapter);
    }

    private void bindEvent() {
        mContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startChangeSkinConfigActivity();
                }
            }
        });
    }

    private void startChangeSkinConfigActivity() {
        startActivity(new Intent(this, ChangeSkinConfigActivity.class));
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
