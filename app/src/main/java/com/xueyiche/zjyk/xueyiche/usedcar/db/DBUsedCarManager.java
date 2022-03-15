package com.xueyiche.zjyk.xueyiche.usedcar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarShaiXuanBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2016/9/29.
 */
public class
DBUsedCarManager {

    private static final String ASSETS_NAME = "usedcarshaixuan.db";
    private static final String DB_NAME = "usedcarshaixuan.db";
    private static final int BUFFER_SIZE = 1024;
    private String DB_PATH;
    private Context mContext;

    public DBUsedCarManager(Context context) {
        this.mContext = context;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyDBFile() {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定姓名下筛选内容
     *
     * @return
     */
    public List<UsedCarShaiXuanBean> findTypeList(String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from usedcarshaixuan where type=?", new String[]{type});
        List<UsedCarShaiXuanBean> result = new ArrayList<>();
        UsedCarShaiXuanBean usedCarShaiXuanBean;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            usedCarShaiXuanBean = new UsedCarShaiXuanBean();
            usedCarShaiXuanBean.setName(name);
            usedCarShaiXuanBean.setState(state);
            result.add(usedCarShaiXuanBean);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 更改选中状态
     *
     * @return
     */
    public void changeState(String name, String state) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        ContentValues values = new ContentValues();
        values.put("state", state);
        db.update("usedcarshaixuan", values, "name=?", new String[]{name});
        db.close();
    }
    /**
     * 更改范围
     *
     * @return
     */
    public void changeFanWei(String type,String name, String state) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        ContentValues values = new ContentValues();
        values.put("state", state);
        values.put("name", name);
        db.update("usedcarshaixuan", values, "type=?", new String[]{type});
        db.close();
    }
    /**
     * 清空所有选中状态
     *
     * @return
     */
    public void cleanAllState() {
        changeFanWei("价格","0","A");
        changeFanWei("车龄","0","A");
        changeFanWei("里程","0","A");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        ContentValues values1 = new ContentValues();
        values1.put("state", "0");
        db.update("usedcarshaixuan", values1, "state = ?", new String[]{"1"});
        db.close();
    }
}
