package com.xueyiche.zjyk.jiakao.homepage.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;


public class QuestionDBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "question.db";
    private static final String TABLE_NAME = "question_info";
    private static final int DB_VERSION = 1;
    private static QuestionDBHelper mHelper = null;
    private static SQLiteDatabase mRDB = null;
    private static SQLiteDatabase wRDB = null;


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


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "subject VARCHAR NOT NULL," +
                "model VARCHAR NOT NULL," +
                "questionType VARCHAR NOT NULL," +
                "item1 VARCHAR NOT NULL," +
                "item2 VARCHAR NOT NULL," +
                "item3 VARCHAR NOT NULL," +
                "item4 VARCHAR NOT NULL," +
                "answer INTEGER NOT NULL," +
                "question INTEGER NOT NULL," +
                "explains INTEGER NOT NULL," +
                "url INTEGER NOT NULL)";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 读取问题
     */
    public List<QuestionBean> getAllQuestionByParams(QuestionBean questionBean) {
        String selection = "id=? AND subject=? AND model=? AND questionType=?";
        String[] selectionArgs = {questionBean.getId(), questionBean.getSubject(), questionBean.getModel(), questionBean.getQuestionType()};

        Cursor cursor = mRDB.query(false, TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null, null);

        List<QuestionBean> result = new ArrayList<>();
        QuestionBean que;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex("question"));
            @SuppressLint("Range") String item1 = cursor.getString(cursor.getColumnIndex("item1"));
            @SuppressLint("Range") String item2 = cursor.getString(cursor.getColumnIndex("item2"));
            @SuppressLint("Range") String item3 = cursor.getString(cursor.getColumnIndex("item3"));
            @SuppressLint("Range") String item4 = cursor.getString(cursor.getColumnIndex("item4"));
            @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex("url"));
            @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex("answer"));
            @SuppressLint("Range") String explains = cursor.getString(cursor.getColumnIndex("explains"));
            que = new QuestionBean();
            que.setQuestion(question);
            que.setItem1(item1);
            que.setItem2(item2);
            que.setItem3(item3);
            que.setItem4(item4);
            que.setUrl(url);
            que.setAnswer(answer);
            que.setExplains(explains);
            result.add(que);
        }
        cursor.close();
        closeLink();
        return result;
    }


}
