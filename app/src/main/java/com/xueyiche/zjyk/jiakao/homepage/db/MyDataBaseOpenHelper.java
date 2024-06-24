package com.xueyiche.zjyk.jiakao.homepage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataBaseOpenHelper extends SQLiteOpenHelper{

    public MyDataBaseOpenHelper(Context context) {
        super(context, "xueyiche_kaojiazhao.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table kaojiazhao (_id integer primary key autoincrement, kemu varchar(20), qid varchar(30)" +
                ",question varchar(30),answer_1 varchar(30),answer_2 varchar(30),answer_3 varchar(30),answer_4 varchar(30)" +
                ",true_answer varchar(30),explain varchar(30),question_type varchar(30), img varchar(20),video varchar(20)," +
                " cuoti_shoucang varchar(20),datijilua varchar(20),datijilud varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
