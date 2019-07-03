package com.app.bookkeeping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.dao.Dao;
import com.app.entify.BillEntify;

import java.util.Date;

class NewBill extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

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
        if(dao.addNewBill(this,bill)) Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"添加失败，请重试",Toast.LENGTH_SHORT).show();

    }
}
