package com.xueyiche.zjyk.jiakao.exam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.xueyiche.zjyk.jiakao.exam.entity.dos.MyGradeBean;

public class MyGradeDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_grade.db";
    private static final String TABLE_NAME = "my_grade_info";
    private static final int DB_VERSION = 1;
    private static MyGradeDBHelper mHelper = null;
    private static SQLiteDatabase mRDB = null;
    private static SQLiteDatabase wRDB = null;


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "examDate VARCHAR NOT NULL," +
                "score  INTEGER NOT NULL," +
                "totalQuestions INTEGER NOT NULL," +
                "mistakesNum INTEGER NOT NULL," +
                "trueNum INTEGER NOT NULL," +
                "unAnsweredNum INTEGER NOT NULL," +
                "mistakesQuestion VARCHAR NOT NULL," +
                "allTime INTEGER NOT NULL," +
                "takeTime INTEGER NOT NULL," +
                "subject INTEGER NOT NULL," +
                "model VARCHAR NOT NULL)";
        db.execSQL(sql);
    }

    public boolean insert(MyGradeBean myGradeBean) {
        try {
            wRDB = openWriteLink();
            ContentValues values = new ContentValues();
            values.put("examDate", myGradeBean.getExamDate());
            values.put("score", myGradeBean.getScore());
            values.put("takeTime", myGradeBean.getTakeTime());
            values.put("totalQuestions", myGradeBean.getTotalQuestions());
            values.put("mistakesNum", myGradeBean.getMistakesNum());
            values.put("trueNum", myGradeBean.getTrueNum());
            values.put("unAnsweredNum", myGradeBean.getUnAnsweredNum());
            values.put("mistakesQuestion", myGradeBean.getMistakesQuestion());
            values.put("allTime", myGradeBean.getAllTime());
            values.put("subject", myGradeBean.getSubject());
            values.put("model", myGradeBean.getModel());

            long result = wRDB.insert(TABLE_NAME, null, values);
            Log.e("DATABASE", String.valueOf(result));
            closeLink();
            return result > 0;
        } finally {
            closeLink();
        }
    }


    public MyGradeDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //利用单例模式获取数据库帮助器的唯一实例
    public static MyGradeDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new MyGradeDBHelper(context);
        }
        return mHelper;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }

        return mRDB;
    }


    //打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (wRDB == null || !wRDB.isOpen()) {
            wRDB = mHelper.getWritableDatabase();
        }
        return wRDB;
    }

    //关闭数据库连接
    public void closeLink() {
        if (wRDB != null && wRDB.isOpen()) {
            wRDB.close();
            wRDB = null;
        }
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
    }
}
