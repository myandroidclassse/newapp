package com.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    //TODO 获取assets表的所有记录,以id降序排序
    public Cursor getAssets_desc(){
        String sql = "select * from assets order by _id desc;";
        Cursor c = mSqliteDatabase.rawQuery(sql,null);
        return c;
    }

    //TODO 获取assets表的所有记录，以升序排序
    public Cursor getAssets__asc(){
        Cursor c =null;

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
        Cursor c =null;
        return c;
    }

    //TODO 获取bill表某项的所有记录，以id升序排序
    public Cursor getBill_asc(String member){
        Cursor c =null;
        return c;
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
