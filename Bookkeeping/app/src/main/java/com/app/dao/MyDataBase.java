package com.app.dao;

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

    //TODO 获取bill表的所有记录，以id降序排序
    public Cursor getBill_desc(){
        Cursor c =null;
        return c;
    }

    //TODO 获取bill表的所有记录，以id升序排序
    public Cursor getBill_asc(){
        Cursor c =null;
        return c;
    }

    //TODO 获取bill表某项的所有记录，以id降序排序
    public Cursor getBill_desc(String member){
        String sql = "select * assets where " + member + " order by _id desc";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取aim表中所有记录,以id降序排序
    public Cursor getAim_desc(){

        Cursor c = null;
        return c;
    }

    //TODO 获取aim表中某项的所有记录，以id降序排序
    public Cursor getAim_desc(String member){

        Cursor c = null;
        return c;
    }

    //TODO 向表assets中插入数据
    public int insertAssets(AssetsEntify assets){
        int id = -1;
        return id;
    }

    //TODO 向表bill中插入数据
    public int insertBill(BillEntify bill){
        int id = -1;
        return id;
    }

    //TODO 向表aim中插入数据
    public int insertAim(AimEntify aim){
        int id = -1;
        return id;
    }

    //TODO 删除assets表中某id
    public int delAssets(int id){
        int sub = 0;
        return sub;
    }

    //TODO 删除bill表中某id
    public int delBill(int id){
        int sub = 0;
        return sub;
    }

    //TODO 删除aim表中某id
    public int delAim(int id){
        int sub = 0;
        return sub;
    }

    //TODO 更改assets表中某项
    public int updateAssets(AssetsEntify assetsEntify){
        int sub = 0;
        return sub;
    }

    //TODO 更改bill表中某项
    public int updateBill(BillEntify billEntify){
        int sub = 0;
        return sub;
    }

    //TODO 更改aim表中某项
    public int updateAim(AimEntify aimEntify){
        int sub = 0;
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
