package com.xueyiche.zjyk.jiakao.exam.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.xueyiche.zjyk.jiakao.exam.entity.dos.QuestionBean;

import java.util.ArrayList;
import java.util.List;


public class QuestionDBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "question.db";
    private static final String TABLE_NAME = "question_info";
    private static final int DB_VERSION = 1;
    private static QuestionDBHelper mHelper = null;
    private static SQLiteDatabase mRDB = null;
    private static SQLiteDatabase wRDB = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "subject INTEGER NOT NULL," +
                "model VARCHAR NOT NULL," +
                "questionType INTEGER NOT NULL," +
                "item1 VARCHAR NOT NULL," +
                "item2 VARCHAR NOT NULL," +
                "item3 VARCHAR NOT NULL," +
                "item4 VARCHAR NOT NULL," +
                "answer VARCHAR NOT NULL," +
                "question VARCHAR NOT NULL," +
                "explains VARCHAR NOT NULL," +
                "url VARCHAR NOT NULL)";
        db.execSQL(sql);
    }

    public boolean insert(QuestionBean questionBean) {
        try {
            wRDB = openWriteLink();
            ContentValues values = new ContentValues();
            values.put("subject", questionBean.getSubject());
            values.put("model", questionBean.getModel());
            values.put("questionType", questionBean.getQuestionType());
            values.put("item1", questionBean.getItem1());
            values.put("item2", questionBean.getItem2());
            values.put("item3", questionBean.getItem3());
            values.put("item4", questionBean.getItem4());
            values.put("answer", questionBean.getAnswer());
            values.put("question", questionBean.getQuestion());
            values.put("explains", questionBean.getExplains());
            values.put("url", questionBean.getUrl());
//当values 为空时，nullColumnHack需指定一个字段


            long result = wRDB.insert(TABLE_NAME, null, values);
            Log.e("DATABASE", String.valueOf(result));
            closeLink();
            return result > 0;
        } finally {
            closeLink();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 读取问题
     */
    public List<QuestionBean> getAllQuestionByParams(QuestionBean questionBean) {
        Cursor cursor = null;
        String selection = "";
        ArrayList<String> selectionArgsList = new ArrayList<>();

        try {
            mRDB = openReadLink();


// 构建查询条件
            if (questionBean.getId() != null) {
                selection += "_id=? AND ";
                selectionArgsList.add(String.valueOf(questionBean.getId()));
            }
            if (questionBean.getSubject() != null) {
                selection += "subject=? AND ";
                selectionArgsList.add(String.valueOf(questionBean.getSubject()));
            }
            if (questionBean.getModel() != null && !questionBean.getModel().isEmpty()) {
                selection += "model=? AND ";
                selectionArgsList.add(questionBean.getModel());
            }
            if (questionBean.getQuestionType() != null) {
                selection += "questionType=? AND ";
                selectionArgsList.add(String.valueOf(questionBean.getQuestionType()));
            }

// 去掉最后的 " AND "
            if (selection.endsWith(" AND ")) {
                selection = selection.substring(0, selection.length() - 5);
            }

// 转换成数组
            String[] selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);


            cursor = mRDB.query(false, TABLE_NAME, null, selection, selectionArgs,
                    null, null, null, null, null);


            List<QuestionBean> result = new ArrayList<>();

            QuestionBean que;
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex("question"));
                @SuppressLint("Range") String item1 = cursor.getString(cursor.getColumnIndex("item1"));
                @SuppressLint("Range") String item2 = cursor.getString(cursor.getColumnIndex("item2"));
                @SuppressLint("Range") String item3 = cursor.getString(cursor.getColumnIndex("item3"));
                @SuppressLint("Range") String item4 = cursor.getString(cursor.getColumnIndex("item4"));
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex("url"));
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex("answer"));
                @SuppressLint("Range") String explains = cursor.getString(cursor.getColumnIndex("explains"));
                @SuppressLint("Range") long questionType = cursor.getLong(cursor.getColumnIndex("questionType"));
                que = new QuestionBean();
                que.setId(id);
                que.setQuestion(question);
                que.setItem1(item1);
                que.setItem2(item2);
                que.setItem3(item3);
                que.setItem4(item4);
                que.setUrl(url);
                que.setAnswer(answer);
                que.setExplains(explains);
                que.setQuestionType(questionType);
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

    public QuestionDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //利用单例模式获取数据库帮助器的唯一实例
    public static QuestionDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new QuestionDBHelper(context);
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
