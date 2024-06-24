package com.xueyiche.zjyk.jiakao.homepage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyResultOpenHelper extends SQLiteOpenHelper{

    public MyResultOpenHelper(Context context) {
        super(context, "wodechengji.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table wodechengji (_id integer primary key autoincrement, shijian " +
                "varchar(20), fenshu varchar(30),jishi varchar(30),kemu varchar(30))");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
