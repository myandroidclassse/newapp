package com.app.bookkeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.entify.AssetsEntify;

public class AssetDetail extends Activity {
    AssetsEntify assetsEntify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        assetsEntify = (AssetsEntify)getIntent().getSerializableExtra("Asset");


    }

    public void OnClickList(){
        Intent intent = new Intent(AssetDetail.this,MyDetail.class);

        startActivity(intent);
    }
}
