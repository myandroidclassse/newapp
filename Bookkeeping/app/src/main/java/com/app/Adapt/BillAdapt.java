package com.app.Adapt;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.bookkeeping.R;
import com.app.entify.BillEntify;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class BillAdapt extends BaseAdapter {
    List<BillEntify> List = new LinkedList<>();
    Activity activity;

    public BillAdapt(Activity activity){
        this.activity = activity;
    }

    public void SetList(List<BillEntify> List){
        this.List = List;
        this.notifyDataSetChanged();//动态更新视图
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public BillEntify getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Properties getAimProperty(){
        Properties properties = new Properties();
        try {
            properties.load(activity.getAssets().open("AimString.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public Properties getCardProperty(){
        Properties properties = new Properties();
        try {
            properties.load(activity.getAssets().open("CardString.properties"));
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
        Properties Aimproperties = getAimProperty();
        Properties Cardproperties = getCardProperty();
        txt_aim.setText(Aimproperties.getProperty(String.valueOf(List.get(position).getAim())));
        txt_money.setText(List.get(position).getMoney());
        txt_card.setText(Cardproperties.getProperty(String.valueOf(List.get(position).getFrom())));
        return v;
    }
}