package com.app.Adapt;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bookkeeping.R;

import java.util.LinkedList;
import java.util.List;

public class ChooseList extends BaseAdapter {
    List<String> List = new LinkedList<>();
    Activity activity;
    public boolean Empty = false;

    public void setList( List<String> List){
        this.List = List;
        if(this.List.size() == 0){
            Log.d("测试点","现在列表是空的");
            Empty = true;
            String s = "空的";
            List.add(s);
        }else{
            Empty = false;
        }
        this.notifyDataSetChanged();//动态更新视图
    }

    public ChooseList(Activity activity){
        this.activity = activity;
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
        if(Empty){
            View view = View.inflate(activity,R.layout.emptylist,null);
            ImageView imageView = view.findViewById(R.id.img_empty);
            imageView.setImageResource(R.drawable.empty);
            Log.d("测试点","我把那个空的放进去了啊");
            return view;
        }
        View adapt = View.inflate(activity,R.layout.choose_list_adapt,null);
        TextView name = adapt.findViewById(R.id.txt_list_bank_name);
        ImageView imageView = adapt.findViewById(R.id.img_list);
        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.bank);
                break;
            case 1:
                imageView.setImageResource(R.drawable.alipay);
                break;
            case 2:
                imageView.setImageResource(R.drawable.jianshebank);
                break;
            case 3:
                imageView.setImageResource(R.drawable.chinabank);
                break;
            case 4:
                imageView.setImageResource(R.drawable.youzhengbank);
                break;
            case 5:
                imageView.setImageResource(R.drawable.gongshangbank);
                break;
        }
        name.setText(List.get(position));
        return adapt;
    }
}
