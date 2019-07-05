package com.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.entify.AimEntify;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

public class MyDataBase {

    public static final String DATABASE_NAME = "Bookkeeping.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_ASSETS = "assets";
    public static final String TABLE_NAME_AIM = "aim";
    public static final String TABLE_NAME_BILL = "bill";

    public static final String SQL_CREATE_TABLE_ASSETS = "create table "
            + TABLE_NAME_ASSETS + "("
            +"_id INTEGER primary key autoincrement," + "_name TEXT,"
            +"_type INTEGER," + "_money TEXT" + ");";
    public static final String SQL_CREATE_TABLE_BILL = "create table "
            + TABLE_NAME_BILL+ "("
            +"_id INTEGER primary key autoincrement," + "_from INTEGER,"
            +"_money TEXT," + "_date TEXT," +  "_aim INTEGER" + ");";
    public static final String SQL_CREATE_TABLE_AIM= "create table "
            + TABLE_NAME_AIM + "("
            +"_id INTEGER primary key autoincrement," + "_type INTEGER,"
            +"_money TEXT," + "_date TEXT" + ");";

    private  String assetsString[] = {"_id","_name","_type","_money"};
    private  String billString[] = {"_id","_from","_money","_date","_aim"};
    private  String aimString[] = {"_id","_type","_money","_date"};

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase mSqliteDatabase = null;

    //TODO 打开数据库
    public void open(){
        dbHelper = new DatabaseHelper(context);
        mSqliteDatabase = dbHelper.getWritableDatabase();
    }

    //TODO 关闭数据库
    public void close(){
        dbHelper.close();
    }

    //TODO 获取记录总数
    public int getCout(String tabName,String type){
        int c = 0;
        String sql = "";

        sql = "select" + type + "from" + tabName +";";
        Cursor c1 = mSqliteDatabase.rawQuery(sql,null);
        if(c1.moveToFirst()){
            c += 1;
            while (c1.moveToNext()){
                c += 1;
            }
        }

        return c;
    }

    //TODO 获取assets表的所有记录,以id降序排序
    public Cursor getAssets_desc(){
        String sql = "select * from assets order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取assets表的所有记录，以升序排序
    public Cursor getAssets__asc(){
        String sql = "select * from assets order by _id asc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取assets中某id项
    public Cursor getAssets(int id){
        String sql = "select * from assets where _id = " + id +";";
        Cursor cursor = mSqliteDatabase.rawQuery(sql,null);
        return cursor;
    }

    //TODO 获取bill表的所有记录，以id降序排序
    public Cursor getBill_desc(){
        String sql = "select * from bill order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取bill表中某时间段记录
    public Cursor getAssets(String time1,String time2){
        String sql = "select * from bill where _date >= " + time1 + " and _date <= " + time2 + " order by _date asc";
        Cursor cursor = mSqliteDatabase.rawQuery(sql,null);
        return cursor;
    }

    //TODO 获取bill表中某时间段的某aim记录
    public Cursor getAssets(String time1,String time2,int aim){
        String sql = "select * from bill where _date >= " + time1 + " and _date <= " + time2 + " and _aim = " + aim +" order by _date asc";
        Cursor cursor = mSqliteDatabase.rawQuery(sql,null);
        return cursor;
    }

    //TODO 获取bill表的所有记录，以id升序排序
    public Cursor getBill_asc(){
        String sql = "select * from bill order by _id asc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取bill表某项的所有记录，以id降序排序
    public Cursor getBill_desc(String member){
        String sql = "select * from bill where " + member + " order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取aim表中所有记录,以id降序排序
    public Cursor getAim_desc(){
        String sql = "select * from aim order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取aim表中某项的所有记录，以id降序排序
    public Cursor getAim_desc(String member){
        String sql = "select * from aim where " + member + " order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 向表assets中插入数据
    public int insertAssets(AssetsEntify assets){
        int id = -1;
        ContentValues values = new ContentValues();
        values.put(assetsString[1],assets.getName());
        values.put(assetsString[2],assets.getType());
        values.put(assetsString[3],assets.getMoney());
        if(mSqliteDatabase.insert(TABLE_NAME_ASSETS,null,values) != -1){
            Cursor cursor = getAssets_desc();
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
                return id;
            }
        }
        return id;
    }

    //TODO 向表assets中插入含id数据
    public int insertAssets_init(AssetsEntify assets){
        int id = -1;
        ContentValues values = new ContentValues();
        values.put(assetsString[0],assets.getID());
        values.put(assetsString[1],assets.getName());
        values.put(assetsString[2],assets.getType());
        values.put(assetsString[3],assets.getMoney());
        if(mSqliteDatabase.insert(TABLE_NAME_ASSETS,null,values) != -1){
            Cursor cursor = getAssets_desc();
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
                return id;
            }
        }
        return id;
    }

    //TODO 向表bill中插入数据
    public int insertBill(BillEntify bill){
        int id = -1;
        ContentValues values = new ContentValues();
//        values.put(billString[0],bill.getID());
        values.put(billString[1],bill.getFrom());
        values.put(billString[2],bill.getMoney());
        values.put(billString[3],bill.getDateString());
        values.put(billString[4],bill.getAim());
        if(mSqliteDatabase.insert(TABLE_NAME_BILL,null,values) != -1){
            Cursor cursor = getBill_desc();
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
                return id;
            }
        }
        return id;
    }

    //TODO 向表aim中插入数据
    public int insertAim(AimEntify aim){
        int id = -1;
        ContentValues values = new ContentValues();
//        values.put(aimString[0],aim.getID());
        values.put(aimString[1],aim.getType());
        values.put(aimString[2],aim.getMoney());
        values.put(aimString[3],aim.getDateString());
        if(mSqliteDatabase.insert(TABLE_NAME_AIM,null,values) != -1){
            Cursor cursor = getAim_desc();
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
                return id;
            }
        }
        return id;
    }

    //TODO 删除assets表中某id
    public int delAssets(int id){
        int sub = 0;
        String selection = "_id = '" + id +"'";
        sub = mSqliteDatabase.delete(TABLE_NAME_ASSETS,selection,null);
        return sub;
    }

    //TODO 删除bill表中某id
    public int delBill(int id){
        int sub = 0;
        String selection = "_id = '" + id +"'";
        sub = mSqliteDatabase.delete(TABLE_NAME_BILL,selection,null);
        return sub;
    }

    //TODO 删除aim表中某id
    public int delAim(int id){
        int sub = 0;
        String selection = "_id = '" + id +"'";
        sub = mSqliteDatabase.delete(TABLE_NAME_AIM,selection,null);
        return sub;
    }

    //TODO 更改assets表中某项
    public int updateAssets(AssetsEntify assetsEntify){
        int sub = 0;
        String selection = "_id = '" + assetsEntify.getID() + "'";
        ContentValues values = new ContentValues();
        values.put(assetsString[0],assetsEntify.getID());
        values.put(assetsString[1],assetsEntify.getName());
        values.put(assetsString[2],assetsEntify.getType());
        values.put(assetsString[3],assetsEntify.getMoney());
        sub = mSqliteDatabase.update(TABLE_NAME_ASSETS,values,selection,null);
        return sub;
    }

    //TODO 更改bill表中某项
    public int updateBill(BillEntify billEntify){
        int sub = 0;
        String selection = "_id = '" + billEntify.getID() + "'";
        ContentValues values = new ContentValues();
        values.put(billString[0],billEntify.getID());
        values.put(billString[1],billEntify.getFrom());
        values.put(billString[2],billEntify.getMoney());
        values.put(billString[3],billEntify.getDateString());
        values.put(billString[4],billEntify.getAim());
        sub = mSqliteDatabase.update(TABLE_NAME_BILL,values,selection,null);
        return sub;
    }

    //TODO 更改aim表中某项
    public int updateAim(AimEntify aimEntify){
        int sub = 0;
        String selection = "_id = '" + aimEntify.getID() + "'";
        ContentValues values = new ContentValues();
        values.put(aimString[0],aimEntify.getID());
        values.put(aimString[1],aimEntify.getType());
        values.put(aimString[2],aimEntify.getMoney());
        values.put(aimString[3],aimEntify.getDateString());
        sub = mSqliteDatabase.update(TABLE_NAME_BILL,values,selection,null);
        return sub;
    }


    public MyDataBase(Context context){
        this.context = context;
    }

   public static class DatabaseHelper extends SQLiteOpenHelper{

       public DatabaseHelper(Context context){
           super(context,DATABASE_NAME,null,DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db){
           //创建表
           db.execSQL(SQL_CREATE_TABLE_ASSETS);
           db.execSQL(SQL_CREATE_TABLE_BILL);
           db.execSQL(SQL_CREATE_TABLE_AIM);
       }

       @Override
       public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
       }
   }
}
