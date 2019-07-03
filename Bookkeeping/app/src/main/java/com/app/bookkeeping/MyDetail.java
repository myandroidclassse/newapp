package com.app.bookkeeping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.dao.Dao;
import com.app.entify.BillEntify;

import java.util.Date;

class MyDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

    }



    //点击确认按钮后修改账单(缺少数据捕获 by王家淇)
    public void updataBill(){
        BillEntify bill = new BillEntify();
        int ID = 0;
        String money = "";
        int aim = 0;
        int from = 0;
        Date date = new Date();




        bill.setID(ID);
        bill.setMoney(money);
        bill.setAim(aim);
        bill.setFrom(from);
        bill.setDate(date);

        Dao dao = new Dao();
        if(dao.updataBill(this,bill)==1) Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"修改失败，请重试",Toast.LENGTH_SHORT).show();

    }

    public void deleteBill(){
        BillEntify bill = new BillEntify();
        int ID = 0;




        bill.setID(ID);
        Dao dao = new Dao();
        dao.deleteBill(this,bill);

    }

}
