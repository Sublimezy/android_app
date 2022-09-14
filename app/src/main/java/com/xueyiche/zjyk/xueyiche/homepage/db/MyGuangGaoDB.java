package com.xueyiche.zjyk.xueyiche.homepage.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoBean;

/**
 * Created by Owner on 2017/4/25.
 */
public class MyGuangGaoDB {
    private final MyGuangGaoOpenHelper helper;
    private SQLiteDatabase db;

    /**
     * 只有一个有参的构造方法,要求必须传入上下文
     *
     * @param context
     */
    public MyGuangGaoDB(Context context) {
        helper = new MyGuangGaoOpenHelper(context);
        db = helper.getWritableDatabase();
    }


    /**
     * 添加
     */
    public void add(String guanggao_id, String tupian, String start_date, String end_date, String start_time, String end_time, String image_url, String tupian_url,String area_id) {
        synchronized (helper) {
            // 看数据库是否关闭
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
            }
            // 开始事务
            db.beginTransaction();
            try {
                db.execSQL("insert into guanggao (guanggao_id,tupian,start_date,end_date,start_time,end_time,image_url,tupian_url,area_id) values (?,?,?,?,?,?,?,?,?)",
                        new Object[]{guanggao_id, tupian, start_date, end_date, start_time, end_time, image_url, tupian_url,area_id});
                db.setTransactionSuccessful(); // 设置事务成功完成
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 获取
     *
     * @return
     */
    public GuangGaoBean findGg(String date, String time,String area_id) {
        GuangGaoBean guangGaoBean = new GuangGaoBean();

        synchronized (helper) {
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
            }
            Cursor cursor = db.rawQuery("select * from guanggao where start_date <= ? and end_date > ?and start_time <= ? and end_time > ? and area_id = ?", new String[]{date, date, time, time,area_id});
            try {
                while (cursor.moveToNext()) {
                    String tupian = cursor.getString(cursor.getColumnIndex("tupian"));
                    String image_url = cursor.getString(cursor.getColumnIndex("image_url"));
                    String tupian_url = cursor.getString(cursor.getColumnIndex("tupian_url"));
                    guangGaoBean.setTupian(tupian);
                    guangGaoBean.setTupian_url(tupian_url);
                    guangGaoBean.setImage_url(image_url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
                db.close();
            }
        }

        return guangGaoBean;
    }

    /**
     * 根据url存入照片
     *
     * @return
     */
    public void addTuPian(String tupian_url, String tupian) {
        synchronized (helper) {
            // 看数据库是否关闭
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
            }
            // 开始事务
            db.beginTransaction();
            try {
                db.execSQL("update guanggao set tupian = ? where tupian_url = ?", new String[]{tupian, tupian_url});
                db.setTransactionSuccessful(); // 设置事务成功完成
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 清空
     *
     * @return
     */
    public void deleAll() {
        synchronized (helper) {
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
            }
            db.beginTransaction();
            try {
                db.execSQL("delete from guanggao");
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }
}
