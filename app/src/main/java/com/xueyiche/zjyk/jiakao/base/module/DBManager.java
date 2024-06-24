package com.xueyiche.zjyk.jiakao.base.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiond;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class
DBManager {

    private static final String ASSETS_NAME = "xueyichejk.db";
    private static final String DB_NAME = "xueyichejk.db";
    private static final int BUFFER_SIZE = 1024;
    private String DB_PATH;
    private Context mContext;

    public DBManager(Context context) {
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
     * 读取科目一问题
     *
     * @return
     */
    public List<Questiona> getAllQuestionA() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from question where course='1'", null);
        List<Questiona> result = new ArrayList<>();
        Questiona que;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex("question"));
            @SuppressLint("Range") String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
            @SuppressLint("Range") String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
            @SuppressLint("Range") String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
            @SuppressLint("Range") String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
            @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("image"));
            @SuppressLint("Range") String qid = cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
            @SuppressLint("Range") String explain = cursor.getString(cursor.getColumnIndex("explain"));
            @SuppressLint("Range") String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
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
    /**
     * 读取科目四问题
     *
     * @return
     */
    public List<Questiond> getAllQuestionD() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from question where course='4'", null);
        List<Questiond> result = new ArrayList<>();
        Questiond que;
        while (cursor.moveToNext()) {
           @SuppressLint("Range")  String question = cursor.getString(cursor.getColumnIndex("question"));
           @SuppressLint("Range")  String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
           @SuppressLint("Range")  String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
           @SuppressLint("Range")  String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
           @SuppressLint("Range")  String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
           @SuppressLint("Range")  String img = cursor.getString(cursor.getColumnIndex("image"));
           @SuppressLint("Range")  String qid = cursor.getString(cursor.getColumnIndex("number"));
           @SuppressLint("Range")  String video = cursor.getString(cursor.getColumnIndex("video"));
           @SuppressLint("Range")  String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
           @SuppressLint("Range")  String explain = cursor.getString(cursor.getColumnIndex("explain"));
           @SuppressLint("Range")  String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiond();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
            que.setQid(qid);
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
     * 考试的问题
     *
     * @return
     */
    public List<Questiona> findAllPractice(String kemu,String questiontype,String mun) {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from question where course="+kemu+" and type="+questiontype+
                " order by random() limit "+mun, null);
        Questiona que;
        while (cursor.moveToNext()) {
          @SuppressLint("Range")   String question = cursor.getString(cursor.getColumnIndex("question"));
          @SuppressLint("Range")   String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
          @SuppressLint("Range")   String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
          @SuppressLint("Range")   String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
          @SuppressLint("Range")   String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
          @SuppressLint("Range")   String qid = cursor.getString(cursor.getColumnIndex("number"));
          @SuppressLint("Range")   String img = cursor.getString(cursor.getColumnIndex("image"));
          @SuppressLint("Range")   String video = cursor.getString(cursor.getColumnIndex("video"));
          @SuppressLint("Range")   String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
          @SuppressLint("Range")   String explain = cursor.getString(cursor.getColumnIndex("explain"));
          @SuppressLint("Range")   String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setVideo(video);
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
     * 读取科目一的随即练习问题
     *
     * @return
     */
    public List<Questiona> getAllRandomQuestionA() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select *  from question where course=1 order by  random() limit 1400", null);
        List<Questiona> result = new ArrayList<>();
        Questiona que;
        while (cursor.moveToNext()) {

          @SuppressLint("Range")  String question = cursor.getString(cursor.getColumnIndex("question"));
          @SuppressLint("Range")  String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
          @SuppressLint("Range")  String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
          @SuppressLint("Range")  String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
          @SuppressLint("Range")  String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
          @SuppressLint("Range")  String img = cursor.getString(cursor.getColumnIndex("image"));
          @SuppressLint("Range")  String qid = cursor.getString(cursor.getColumnIndex("number"));
          @SuppressLint("Range")  String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
          @SuppressLint("Range")  String explain = cursor.getString(cursor.getColumnIndex("explain"));
          @SuppressLint("Range")  String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
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


    /**
     * 读取科目四的随即练习问题
     *
     * @return的
     */
    public List<Questiond> getAllRandomQuestionD() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select *  from question  where course=4 order by  random() limit 1400", null);
        List<Questiond> result = new ArrayList<>();
        Questiond que;
        while (cursor.moveToNext()) {
          @SuppressLint("Range")   String question = cursor.getString(cursor.getColumnIndex("question"));
          @SuppressLint("Range")   String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
          @SuppressLint("Range")   String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
          @SuppressLint("Range")   String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
          @SuppressLint("Range")   String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
          @SuppressLint("Range")   String img = cursor.getString(cursor.getColumnIndex("image"));
          @SuppressLint("Range")   String qid = cursor.getString(cursor.getColumnIndex("number"));
          @SuppressLint("Range")   String video = cursor.getString(cursor.getColumnIndex("video"));
          @SuppressLint("Range")   String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
          @SuppressLint("Range")   String explain = cursor.getString(cursor.getColumnIndex("explain"));
          @SuppressLint("Range")   String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiond();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
            que.setQid(qid);
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
     * 读取专项问题
     *
     * @return
     */
    public List<Questiona> getAllZhuangXiangQuestion(String kemu,String leixing) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("SELECT * FROM  question where course="+kemu+"" +
                " and question_type LIKE '%"+leixing+"%'", null);
        List<Questiona> result = new ArrayList<>();
        Questiona que;
        while (cursor.moveToNext()) {
           @SuppressLint("Range")  String question = cursor.getString(cursor.getColumnIndex("question"));
           @SuppressLint("Range")  String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
           @SuppressLint("Range")  String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
           @SuppressLint("Range")  String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
           @SuppressLint("Range")  String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
           @SuppressLint("Range")  String img = cursor.getString(cursor.getColumnIndex("image"));
           @SuppressLint("Range")  String qid = cursor.getString(cursor.getColumnIndex("number"));
           @SuppressLint("Range")  String video = cursor.getString(cursor.getColumnIndex("video"));
           @SuppressLint("Range")  String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
           @SuppressLint("Range")  String explain = cursor.getString(cursor.getColumnIndex("explain"));
           @SuppressLint("Range")  String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
            que.setQid(qid);
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
     * 读取专项问题数量
     *
     * @return
     */
    public String getZhuangXiangNumber(String kemu,String leixing) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("SELECT * FROM  question where course="+kemu+"" +
                " and question_type LIKE '%"+leixing+"%'", null);
        List<Questiona> result = new ArrayList<>();
        Questiona que;
        while (cursor.moveToNext()) {
          @SuppressLint("Range")   String question = cursor.getString(cursor.getColumnIndex("question"));
          @SuppressLint("Range")   String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
          @SuppressLint("Range")   String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
          @SuppressLint("Range")   String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
          @SuppressLint("Range")   String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
          @SuppressLint("Range")   String img = cursor.getString(cursor.getColumnIndex("image"));
          @SuppressLint("Range")   String qid = cursor.getString(cursor.getColumnIndex("number"));
          @SuppressLint("Range")   String video = cursor.getString(cursor.getColumnIndex("video"));
          @SuppressLint("Range")   String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
          @SuppressLint("Range")   String explain = cursor.getString(cursor.getColumnIndex("explain"));
          @SuppressLint("Range")   String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setImg(img);
            que.setQid(qid);
            que.setVideo(video);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result.size()+"";
    }

    /**
     * 答题赢现金抽取题目
     *
     * @return
     */
    public List<Questiona> answerWithCashQuestion() {
        List<Questiona> result = new ArrayList<Questiona>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from question where course=1 order by random() limit 60", null);
        Cursor cursor1 = db.rawQuery("select * from question where course=4 order by random() limit 40", null);
        Questiona que;
        while (cursor.moveToNext()) {
          @SuppressLint("Range")   String question = cursor.getString(cursor.getColumnIndex("question"));
          @SuppressLint("Range")   String answer_1 = cursor.getString(cursor.getColumnIndex("answer1"));
          @SuppressLint("Range")   String answer_2 = cursor.getString(cursor.getColumnIndex("answer2"));
          @SuppressLint("Range")   String answer_3 = cursor.getString(cursor.getColumnIndex("answer3"));
          @SuppressLint("Range")   String answer_4 = cursor.getString(cursor.getColumnIndex("answer4"));
          @SuppressLint("Range")   String qid = cursor.getString(cursor.getColumnIndex("number"));
          @SuppressLint("Range")   String img = cursor.getString(cursor.getColumnIndex("image"));
          @SuppressLint("Range")   String video = cursor.getString(cursor.getColumnIndex("video"));
          @SuppressLint("Range")   String true_answer = cursor.getString(cursor.getColumnIndex("true_answer"));
          @SuppressLint("Range")   String explain = cursor.getString(cursor.getColumnIndex("explain"));
          @SuppressLint("Range")   String question_type = cursor.getString(cursor.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setVideo(video);
            que.setImg(img);
            que.setTrue_answer(true_answer);
            que.setExplain(explain);
            que.setQuestion_type(question_type);
            result.add(que);
        }
        while (cursor1.moveToNext()) {
           @SuppressLint("Range")  String question = cursor1.getString(cursor1.getColumnIndex("question"));
           @SuppressLint("Range")  String answer_1 = cursor1.getString(cursor1.getColumnIndex("answer1"));
           @SuppressLint("Range")  String answer_2 = cursor1.getString(cursor1.getColumnIndex("answer2"));
           @SuppressLint("Range")  String answer_3 = cursor1.getString(cursor1.getColumnIndex("answer3"));
           @SuppressLint("Range")  String answer_4 = cursor1.getString(cursor1.getColumnIndex("answer4"));
           @SuppressLint("Range")  String qid = cursor1.getString(cursor1.getColumnIndex("number"));
           @SuppressLint("Range")  String img = cursor1.getString(cursor1.getColumnIndex("image"));
           @SuppressLint("Range")  String video = cursor1.getString(cursor1.getColumnIndex("video"));
           @SuppressLint("Range")  String true_answer = cursor1.getString(cursor1.getColumnIndex("true_answer"));
           @SuppressLint("Range")  String explain = cursor1.getString(cursor1.getColumnIndex("explain"));
           @SuppressLint("Range")  String question_type = cursor1.getString(cursor1.getColumnIndex("type"));
            que = new Questiona();
            que.setQuestion(question);
            que.setAnswer_1(answer_1);
            que.setAnswer_2(answer_2);
            que.setAnswer_3(answer_3);
            que.setAnswer_4(answer_4);
            que.setQid(qid);
            que.setVideo(video);
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
}
