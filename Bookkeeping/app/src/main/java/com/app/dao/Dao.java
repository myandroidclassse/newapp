package com.app.dao;

import android.database.Cursor;

import com.app.entify.AimEntify;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Dao {
    public List<AssetsEntify> getAssets(){
        List<AssetsEntify> Assets = new LinkedList<>();
        Cursor cursor = null;
//        DBHelper dbHelper = new DBHelper();
//        cursor = dbHelper.getAssets();//获取全部资产列表
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


        return Assets;
    }

    //获取账单，参数为想要获取的数量，0则为全部；
    public List<BillEntify> getBills(int num ){
        List<BillEntify> Bills = new LinkedList<>();
        Cursor cursor = null;


        if(cursor.moveToFirst()){
            while (true){
                BillEntify bill = new BillEntify();
                String[] get = {""};
                for (int i = 0; i < 5; i++) {
                        get[i] = cursor.getString(i);
                }


            }
        }





        return Bills;
    }

    public List<BillEntify> getBills(int num , int type){
        List<BillEntify> Bills = new LinkedList<>();
        Cursor cursor = null;


        if(cursor.moveToFirst()){
            while (true){
                BillEntify bill = new BillEntify();
                String[] get = {""};
                for (int i = 0; i < 5; i++) {
                    cursor.getString(i);

                }
            }
        }





        return Bills;
    }



    public double getMoneyFromDate(int type,Date begin,Date end){
        double money = 0;



        return money;
    }

    public boolean addNewBill(BillEntify bill){


        return true;
    }

    public boolean addNewAsset(AssetsEntify asset){


        return true;
    }

    public boolean addNewAim(AimEntify aim){

        return true;
    }

    public int updataBill(BillEntify bill){

        return 1;
    }

    public int updataAsset(AssetsEntify asset){

        return 1;
    }


    public void deleteBill(BillEntify bill){

    }

    public void deleteAsset(AssetsEntify asset){

    }


}
