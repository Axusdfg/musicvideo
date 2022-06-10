package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.Music;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Music> list;

    public MusicListAdapter() {
    }

    public MusicListAdapter(Context context, ArrayList<Music> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.music_list_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Music music=list.get(i);
        holder.music_list_item_tv_song.setText(music.getSinger());
        holder.music_list_item_tv_singer.setText(music.getSong());
        return view;
    }
    public static class ViewHolder {
        public View rootView;
        public TextView music_list_item_tv_singer;
        public TextView music_list_item_tv_song;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.music_list_item_tv_singer = rootView.findViewById(R.id.music_list_item_tv_singer);
            this.music_list_item_tv_song = rootView.findViewById(R.id.music_list_item_tv_song);
        }
    }
}
