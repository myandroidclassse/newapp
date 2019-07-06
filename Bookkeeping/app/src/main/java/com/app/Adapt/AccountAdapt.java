package com.app.Adapt;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookkeeping.R;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import org.w3c.dom.Text;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class AccountAdapt extends BaseAdapter {
    Activity activity;
    List<AssetsEntify> List = new LinkedList<>();
    public boolean Empty =false;
    public AccountAdapt(Activity activity){
        this.activity = activity;
    }

    public void setList(List<AssetsEntify> List){
        this.List = List;
        if(this.List.size() == 0){
            Log.d("测试点","现在列表是空的");
            Empty = true;
            AssetsEntify assetsEntify = new AssetsEntify();
            List.add(assetsEntify);
        }else{
            Empty = false;
            Comparator<AssetsEntify> comparator = new Comparator<AssetsEntify>() {
                public int compare(AssetsEntify a1, AssetsEntify a2) {
                    // 先排年龄
                    return (int)(Double.valueOf(a1.getMoney()) - Double.valueOf(a2.getMoney()));
                }
            };
            Collections.sort(List,comparator);
        }
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
        if(Empty){
            View view = View.inflate(activity,R.layout.emptylist,null);
            Log.d("测试点","我把那个空的放进去了啊");
            ImageView imageView = view.findViewById(R.id.img_empty);
            imageView.setImageResource(R.drawable.empty);
            return view;
        }
        View v = View.inflate(activity, R.layout.account_list,null);
        ImageView img_bank = v.findViewById(R.id.img_bank);
        Properties cardproperty = getProperty("CardString.properties");
        TextView txt_name = v.findViewById(R.id.txt_name);
        TextView txt_describe = v.findViewById(R.id.txt_describe);
        TextView txt_money = v.findViewById(R.id.txt_card_money);
        int img_type = List.get(position).getType();

        switch (img_type){
            case 0:
                img_bank.setImageResource(R.drawable.bank);
                break;
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
