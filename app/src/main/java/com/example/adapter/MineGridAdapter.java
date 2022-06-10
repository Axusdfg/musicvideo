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
//“我的”页面九宫格的适配器
public class MineGridAdapter extends BaseAdapter{
    //定义成员变量
    Context context;        //上下文对象
    ArrayList<MineGridBean> dataList;//数据清单

    //创建构造函数
    public MineGridAdapter(Context context, ArrayList<MineGridBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    //重写BASEAdapter中的四个方法
    @Override
    public int getCount() {
        return dataList.size();//获取小格数量（列表长度）
    }

    //获取第position项目的数据
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    //获取第position项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取第position条数据显示出来的view对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.布局加载器（通道膨胀器）：读取小布局，并转化为一个view对象
        //从当前页面中获取一个通道膨胀器
        LayoutInflater inflater = LayoutInflater.from(context);
        //利用通道膨胀器把小布局“膨胀”为一个view
        View view = inflater.inflate(R.layout.mine_grid_item,null);

        //2.找到view中的控件，并填充数据
        ImageView iv = view.findViewById(R.id.mine_grid_item_iv);
        TextView tv = view.findViewById(R.id.mine_grid_item_tv);
        //读取小格数据
        MineGridBean item = dataList.get(position);
        iv.setImageResource(item.getImgSrc());
        tv.setText(item.getTitle());
//3.return返回我们已经填充好的view 对象
        return view;
    }


}
