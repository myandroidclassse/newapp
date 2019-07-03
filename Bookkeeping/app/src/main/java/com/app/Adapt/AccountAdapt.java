package com.app.Adapt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookkeeping.R;
import com.app.entify.AssetsEntify;

import org.w3c.dom.Text;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class AccountAdapt extends BaseAdapter {
    Activity activity;
    List<AssetsEntify> List = new LinkedList<>();

    public AccountAdapt(Activity activity){
        this.activity = activity;
    }

    public void setList(List<AssetsEntify> List){
        List.clear();
        this.List = List;
        this.notifyDataSetChanged();//动态更新视图
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public AssetsEntify getItem(int position) {
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
        View v = View.inflate(activity, R.layout.account_list,null);
        ImageView img_bank = v.findViewById(R.id.img_bank);
        Properties cardproperty = getProperty("CardString.properties");
        TextView txt_name = v.findViewById(R.id.txt_name);
        TextView txt_describe = v.findViewById(R.id.txt_describe);
        TextView txt_money = v.findViewById(R.id.txt_card_money);
        int img_type = List.get(position).getType();

        switch (img_type){
            case 1:
                img_bank.setImageResource(R.drawable.alipay);
                break;
            case 2:
                img_bank.setImageResource(R.drawable.jianshebank);
                break;
            case 3:
                img_bank.setImageResource(R.drawable.chinabank);
                break;
            case 4:
                img_bank.setImageResource(R.drawable.youzhengbank);
                break;
            case 5:
                img_bank.setImageResource(R.drawable.gongshangbank);
                break;
        }

        txt_name.setText(cardproperty.getProperty(String.valueOf(List.get(position).getType())));
        txt_describe.setText(List.get(position).getName());
        txt_money.setText("¥"+List.get(position).getMoney());
        return v;
    }
}
