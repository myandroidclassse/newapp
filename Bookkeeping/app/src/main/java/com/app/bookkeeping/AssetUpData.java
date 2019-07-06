package com.app.bookkeeping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.dao.Dao;
import com.app.entify.AssetsEntify;

public class AssetUpData extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
    }






    public void upDataAsset(){

        int num = 0;
        AssetsEntify asset = new AssetsEntify();
//        asset.setID();
//        asset.setMoney();
//        asset.setName();
//        asset.setType();
//
//        Dao dao = new Dao();
//        num = dao.updataAsset(AssetUpData.this,asset,);
        if(num == 1) Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT);
        else if (num != 0)Toast.makeText(this,"更新异常",Toast.LENGTH_SHORT);
        else if (num == 0){Toast.makeText(this,"更新失败，请重试",Toast.LENGTH_SHORT);return;}
        finish();

    }
}
