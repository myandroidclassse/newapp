package com.app.bookkeeping;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.Adapt.BillAdapt;
import com.app.Adapt.ChooseList;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class AssetDetail extends Activity {
    AssetsEntify assetsEntify;
    ListView List;
    BillAdapt billAdapt;
    TextView txt_money;
    CardView assetdetail;

    CardView choose_bank;
    EditText editname;
    EditText editmoney;
    Button save,qiut;
    int type;


    private View inflate;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        assetsEntify = (AssetsEntify)getIntent().getSerializableExtra("Asset");
        List = findViewById(R.id.asset_bill_list);
        billAdapt = new BillAdapt(AssetDetail.this);
        txt_money = findViewById(R.id.txt_asset_detail_money);
        txt_money.setText(assetsEntify.getMoney());
        assetdetail = findViewById(R.id.card_view_asset_detial);
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


        assetdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();
            }
        });
    }


    public void setDialog(){
        dialog = new Dialog(AssetDetail.this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(AssetDetail.this).inflate(R.layout.activity_add_account, null);
        //初始化控件

        choose_bank = inflate.findViewById(R.id.bank);
        choose_bank.setVisibility(View.GONE);
        editname = inflate.findViewById(R.id.edit_name);
        editname.setText(assetsEntify.getName());
        editmoney = inflate.findViewById(R.id.edit_money);
        editmoney.setText(assetsEntify.getMoney());
        save = inflate.findViewById(R.id.btn_add_asset_save);
        qiut = inflate.findViewById(R.id.btn_add_asset_qiut);
        qiut.setText("删除");


        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = dialogWindow.getWindowManager().getDefaultDisplay().getWidth();
        //    将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

}
