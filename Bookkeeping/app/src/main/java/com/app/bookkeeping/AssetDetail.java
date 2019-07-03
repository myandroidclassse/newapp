package com.app.bookkeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AssetDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);



    }

    public void OnClickList(){
        Intent intent = new Intent(AssetDetail.this,MyDetail.class);
        startActivity(intent);
    }
}
