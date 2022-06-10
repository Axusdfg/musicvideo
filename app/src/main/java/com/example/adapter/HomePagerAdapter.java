package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class HomePagerAdapter extends PagerAdapter {
    ArrayList<View> viewList;

    public HomePagerAdapter(ArrayList<View> viewList) {
        this.viewList = viewList;
    }

    //一共有多少格滑动视图
    @Override
    public int getCount() {
        return viewList.size();
    }
        //判断传来的两个参数是否是同一个对象
    @Override
    public boolean isViewFromObject( View view,Object object) {
        return view==object;
    }

//    @NonNull
    //创建新视图
    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
    //销毁position视图
    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView(viewList.get(position));
    }
}
