package com.app.bookkeeping;


import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import android.widget.Toast;

import com.app.Adapt.ChooseList;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

class NewAsset extends AppCompatActivity {
    CardView choose_bank;
    private View inflate;
    private Dialog dialog;
    TextView bank_type;
    EditText editname;
    EditText editmoney;
    ImageView img_bank;
    Button save,qiut;
    int type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        choose_bank = findViewById(R.id.bank);
        bank_type = findViewById(R.id.bank_type);
        editname = findViewById(R.id.edit_name);
        editmoney = findViewById(R.id.edit_money);
        img_bank = findViewById(R.id.img_choose_bank);
        save = findViewById(R.id.btn_add_asset_save);
        qiut = findViewById(R.id.btn_add_asset_qiut);
        setDialog();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAsset();
            }
        });
        qiut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void setDialog(){
        choose_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(NewAsset.this,R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(NewAsset.this).inflate(R.layout.choose_list, null);
                //初始化控件
                Properties properties = getProperty("CardString.properties");
                List<String> List = new LinkedList<>();
                for(int i = 0 ;i<properties.size();i++){
                    List.add(properties.getProperty(String.valueOf(i)));
                }
                ChooseList chooseList = new ChooseList(NewAsset.this);
                chooseList.setList(List);

                final ListView list_of_bank = inflate.findViewById(R.id.choose_list);
                list_of_bank.setAdapter(chooseList);
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
                list_of_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChooseList alist = (ChooseList) list_of_bank.getAdapter();
                        String name = alist.getItem(position);
                        bank_type.setText(name);
                        switch (position){
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
                        type = position;
                        dialog.cancel();
                    }
                });
            }
        });
    }

    public Properties getProperty(String filename){
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new InputStreamReader(NewAsset.this.getAssets().open(filename))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    //点击确认按钮后添加账单(缺少数据捕获 by王家淇)
    public void addAsset(){
        AssetsEntify asset = new AssetsEntify();
        asset.setName(editname.getText().toString());
        asset.setType(type);
        asset.setMoney(editmoney.getText().toString());
        Dao dao = new Dao();

        if(dao.addNewAsset(NewAsset.this,asset) != -1) {
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this,"添加失败，请重试",Toast.LENGTH_SHORT).show();
        }
    }
}
