package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.MusicListAdapter;
import com.example.bean.Music;
import com.example.util.MusicUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MusicListActivity extends Activity {
    private TextView activity_music_list_tv_return, activity_music_list_tv_current_time, activity_music_list_tv_finish_time, activity_music_list_tv_song,
            activity_music_list_tv_singer, activity_music_list_tv_duration;
    private ImageView activity_music_list_iv_return, activity_music_list_iv_more, activity_music_list_iv_loop,
            activity_music_list_iv_search, activity_music_list_iv_prev, activity_music_list_iv_play, activity_music_list_iv_next;
    private SeekBar activity_music_list_sb;
    private GridView gridView;
    private TextView t2;

    ArrayList<Music> list;

    int pos;
    MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

//        initView();
        activity_music_list_iv_return = findViewById(R.id.activity_music_list_iv_return);
        activity_music_list_tv_return = findViewById(R.id.activity_music_list_tv_return);
        activity_music_list_iv_more = findViewById(R.id.activity_music_list_iv_more);
        activity_music_list_iv_loop = findViewById(R.id.activity_music_list_iv_loop);
        activity_music_list_iv_search = findViewById(R.id.activity_music_list_iv_search);

        /*
         * */
        gridView = findViewById(R.id.activity_music_list_rv);
        activity_music_list_tv_current_time = findViewById(R.id.activity_music_list_tv_current_time);
        activity_music_list_tv_finish_time = findViewById(R.id.activity_music_list_tv_finish_time);
        activity_music_list_sb = findViewById(R.id.activity_music_list_sb);

        //listView=findViewById(R.id.listView);
        activity_music_list_tv_song = findViewById(R.id.music_list_item_tv_song);
        activity_music_list_tv_singer = findViewById(R.id.music_list_item_tv_singer);
        activity_music_list_tv_duration = findViewById(R.id.activity_music_list_tv_duration);

        activity_music_list_iv_prev = findViewById(R.id.activity_music_list_iv_prev);
        activity_music_list_iv_play = findViewById(R.id.activity_music_list_iv_play);
        activity_music_list_iv_next = findViewById(R.id.activity_music_list_iv_next);

        initDate();

        initListener();

        MusicListAdapter adapter = new MusicListAdapter(MusicListActivity.this, list);
        //listView.setAdapter(adapter);
        gridView.setAdapter(adapter);
        //跳转回主界面
        activity_music_list_tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initListener() {
        activity_music_list_tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                play(position);

                setMusicInfo(pos);
                activity_music_list_iv_play.setImageResource(R.drawable.icon_pause);
            }
            

            private void play(int position) {
                pos = position;
                mp.reset();
                try {
                    mp.prepare();

                    mp.setDataSource(list.get(pos).getSongPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        activity_music_list_iv_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrev();
                setMusicInfo(pos);
            }


            private void playPrev() {
                if (pos == 0) {
                    pos = list.size() - 1;
                } else {
                    pos--;
                }

            }
        });
        activity_music_list_iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
                setMusicInfo(pos);
            }

            private void playNext() {

            }

            private void play(int pos) {
            }

        });
        activity_music_list_iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying() == true) {
                    mp.pause();
                    activity_music_list_iv_play.setImageResource(R.drawable.icon_play);
                } else {
                    play(pos);
                    activity_music_list_iv_play.setImageResource(R.drawable.icon_pause);
                }
            }

            private void play(int pos) {
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNext();
            }

            private void playNext() {
            }
        });
    }

    private void setMusicInfo(int position) {
        Music m = list.get(position);

        activity_music_list_tv_song.setText("歌曲" + m.getSong());
        activity_music_list_tv_singer.setText("歌手" + m.getSinger());
        activity_music_list_tv_duration.setText("时长" + getFormatTime(mp.getDuration()));
        activity_music_list_tv_finish_time.setText(getFormatTime(mp.getDuration()));
    }

    private String getFormatTime(int duration) {
        DecimalFormat df = new DecimalFormat("#00");

        int min = duration / 1000 / 60;//分
        int sec = duration % 60000 / 1000;//秒
        return df.format(min) + ":" + df.format(sec);
    }

    private void initDate() {//
//        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Music item = new Music();
//            item.setSong("Song" + i);
//            item.setSinger("Singer" + i);
//            list.add(item);
//        }
        list= MusicUtil.scanMusic(this);
    }
//
}
