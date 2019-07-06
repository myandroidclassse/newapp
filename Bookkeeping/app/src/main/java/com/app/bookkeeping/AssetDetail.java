package com.app.bookkeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.Adapt.BillAdapt;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.util.LinkedList;
import java.util.List;

public class AssetDetail extends Activity {
    AssetsEntify assetsEntify;
    ListView List;
    BillAdapt billAdapt;
    TextView txt_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        assetsEntify = (AssetsEntify)getIntent().getSerializableExtra("Asset");
        List = findViewById(R.id.asset_bill_list);
        billAdapt = new BillAdapt(AssetDetail.this);
        txt_money = findViewById(R.id.txt_asset_detail_money);
        txt_money.setText(assetsEntify.getMoney());
        Dao dao = new Dao();

        List<BillEntify> ListofAsset = new LinkedList<>();
        ListofAsset = dao.getBills(AssetDetail.this,0);
        for(int i=0;i<ListofAsset.size();i++){
            if(ListofAsset.get(i).getFrom() != assetsEntify.getID()){
                ListofAsset.remove(i);
            }
        }

        billAdapt.setList(ListofAsset);
        List<AssetsEntify> theAsset = new LinkedList<>();
        theAsset.add(assetsEntify);
        billAdapt.setAssetList(theAsset);

        List.setAdapter(billAdapt);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillAdapt billAdapt = (BillAdapt)List.getAdapter();
                BillEntify billEntify = billAdapt.getItem(position);
                AssetsEntify assetsEntify = billAdapt.getAsset(position);
                Intent intent = new Intent(AssetDetail.this,MyDetail.class);
                intent.putExtra("Bill",billEntify);
                if(billEntify.getFrom() != 0){
                    intent.putExtra("Asset",assetsEntify);
                }
                startActivity(intent);
            }
        });
    }

}
