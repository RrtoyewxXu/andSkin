package com.rrtoyewx.andskin.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rrtoyewx.andskin.R;
import com.rrtoyewx.andskinlibrary.base.BaseSkinActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DisplayActivity extends BaseSkinActivity {
    private TextView mContentTextTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);
        initView();
    }

    private void initView() {
        mContentTextTV = (TextView) findViewById(R.id.tv_display_text_content);

        mContentTextTV.setText(getFileFromAssets(this, "Article.txt"));
    }

    private String getFileFromAssets(Context context, String fileName) {
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
