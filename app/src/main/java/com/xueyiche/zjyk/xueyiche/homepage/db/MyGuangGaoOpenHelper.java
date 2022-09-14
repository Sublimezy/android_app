package com.xueyiche.zjyk.xueyiche.homepage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Owner on 2016/10/8.
 */
public class MyGuangGaoOpenHelper extends SQLiteOpenHelper {
    public MyGuangGaoOpenHelper(Context context) {
        super(context, "guanggao.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("create table guanggao (_id integer primary key autoincrement," +
                    " guanggao_id varchar(50)" +
                    ",tupian varchar(1000)" +
                    ",start_date varchar(50)" +
                    ",end_date varchar(50)" +
                    ",start_time varchar(50)" +
                    ",end_time varchar(50)" +
                    ",image_url varchar(50)" +
                    ",tupian_url varchar(50)"+
                    ",area_id varchar(50))");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除原来的数据表
        db.execSQL("DROP TABLE IF EXISTS  guanggao");
        // 重新创建
        onCreate(db);
    }

    public static byte[] objectToBytes(Object obj) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = new ObjectOutputStream(out);
        sOut.writeObject(obj);
        sOut.flush();
        byte[] bytes = out.toByteArray();
        return bytes;
    }

    public static Object bytesToObject(byte[] bytes) throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }
}
