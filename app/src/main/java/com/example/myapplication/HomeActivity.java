package com.example.myapplication;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adapter.HomePagerAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  {
    private RadioButton mine;
    private RadioButton net;
    private RadioButton config;
    private RadioGroup title;
    private TextView home_tv_mine;
    private TextView home_tv_net;
    private TextView home_tv_config;
    private ViewPager2 home_ViewPager;
    private ImageView home_iv_genre;
    private TextView home_tv_song;
    private ImageView home_iv_play;
    private ImageView home_iv_next;
    private ImageView home_iv_list;

    ArrayList<View> viewList;
    LocalActivityManager manager;
    RadioButton[] titleTexts=new RadioButton[3];
    TextView[] titleUnderlines=new TextView[3];
    //保存vp中加载的碎片
    ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        initView();
        manager=new LocalActivityManager(HomeActivity.this,true);
        manager.dispatchCreate(savedInstanceState);

        initPager();
        //初始化集合
        initListener();



    }

    private void initPager() {
        initDate();
        HomePagerAdapter adapter = new HomePagerAdapter(HomeActivity.this);
        home_ViewPager.setAdapter(adapter);

    }

    private void initView() {
        mine = (RadioButton) findViewById(R.id.home_rb_mine);
        net = (RadioButton) findViewById(R.id.home_rb_net);
        config = (RadioButton) findViewById(R.id.home_rb_config);
        title = (RadioGroup) findViewById(R.id.radioGroup);
        home_tv_mine=findViewById(R.id.activity_home_tv_mine);
        home_tv_net=findViewById(R.id.activity_home_tv_net);
        home_tv_config=findViewById(R.id.activity_home_tv_config);

        home_ViewPager = (ViewPager2) findViewById(R.id.layout_home_vp);
        home_iv_genre = (ImageView) findViewById(R.id.imageView5);
        home_tv_song = (TextView) findViewById(R.id.textView);
        home_iv_play = (ImageView) findViewById(R.id.imageView6);
        home_iv_next = (ImageView) findViewById(R.id.imageView7);
        home_iv_list = (ImageView) findViewById(R.id.imageView8);

    }

    private void initListener() {
        home_ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              switch(position){
                  case 0:
                      mine.setChecked(true);
                      break;
                  case 1:
                      net.setChecked(true);
                      break;
                  case 2:
                      config.setChecked(true);
                      break;
              }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                setTitleHighLight(checkedId);
//                for (int i=0;i<title.getChildCount();i++){
//                    if (checkedId==titleTexts[i].getId()){
//                        home_ViewPager.setCurrentItem(i);
//                    }
//                }
                switch (checkedId){
                    case R.id.home_rb_mine:
                        home_ViewPager.setCurrentItem(0);
                        break;
                    case R.id.home_rb_net:
                        home_ViewPager.setCurrentItem(1);
                        break;
                    case R.id.home_rb_config:
                        home_ViewPager.setCurrentItem(2);
                        break;
                }
            }
        });

    }
            //设置某个标题和下划线高亮
    private void setTitleHighLight(int check) {
        for (int i=0;i<viewList.size();i++){
            if (i==check ||titleTexts[i].getId()==check){
                titleTexts[i].setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.tab_checked));
                titleUnderlines[i].setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.tab_checked));
            }else{
                titleTexts[i].setTextColor(Color.WHITE);
                titleUnderlines[i].setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.tab_not_checked));
            }
        }

    }


    private void initDate() {
        viewList=new ArrayList<View>();
        //我的界面
        Intent it_mine=new Intent(HomeActivity.this, MineFragment.class);
        View view=getView("mineActivity",it_mine);
        viewList.add(view);
        //
        Intent it_net=new Intent(HomeActivity.this,NetActivity.class);
        viewList.add(getView("netActivity",it_net));
        //
        Intent it_config=new Intent(HomeActivity.this,ConfigActivity.class);
        viewList.add(getView("netActivity",it_config));
        titleTexts[0]=mine;
        titleTexts[1]=net;
        titleTexts[2]=config;
        titleUnderlines[0]=home_tv_mine;
        titleUnderlines[1]=home_tv_net;
        titleUnderlines[2]=home_tv_config;


        fragmentList=new ArrayList<>();
        fragmentList.add(new MineFragment());
        fragmentList.add(new NetFragment());
        fragmentList.add(new ConfigFragment());


    }
                    //我的页面
    private View getView(String id, Intent intent) {
        Window win=manager.startActivity(id,intent);//窗体管理类
        View view=win.getDecorView();
        return view;
    }

    class HomePagerAdapter extends FragmentStateAdapter {

        public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}





