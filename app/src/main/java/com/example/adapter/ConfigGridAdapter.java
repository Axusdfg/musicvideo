package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.MineGridBean;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ConfigGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MineGridBean> data;

    private ImageView config_grid_item_iv;
    private TextView config_grid_item_tv;

    public ConfigGridAdapter(Context context,ArrayList<MineGridBean> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.config_grid_item,null);

        config_grid_item_iv=view.findViewById(R.id.config_grid_item_iv);
        config_grid_item_tv=view.findViewById(R.id.config_grid_item_tv);

        config_grid_item_iv.setImageResource(data.get(position).getImgSrc());
        config_grid_item_tv.setText(data.get(position).getTitle());

        return view;
    }
}
