package com.rrtoyewx.andskin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskin.adapter.MainListAdapter;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseSkinActivity {
    private ListView mContentListView;

    private MainListAdapter mListAdapter;
    private List<String> mHintMessageStrList;

    static String[] sHintMessage = {
            "更换皮肤",
            "内容展示页",
            "缺少部分资源的展示页"
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
                        break;
                    case 1:
                        startDisplayActivity();
                        break;
                    case 2:
                        startLackResourceActivity();
                        break;
                }
            }
        });
    }

    private void startChangeSkinConfigActivity() {
        startActivity(new Intent(this, ChangeSkinConfigActivity.class));
    }

    private void startDisplayActivity() {
        startActivity(new Intent(this, DisplayActivity.class));
    }

    private void startLackResourceActivity() {
        startActivity(new Intent(this, LackSomeResourceActivity.class));
    }
}
