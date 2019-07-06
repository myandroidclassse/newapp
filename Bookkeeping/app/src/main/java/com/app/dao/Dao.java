package com.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.app.entify.AimEntify;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

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
            for (int j = 0; j < num || num==0 ; j++){
                BillEntify bill = new BillEntify();
                String[] get = new String[10];
                for (int i = 0; i < 5; i++) {
                    get[i] = cursor.getString(i);
                }
                int ID = Integer.valueOf(get[0]);
                bill.setID(ID);
                int From = Integer.valueOf(get[1]);
                bill.setFrom(From);
                bill.setMoney(get[2]);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(get[3]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bill.setDate(date);
                bill.setDateString(get[3]);
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
            for (int j = 0; j < num || num == 0; j++){
                BillEntify bill = new BillEntify();
                String[] get = new String[10];
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
                bill.setDateString(get[3]);
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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String From = format.format(begin);
        String To = format.format(end);

        cursor = dataBase.getAssets(From,To,type);

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

    public double getMoneyFromDate(Context context,Date begin,Date end){
        double money = 0;
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String From = format.format(begin);
        String To = format.format(end);

        cursor = dataBase.getAssets(From,To);

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

    public double getMoneyFromDate(Context context,int type,String begin,String end){
        double money = 0;
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();

        cursor = dataBase.getAssets(begin,end,type);

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

    public double getMoneyFromDate(Context context,String begin,String end){
        double money = 0;
        Cursor cursor = null;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();

        cursor = dataBase.getAssets(begin,end);

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
        if(bill.getFrom()!=0)
        {
            desMoney(dataBase,bill.getFrom(),Double.valueOf(bill.getMoney()));
        }
        if(bill.getFrom()!=0)
        {
            desMoney(dataBase,0,Double.valueOf(bill.getMoney()));
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
            addMoney(dataBase,0,Double.valueOf(asset.getMoney()));
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

    public int updataBill(Context context,BillEntify bill,String moneyed,int fromold){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        int sub = dataBase.updateBill(bill);
        if(fromold!=0){
            //Log.w("updataBill:",Double.valueOf(bill.getMoney())+"");
            addMoney(dataBase,fromold,Double.valueOf(moneyed));
        }
        if(fromold!=0){
            addMoney(dataBase,0,Double.valueOf(moneyed));
        }
        if(bill.getFrom()!=0)
        {
            desMoney(dataBase,bill.getFrom(),Double.valueOf(bill.getMoney()));
        }
        if(bill.getFrom()!=0)
        {
            desMoney(dataBase,0,Double.valueOf(bill.getMoney()));
        }
        dataBase.close();
        return sub;
    }

    public int updataAsset(Context context,AssetsEntify asset , String moneyed){
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        int sub = dataBase.updateAssets(asset);

        {
            desMoney(dataBase,0,Double.valueOf(moneyed));
            addMoney(dataBase,0,Double.valueOf(asset.getMoney()));
        }


        dataBase.close();
        return sub;
    }

    public int deleteBill(Context context,BillEntify bill){
        int num = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        num = dataBase.delBill(bill.getID());
        if(bill.getFrom()!=0)
        {
            Cursor cursor = null;
            cursor = dataBase.getAssets(bill.getFrom());
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
        if(bill.getFrom()!=0)
        {
            Cursor cursor = null;
            AssetsEntify Asset = new AssetsEntify();
            cursor = dataBase.getAssets(0);
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
        num = dataBase.delAssets(asset.getID());

        {
            Cursor cursor = null;
            cursor = dataBase.getAssets(0);
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
            double moneys = Double.valueOf(money) - Double.valueOf(asset.getMoney());
            String newmoney = moneys + "";
            Asset.setMoney(newmoney);
            dataBase.updateAssets(Asset);
        }



        dataBase.close();
        return num;
    }

    public void Creat(Context context){
        MyDataBase dataBase = new MyDataBase(context);
        Cursor cursor = null;
        dataBase.open();
        cursor = dataBase.getAssets(0);
        if(cursor.getCount() == 0) {
            AssetsEntify asset = new AssetsEntify();
            asset.setMoney("0");
            asset.setID(0);
            asset.setName("all assets's sub");
            asset.setType(0);
            dataBase.insertAssets_init(asset);
        }
        dataBase.close();
    }

    public double monthPay(Context context){
        double money = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        ///"yyyy-MM-dd HH:mm:ss"
        if(month.length() == 1) {
            month = "0"+month;
        }
        String begin = year+"-"+month+"-"+"01 "+"00:00:01";
        String end = year+"-"+month+"-"+"30 "+"23:59:59";

        Cursor cursor = dataBase.getAssets(begin,end);

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
    public double monthPay(Context context,int year,int month){
        double money = 0;
        MyDataBase dataBase = new MyDataBase(context);
        dataBase.open();
        ///"yyyy-MM-dd HH:mm:ss"
        String monthString = month + "";
        if(month < 10){
            monthString = "0"+monthString;
        }
        String begin = year+"-"+monthString+"-"+"01 "+"00:00:01";
        String end = year+"-"+monthString+"-"+"30 "+"23:59:59";

        Cursor cursor = dataBase.getAssets(begin,end);

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

    public void addMoney(MyDataBase dataBase,int asset,double moneyed){

        Cursor cursor = null;
        cursor = dataBase.getAssets(asset);
        AssetsEntify Asset = new AssetsEntify();
        Asset.setID(asset);
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
        double moneys = Double.valueOf(money) + moneyed;
        String newmoney = moneys + "";
        if(newmoney.contains(".")&&newmoney.substring(newmoney.indexOf(".")).length()>3){
            newmoney = newmoney.substring(0,newmoney.indexOf(".")+3);
        }
        Asset.setMoney(newmoney);
        //Log.w("addMoney:",newmoney);
        dataBase.updateAssets(Asset);

    }
    public void desMoney(MyDataBase dataBase,int asset,double moneyed){

        Cursor cursor = null;
        cursor = dataBase.getAssets(asset);
        AssetsEntify Asset = new AssetsEntify();
        Asset.setID(asset);
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
        double moneys = Double.valueOf(money) - moneyed;
        String newmoney = moneys + "";
        if(newmoney.contains(".")&&newmoney.substring(newmoney.indexOf(".")).length()>3){
            newmoney = newmoney.substring(0,newmoney.indexOf(".")+3);
        }
        Asset.setMoney(newmoney);
        //Log .w("desMoney:",newmoney);
        dataBase.updateAssets(Asset);

    }

}
