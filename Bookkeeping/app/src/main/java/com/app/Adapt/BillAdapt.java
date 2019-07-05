package com.app.Adapt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookkeeping.R;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class BillAdapt extends BaseAdapter {
    List<BillEntify> List = new LinkedList<>();
    List<AssetsEntify> Assets = new LinkedList<>();
    Activity activity;

    public BillAdapt(Activity activity){
        this.activity = activity;
    }

    public void setList(List<BillEntify> List){
        this.List = List;
        this.notifyDataSetChanged();//动态更新视图
    }

    public void setAssetList(List<AssetsEntify> List){
        Assets = List;
    }


    public AssetsEntify getAsset(int position){
        for(int i = 0;i<Assets.size();i++){
            if(Assets.get(i).getID() == List.get(position).getFrom()){
                return Assets.get(i);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public BillEntify getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(activity, R.layout.bill_list,null);
        TextView txt_aim = v.findViewById(R.id.txt_aim);
        TextView txt_money = v.findViewById(R.id.txt_money);
        TextView txt_card = v.findViewById(R.id.txt_card);
        TextView txt_data = v.findViewById(R.id.txt_data);
        Properties Aimproperties = getProperty("AimString.properties");
        ImageView img = v.findViewById(R.id.img_aim);
        img.setImageResource(R.drawable.money);
//        Properties Cardproperties = getProperty("CardString.properties");
        txt_aim.setText(Aimproperties.getProperty(String.valueOf(List.get(position).getAim())));
        txt_money.setText("¥  "+List.get(position).getMoney());
        txt_card.setText(findCard(List.get(position).getFrom()));
        txt_data.setText(List.get(position).getDateString().substring(0,16));
        return v;
    }

    public String findCard(int from){
        for(int i = 0;i<Assets.size();i++){
            if(Assets.get(i).getID() == from){
                return Assets.get(i).getName();
            }
        }
        return "未指定";
    }
}
