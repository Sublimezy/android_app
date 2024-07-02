package com.gxuwz.zy.exam.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gxuwz.zy.exam.entity.dos.MyGradeBean;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 读取问题
     */
    public List<MyGradeBean> getAllQuestionByParams(MyGradeBean myGradeBean) {
        Cursor cursor = null;
        String selection = "";
        ArrayList<String> selectionArgsList = new ArrayList<>();

        try {
            mRDB = openReadLink();

            Long subjects = (long) myGradeBean.getSubject();

            if (subjects != null) {
                selection += "subject=? AND ";
                selectionArgsList.add(String.valueOf(myGradeBean.getSubject()));
            }
            if (myGradeBean.getModel() != null && !myGradeBean.getModel().isEmpty()) {
                selection += "model=? AND ";
                selectionArgsList.add(myGradeBean.getModel());
            }

// 去掉最后的 " AND "
            if (selection.endsWith(" AND ")) {
                selection = selection.substring(0, selection.length() - 5);
            }

// 转换成数组
            String[] selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);


            cursor = mRDB.query(false, TABLE_NAME, null, selection, selectionArgs,
                    null, null, null, null, null);


            List<MyGradeBean> result = new ArrayList<>();

            MyGradeBean que;


            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String examDate = cursor.getString(cursor.getColumnIndex("examDate"));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex("score"));
                @SuppressLint("Range") int totalQuestions = cursor.getInt(cursor.getColumnIndex("totalQuestions"));
                @SuppressLint("Range") int mistakesNum = cursor.getInt(cursor.getColumnIndex("mistakesNum"));
                @SuppressLint("Range") String mistakesQuestion = cursor.getString(cursor.getColumnIndex("mistakesQuestion"));
                @SuppressLint("Range") int trueNum = cursor.getInt(cursor.getColumnIndex("trueNum"));
                @SuppressLint("Range") int unAnsweredNum = cursor.getInt(cursor.getColumnIndex("unAnsweredNum"));
                @SuppressLint("Range") int allTime = cursor.getInt(cursor.getColumnIndex("allTime"));
                @SuppressLint("Range") int takeTime = cursor.getInt(cursor.getColumnIndex("takeTime"));
                @SuppressLint("Range") int subject = cursor.getInt(cursor.getColumnIndex("subject"));
                @SuppressLint("Range") String model = cursor.getString(cursor.getColumnIndex("model"));
                que = new MyGradeBean();
                que.setId((int) id);
                que.setExamDate(examDate);
                que.setScore(score);
                que.setTotalQuestions(totalQuestions);
                que.setMistakesQuestion(mistakesQuestion);
                que.setMistakesNum(mistakesNum);
                que.setTrueNum(trueNum);
                que.setUnAnsweredNum(unAnsweredNum);
                que.setAllTime(allTime);
                que.setTakeTime(takeTime);
                que.setSubject(subject);
                que.setModel(model);
                result.add(que);
            }

            cursor.close();
            mRDB.close();
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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
