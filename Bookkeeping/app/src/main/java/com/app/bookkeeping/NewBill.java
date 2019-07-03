package com.app.bookkeeping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Adapt.AimGridAdapt;
import com.app.Adapt.ChooseList;
import com.app.dao.Dao;
import com.app.entify.BillEntify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

class NewBill extends Activity {

    private View inflate;
    private Dialog dialog;
    CardView choose_bank;
    ImageView img_bank;
    TextView bank_name;
    int type;
    Button save;
    Button qiut;
    Button delete;
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        choose_bank = findViewById(R.id.bill_bank);
        img_bank = findViewById(R.id.bill_img_bank);
        bank_name = findViewById(R.id.txt_bill_bank_name);
        gridView = findViewById(R.id.aim_list);
        img_bank.setImageResource(R.drawable.bank);
        save = findViewById(R.id.btn_bill_save);
        qiut = findViewById(R.id.btn_bill_qiut);
        delete = findViewById(R.id.btn_bill_delete);
        delete.setVisibility(View.GONE);
        setDialog();
        initData();


    }

    void initData() {
        AimGridAdapt aimGridAdapt = new AimGridAdapt(NewBill.this);
        aimGridAdapt.setList();
        gridView.setAdapter(aimGridAdapt);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AimGridAdapt adapt = (AimGridAdapt) gridView.getAdapter();
                adapt.setLastPosition(position);
            }
        });
    }




    public void setDialog(){
        choose_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(NewBill.this,R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(NewBill.this).inflate(R.layout.choose_list, null);
                //初始化控件
                Properties properties = getProperty("CardString.properties");
                List<String> List = new LinkedList<>();
                for(int i = 0 ;i<properties.size();i++){
                    List.add(properties.getProperty(String.valueOf(i)));
                }
                ChooseList chooseList = new ChooseList(NewBill.this);
                chooseList.setList(List);
                final ListView list_of_bank = inflate.findViewById(R.id.choose_list);
                list_of_bank.setAdapter(chooseList);
                //将布局设置给Dialog
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity( Gravity.BOTTOM);
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
                        bank_name.setText(name);
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
            properties.load(new BufferedReader(new InputStreamReader(NewBill.this.getAssets().open(filename))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //点击确认按钮后添加账单(缺少数据捕获 by王家淇)
    public void addBill(){
        BillEntify bill = new BillEntify();
        String money = "";
        int aim = 0;
        int from = 0;
        Date date = new Date();





        bill.setMoney(money);
        bill.setAim(aim);
        bill.setFrom(from);
        bill.setDate(date);

        Dao dao = new Dao();
        if(dao.addNewBill(this,bill)!=-1) Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"添加失败，请重试",Toast.LENGTH_SHORT).show();

        finish();
    }

}
