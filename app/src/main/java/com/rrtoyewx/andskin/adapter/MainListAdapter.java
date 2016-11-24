package com.rrtoyewx.andskin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rrtoyewx.andskin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rrtoyewx on 2016/11/24.
 * MianActivity ListAdapter
 */

public class MainListAdapter extends BaseAdapter {
    private List<String> mMessageStrList = new ArrayList<>();
    private Context mContext;

    public MainListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mMessageStrList.size();
    }

    public void setMessageStrList(List<String> messageStrList) {
        mMessageStrList.clear();
        mMessageStrList.addAll(messageStrList);
    }

    @Override
    public String getItem(int position) {
        return mMessageStrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainListAdapterViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MainListAdapterViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list, parent, false);
            viewHolder.mHintMessageTV = (TextView) convertView.findViewById(R.id.tv_item_main_list_hint_message);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MainListAdapterViewHolder) convertView.getTag();
        }

        String hintMessage = mMessageStrList.get(position);
        viewHolder.mHintMessageTV.setText(hintMessage);

        return convertView;
    }

    static class MainListAdapterViewHolder {
        TextView mHintMessageTV;
    }
}
