package com.app.Adapt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.entify.AssetsEntify;

import java.util.LinkedList;
import java.util.List;

public class AccountAdapt extends BaseAdapter {
    Activity activity;
    List<AssetsEntify> List = new LinkedList<>();

    public AccountAdapt(Activity activity){
        this.activity = activity;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
