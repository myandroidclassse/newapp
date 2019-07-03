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
        cursor = dataBase.getBill_desc("_from=\""+type+"\"");
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
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();


        if(cursor.moveToFirst()){
            while (true){
                String one = cursor.getString(2);
                money += Double.valueOf(one);

                if(cursor.moveToNext()) continue;
                else break;
            }
            cursor.close();
        }

        dataBase.close();
        return money;
    }

    public int addNewBill(Context context,BillEntify bill){
        int ID = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        ID=dataBase.insertBill(bill);

        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(bill.getFrom());
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) - Double.valueOf(bill.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }
        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) - Double.valueOf(bill.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }


        dataBase.close();
        return ID;
    }

    public int addNewAsset(Context context,AssetsEntify asset){
        int ID = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        ID=dataBase.insertAssets(asset);


        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) + Double.valueOf(asset.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }
        dataBase.close();
        return ID;
    }

    public int addNewAim(Context context,AimEntify aim){
        int ID = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();





        dataBase.close();
        return ID;
    }

    public int updataBill(Context context,BillEntify bill,String moneyed){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        dataBase.updateBill(bill);


        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(bill.getFrom());
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) - Double.valueOf(bill.getMoney())
                    + Double.valueOf(moneyed);
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }
        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) - Double.valueOf(bill.getMoney())
                    + Double.valueOf(moneyed);
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }



        dataBase.close();
        return 1;
    }

    public int updataAsset(Context context,AssetsEntify asset , String moneyed){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        dataBase.updateAssets(asset);

        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) + Double.valueOf(asset.getMoney())
                    - Double.valueOf(moneyed);
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }


        dataBase.close();
        return 1;
    }


    public int deleteBill(Context context,BillEntify bill){
        int num = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        dataBase.delBill(bill.getID());

        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(bill.getFrom());
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) + Double.valueOf(bill.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }
        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) + Double.valueOf(bill.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }



        dataBase.close();
        return num;
    }

    public int deleteAsset(Context context,AssetsEntify asset){
        int num = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        dataBase.delAssets(asset.getID());

        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            Asset.setID(0);
            String money = "";
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String TYPES = cursor.getString(2);
                int type = Integer.valueOf(TYPES);
                money = cursor.getString(3);
                Asset.setType(type);
                Asset.setName(name);
                cursor.close();
            }
            double moneys = Double.valueOf(money) + Double.valueOf(asset.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }



        dataBase.close();
        return num;
    }


}
