package com.app.dao;

import android.content.Context;
import android.database.Cursor;

import com.app.entify.AimEntify;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Dao {
    public List<AssetsEntify> getAssets(Context context){
        List<AssetsEntify> Assets = new LinkedList<>();
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        cursor = dataBase.getAssets__asc();
        if(cursor.moveToFirst()){
            while (true){
                AssetsEntify Asset = new AssetsEntify();
                String IDS = cursor.getString(0);
                int ID = Integer.valueOf(IDS);
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                String money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                Asset.setID(ID);
                Asset.setMoney(money);
                Assets.add(Asset);
                if(cursor.moveToNext()) continue;
                else break;
            }
            cursor.close();
        }

        dataBase.close();

        return Assets;
    }

    //获取账单，参数为想要获取的数量，0则为全部；
    public List<BillEntify> getBills(Context context,int num ){
        List<BillEntify> Bills = new LinkedList<>();
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        cursor = dataBase.getBill_desc();
        if(cursor.moveToFirst()){
            for (int j = 0; j < num; j++){
                BillEntify bill = new BillEntify();
                String[] get = {""};
                for (int i = 0; i < 5; i++) {
                        get[i] = cursor.getString(i);
                }
                int ID = Integer.valueOf(get[0]);
                bill.setID(ID);
                int From = Integer.valueOf(get[1]);
                bill.setFrom(From);
                bill.setMoney(get[2]);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(get[3]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bill.setDate(date);
                int aim = Integer.valueOf(get[4]);
                bill.setAim(aim);
                Bills.add(bill);
                if(cursor.moveToNext()) continue;
                else break;
            }
            cursor.close();
        }

        dataBase.close();

        return Bills;
    }

    public List<BillEntify> getBills(Context context,int num , int type){
        List<BillEntify> Bills = new LinkedList<>();
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        cursor = dataBase.getBill_desc(type+"");
        if(cursor.moveToFirst()){
            for (int j = 0; j < num; j++){
                BillEntify bill = new BillEntify();
                String[] get = {""};
                for (int i = 0; i < 5; i++) {
                    get[i] = cursor.getString(i);
                }
                int ID = Integer.valueOf(get[0]);
                bill.setID(ID);
                int From = Integer.valueOf(get[1]);
                bill.setFrom(From);
                bill.setMoney(get[2]);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(get[3]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bill.setDate(date);
                int aim = Integer.valueOf(get[4]);
                bill.setAim(aim);
                Bills.add(bill);
                if(cursor.moveToNext()) continue;
                else break;
            }
            cursor.close();
        }
        dataBase.close();
        return Bills;
    }



    public double getMoneyFromDate(Context context,int type,Date begin,Date end){
        double money = 0;
        Cursor cursor = null;
        double endd = 0;
        double begind = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();


        cursor.moveToFirst();
        endd = Integer.valueOf(cursor.getString(0));


        cursor.moveToFirst();
        begind = Integer.valueOf(cursor.getString(0));

        dataBase.close();
        money = endd - begind;
        return money;
    }

    public boolean addNewBill(Context context,BillEntify bill){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return true;
    }

    public boolean addNewAsset(Context context,AssetsEntify asset){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return true;
    }

    public boolean addNewAim(Context context,AimEntify aim){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return true;
    }

    public int updataBill(Context context,BillEntify bill){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return 1;
    }

    public int updataAsset(Context context,AssetsEntify asset){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return 1;
    }


    public void deleteBill(Context context,BillEntify bill){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
    }

    public void deleteAsset(Context context,AssetsEntify asset){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
    }


}
