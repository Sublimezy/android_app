package com.xueyiche.zjyk.xueyiche.homepage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Owner on 2016/10/8.
 */
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
