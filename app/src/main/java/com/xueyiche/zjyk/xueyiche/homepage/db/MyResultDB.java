package com.xueyiche.zjyk.xueyiche.homepage.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xueyiche.zjyk.xueyiche.homepage.bean.MyResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2016/10/8.
 */
public class MyResultDB {

    private final MyResultOpenHelper helper;

    /**
     * 只有一个有参的构造方法,要求必须传入上下文
     *
     * @param context
     */
    public MyResultDB(Context context) {
        helper = new MyResultOpenHelper(context);

    }


    /**
     * 添加一个问题
     */
    public void add(String shijian, String fenshu, String jishi, String kemu) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into wodechengji (shijian,fenshu,jishi,kemu) values (?,?,?,?)",
                new Object[]{shijian, fenshu, jishi, kemu});
        db.close();//释放资源
    }

    /**
     * 获取成绩
     *
     * @return
     */
    public List<MyResultBean> findAllResult() {
        List<MyResultBean> result = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from wodechengji where kemu=1 order by shijian desc", null);
        MyResultBean que;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String shijian = cursor.getString(cursor.getColumnIndex("shijian"));
            @SuppressLint("Range") String fenshu = cursor.getString(cursor.getColumnIndex("fenshu"));
            @SuppressLint("Range") String jishi = cursor.getString(cursor.getColumnIndex("jishi"));
            que = new MyResultBean();
            que.setShijian(shijian);
            que.setFenshu(fenshu);
            que.setJishi(jishi);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }
    public List<MyResultBean> findAllResultD() {
        List<MyResultBean> result = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from wodechengji where kemu=4 order by shijian desc", null);
        MyResultBean que;
        while (cursor.moveToNext()) {
            String shijian = cursor.getString(cursor.getColumnIndex("shijian"));
            String fenshu = cursor.getString(cursor.getColumnIndex("fenshu"));
            String jishi = cursor.getString(cursor.getColumnIndex("jishi"));
            que = new MyResultBean();
            que.setShijian(shijian);
            que.setFenshu(fenshu);
            que.setJishi(jishi);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }



}
