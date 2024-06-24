package com.xueyiche.zjyk.jiakao.homepage.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;

import java.util.ArrayList;
import java.util.List;


public class KaoJiaZhaoDao {

    private final MyDataBaseOpenHelper helper;

    /**
     * 只有一个有参的构造方法,要求必须传入上下文
     *
     * @param context
     */
    public KaoJiaZhaoDao(Context context) {
        helper = new MyDataBaseOpenHelper(context);

    }


    /**
     * 添加一个问题
     */
    public void add(String qid, String question, String answer_1, String answer_2, String answer_3,
                    String answer_4, String true_answer, String explain, String question_type, String img, String video, String cuoti_shoucang) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into kaojiazhao (qid,question,answer_1,answer_2,answer_3,answer_4," +
                        "true_answer,explain,question_type,img,video,cuoti_shoucang) values (?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain, question_type, img, video, cuoti_shoucang});
        db.close();//释放资源
    }

    /**
     * 添加一个科目一做到第几题的记录
     */
    public void daTJLA(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into kaojiazhao (datijilua) values (?)",
                new Object[]{id});
        db.close();//释放资源
    }

    /**
     * 获取科目一做到第几题的记录
     *
     * @return
     */
    public String finddaTJLA() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select datijilua from kaojiazhao", null);
        String daTJL_id = "0";
        if (cursor.moveToLast()) {
            daTJL_id = cursor.getString(cursor.getColumnIndex("datijilua"));
        }
        return daTJL_id;
    }

    /**
     * 添加一个科目四做到第几题的记录
     */
    public void daTJLD(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into kaojiazhao (datijilud) values (?)",
                new Object[]{id});
        db.close();//释放资源
    }

    /**
     * 获取科目四做到第几题的记录
     *
     * @return
     */
    public String finddaTJLD() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select datijilud from kaojiazhao", null);
        String daTJL_id = "0";
        if (cursor.moveToLast()) {
            daTJL_id = cursor.getString(cursor.getColumnIndex("datijilud"));
        }
        return daTJL_id;
    }

    /**
     * 删除一个问题
     *
     * @param qid 题号
     */
    public void delete(String qid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from kaojiazhao where qid=?", new Object[]{qid});
        db.close();//释放资源
    }

    /**
     * 获取收藏A的问题
     *
     * @return
     */
    public List<Questiona> findAllShouCangA() {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from kaojiazhao where cuoti_shoucang='shoucanga'", null);
        Questiona que;
        while (cursor.moveToNext()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
            String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
            String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
            String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
            String qid = cursor.getString(cursor.getColumnIndex("qid"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
            String explain = cursor.getString(cursor.getColumnIndex("explain"));
            String question_type = cursor.getString(cursor.getColumnIndex("question_type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setImg(img);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 获取收藏D的问题
     *
     * @return
     */
    public List<Questiona> findAllShouCangD() {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from kaojiazhao where cuoti_shoucang='shoucangd'", null);
        Questiona que;
        while (cursor.moveToNext()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
            String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
            String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
            String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
            String qid = cursor.getString(cursor.getColumnIndex("qid"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
            String explain = cursor.getString(cursor.getColumnIndex("explain"));
            String video = cursor.getString(cursor.getColumnIndex("video"));
            String question_type = cursor.getString(cursor.getColumnIndex("question_type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setImg(img);
            que.setVideo(video);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 获取错题A的问题
     *
     * @return
     */
    public List<Questiona> findAllCuoTiA() {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from kaojiazhao where cuoti_shoucang='cuotia'", null);
        Questiona que;
        while (cursor.moveToNext()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
            String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
            String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
            String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
            String qid = cursor.getString(cursor.getColumnIndex("qid"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
            String explain = cursor.getString(cursor.getColumnIndex("explain"));
            String question_type = cursor.getString(cursor.getColumnIndex("question_type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setImg(img);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 获取错题D的问题
     *
     * @return
     */
    public List<Questiona> findAllCuoTiD() {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from kaojiazhao where cuoti_shoucang='cuotid'", null);
        Questiona que;
        while (cursor.moveToNext()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
            String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
            String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
            String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
            String qid = cursor.getString(cursor.getColumnIndex("qid"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String video = cursor.getString(cursor.getColumnIndex("video"));
            String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
            String explain = cursor.getString(cursor.getColumnIndex("explain"));
            String question_type = cursor.getString(cursor.getColumnIndex("question_type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setImg(img);
            que.setVideo(video);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean isInShouCangD(String qid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String cuoti_shoucang = "xyc";
        Cursor cursor = db.rawQuery("select * from kaojiazhao where qid=?", new String[]{qid});
        if (cursor.moveToNext()) {
            cuoti_shoucang = cursor.getString(cursor.getColumnIndex("cuoti_shoucang"));
        }
        if (cuoti_shoucang.equals("shoucangd")) {
            return true;
        }
            return false;

    }
    public boolean isInShouCangA(String qid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String cuoti_shoucang = "xyc";
        Cursor cursor = db.rawQuery("select * from kaojiazhao where qid=?", new String[]{qid});
        if (cursor.moveToNext()) {
            cuoti_shoucang = cursor.getString(cursor.getColumnIndex("cuoti_shoucang"));
        }
        if (cuoti_shoucang.equals("shoucanga")) {
            return true;
        }
        return false;

    }

}
