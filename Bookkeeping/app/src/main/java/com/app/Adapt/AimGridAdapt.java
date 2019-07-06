package com.app.Adapt;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bookkeeping.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class AimGridAdapt extends BaseAdapter {
    List<String> List = new LinkedList<>();
    Activity activity;
    private int lastPosition = -1;   //记录上一次选中的图片位置，默认不选中


    public void setLastPosition(int lastPosition){
        this.lastPosition = lastPosition;
        this.notifyDataSetChanged();//动态更新视图
    }

    public AimGridAdapt(Activity activity){
        this.activity = activity;
    }

    public void setList(){
        List.clear();
        Properties properties = getProperty("AimString.properties");
        for(int i = 0;i < properties.size() ;i++){
            List.add(properties.getProperty(String.valueOf(i+1)));
        }
        this.notifyDataSetChanged();//动态更新视图
    }

    public Properties getProperty(String filename){
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new InputStreamReader(activity.getAssets().open(filename))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public String getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(activity, R.layout.gridlayout,null);

        ImageView imageView = view.findViewById(R.id.img_aim);
        TextView textView = view.findViewById(R.id.txt_aim);
        RelativeLayout relativeLayout = view.findViewById(R.id.grid_background);
        String aim = List.get(position);
        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.food);
                break;
            case 1:
                imageView.setImageResource(R.drawable.buy);
                break;
            case 2:
                imageView.setImageResource(R.drawable.entertainment);
                break;
            case 3:
                imageView.setImageResource(R.drawable.study2);
                break;
        }

        textView.setText(aim);
        if(position == lastPosition){
            relativeLayout.setBackgroundResource(R.drawable.biankuang);
        }

        return view;
    }
}
