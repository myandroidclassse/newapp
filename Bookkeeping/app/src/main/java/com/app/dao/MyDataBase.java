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

    public static final String SQL_CREATE_TABLE_ASSETS = "create table"
            + TABLE_NAME_ASSETS + "("
            +"_id INTEGER primary key autoincrement," + "_name TEXT,"
            +"__type ";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase mSqliteDatabase = null;



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

    //TODO 获取bill表from的所有记录，以id降序排序
    public Cursor getBill_desc(int from){
        Cursor c =null;
        return c;
    }

    //TODO 获取bill表from的所有记录，以id升序排序
    public Cursor getBill_asc(int from){
        Cursor c =null;
        return c;
    }
   public static class DatabaseHelper extends SQLiteOpenHelper{

       public DatabaseHelper(Context context){
           super(context,DATABASE_NAME,null,DATABASE_VERSION);
       }
   }
}
