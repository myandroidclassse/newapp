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
//        DBHelper dbHelper = new DBHelper();
//        Cursor cursor = dbHelper.getAssets();//获取全部资产列表
//        if(cursor.moveToFirst()){
//            for(int i = 0 ; ;i++){
//                AssetsEntify Asset = new AssetsEntify();
//                String IDS = cursor.getString(0);
//                int IDS =
//            }
//        }




        return Assets;
    }

    //获取账单，参数为想要获取的数量，0则为全部；
    public List<BillEntify> getBills(int num){
        List<BillEntify> Bills = new LinkedList<>();





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
