package com.app.Adapt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.bookkeeping.R;
import com.app.entify.BillEntify;

import java.io.IOException;
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
        List.clear();
        this.List = List;
        this.notifyDataSetChanged();//动态更新视图
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
            properties.load(activity.getAssets().open(filename));
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
        Properties Aimproperties = getProperty("AimString.properties");
        Properties Cardproperties = getProperty("CardString.properties");
        txt_aim.setText(Aimproperties.getProperty(String.valueOf(List.get(position).getAim())));
        txt_money.setText("¥  "+List.get(position).getMoney());
        txt_card.setText(Cardproperties.getProperty(String.valueOf(List.get(position).getFrom())));
        return v;
    }
}
