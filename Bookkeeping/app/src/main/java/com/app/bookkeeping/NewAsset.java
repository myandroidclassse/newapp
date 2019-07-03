package com.app.bookkeeping;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.dao.Dao;
import com.app.entify.AssetsEntify;

import java.util.Date;

class NewAsset extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
    }

    //点击确认按钮后添加账单(缺少数据捕获 by王家淇)
    public void addAsset(){
        AssetsEntify asset = new AssetsEntify();
        String name = "";
        int from = 0;
        int type = 0;
        String money = "";




        asset.setMoney(money);
        asset.setName(name);
        asset.setType(type);
        Dao dao = new Dao();
        if(dao.addNewAsset(asset)) Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"添加失败，请重试",Toast.LENGTH_SHORT).show();
    }
}
