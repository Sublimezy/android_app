package com.gxuwz.zy.exam.fragment.subject.subject_ad;


import static com.gxuwz.zy.constants.Constant.PRE_ALL_QUESTION;
import static com.gxuwz.zy.constants.Constant.PRE_COLLECT;
import static com.gxuwz.zy.constants.Constant.PRE_MISTAKES;
import static com.gxuwz.zy.constants.Constant.PRE_MISTAKES_QUESTION;
import static com.gxuwz.zy.constants.Constant.PRE_MODEL;
import static com.gxuwz.zy.constants.Constant.PRE_PAGE;
import static com.gxuwz.zy.constants.Constant.PRE_SUBJECT;
import static com.gxuwz.zy.constants.Constant.TAKE_START_TIME;
import static com.gxuwz.zy.exam.entity.enums.PageEnum.COLLECTION;
import static com.gxuwz.zy.exam.entity.enums.PageEnum.MISTAKES;
import static com.gxuwz.zy.exam.entity.enums.PageEnum.PRACTICE;
import static com.gxuwz.zy.exam.entity.enums.PageEnum.SEQUENCE;
import static com.gxuwz.zy.exam.entity.enums.PageEnum.SPECIALS;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseFragment;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.exam.activity.mygradle.MyGradleActivity;
import com.gxuwz.zy.exam.activity.question.PracticeNormalActivity;
import com.gxuwz.zy.exam.activity.special.SpecialQuestionActivity;
import com.gxuwz.zy.exam.database.MyGradeDBHelper;
import com.gxuwz.zy.exam.database.QuestionDBHelper;
import com.gxuwz.zy.exam.entity.dos.MyGradeBean;
import com.gxuwz.zy.exam.entity.dos.QuestionBean;
import com.gxuwz.zy.exam.entity.dto.ReqQuestionBean;
import com.gxuwz.zy.myhttp.MyHttpUtils;
import com.gxuwz.zy.myhttp.RequestCallBack;
import com.gxuwz.zy.utils.PrefUtils;
import com.luck.picture.lib.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//科目一的fragment
public class SubjectADFragment extends BaseFragment implements View.OnClickListener {
    private QuestionDBHelper mHelper;
    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid, tv_practice_test;
    private TextView mTV_four, mTV_three, mTV_two, mTV_five;
    private Integer questionBeanListSize;
    private Button dialogBtnCancel;
    private Button genxing;
    private RadioGroup subjectGroup;
    private RadioButton subject1;
    private RadioButton subject2;
    private ProgressBar progressBar2;
    private TextView textProgress2;
    private QuestionBean queryQuestionParams;
    private List<QuestionBean> questionBeanList;

    private List<String> mistakes = new ArrayList<>();

    private List<String> collect = new ArrayList<>();

    Long subject = null;


    Integer pageNum = 1;
    Integer pageSize = 10000;
    Bundle args;

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected View setInitView() {


        View view = View.inflate(App.context, R.layout.home_exam_subjecta, null);

        //顺序练习
        ll_test_practice = view.findViewById(R.id.ll_test_practice);
        //顺序联系的题目数量
        mTV_shunxuid = view.findViewById(R.id.tv_shunxuid);
        //专项
        mTV_two = view.findViewById(R.id.tv_two);
        //错题
        mTV_three = view.findViewById(R.id.tv_three);
        //收藏
        mTV_four = view.findViewById(R.id.tv_four);
        //我的成绩
        mTV_five = view.findViewById(R.id.tv_five);
        //模拟考试
        tv_practice_test = view.findViewById(R.id.tv_practice_test);

        subjectGroup = view.findViewById(R.id.subject_group);

        subject1 = view.findViewById(R.id.subject1);

        subject2 = view.findViewById(R.id.subject4);

        genxing = view.findViewById(R.id.genxing);

        args = new Bundle();

        String model = PrefUtils.getString(App.context, PRE_MODEL, "c1");


        mHelper = QuestionDBHelper.getInstance(App.context);

        subject = (long) PrefUtils.getInt(getContext(), PRE_SUBJECT, 1);

        subjectArgs(null, subject, model, null);

        mTV_five.setOnClickListener(this);
        tv_practice_test.setOnClickListener(this);
        ll_test_practice.setOnClickListener(this);
        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);
        genxing.setOnClickListener(this);
        subject1.setOnClickListener(this);
        subject2.setOnClickListener(this);

        if (subject == 1L) {
            subject1.setChecked(true);
        } else if (subject == 4L) {
            subject2.setChecked(true);
        }

        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "1111";
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    void subjectArgs(Long id, Long subject, String model, Long questionType) {

        this.subject = subject;

        args.putLong(PRE_SUBJECT, subject);

        ToastUtils.showToast(getContext(), "已切换为" + (subject == 1 ? "科目一" : "科目四") + "驾照类型" + model);

        queryQuestionParams = new QuestionBean(id, subject, model, questionType);

        questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);

        questionBeanListSize = questionBeanList.size();

        if (questionBeanListSize == 0) {
            showLayoutDialog();
            // 启动后台任务 查询后台数据库，导入到本地
            new ImportDataTask().execute();
        }

        mTV_shunxuid.setText(String.valueOf(questionBeanListSize));

    }


    @Override
    public void onClick(View v) {
        String model = PrefUtils.getString(App.context, PRE_MODEL, "c1");

        args.putString(PRE_MODEL, model);

        switch (v.getId()) {

            //顺序练习
            case R.id.ll_test_practice:
                queryQuestionParams = new QuestionBean(null, subject, model, null);
                questionBeanListSize = mHelper.getAllQuestionByParams(queryQuestionParams).size();
                if (questionBeanListSize == 0) {
                    subjectArgs(null, subject, model, null);
                } else {
                    args.putString(PRE_PAGE, SEQUENCE.name());
                    readyGo(PracticeNormalActivity.class, args);
                }

                break;
            //我的错题
            case R.id.tv_three:
                mistakes = PrefUtils.getStrListValue(getContext(), PRE_MISTAKES);
                if (mistakes.size() == 0) {
                    showToastShort("当前没有错题呦 ！");
                } else {
                    args.putString(PRE_PAGE, MISTAKES.name());
                    readyGo(PracticeNormalActivity.class, args);
                }
                break;
            //我的收藏
            case R.id.tv_four:
                collect = PrefUtils.getStrListValue(getContext(), PRE_COLLECT);
                if (collect.size() == 0) {
                    showToastShort("当前没有收藏呦 ！");
                } else {
                    args.putString(PRE_PAGE, COLLECTION.name());
                    readyGo(PracticeNormalActivity.class, args);
                }
                break;
            //专项练习
            case R.id.tv_two:
                args.putString(PRE_PAGE, SPECIALS.name());
                readyGo(SpecialQuestionActivity.class, args);
                break;
            //模拟练习
            case R.id.tv_practice_test:

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);

                collect = PrefUtils.getStrListValue(getContext(), PRE_COLLECT);

                PrefUtils.putString(getContext(), TAKE_START_TIME, formattedDate);
                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES_QUESTION, new ArrayList<>());
                PrefUtils.putStrListValue(getContext(), PRE_ALL_QUESTION, new ArrayList<>());
                args.putString(PRE_PAGE, PRACTICE.name());
                readyGo(PracticeNormalActivity.class, args);
                break;

            //我的成绩
            case R.id.tv_five:

                MyGradeDBHelper myGradeDBHelper = MyGradeDBHelper.getInstance(App.context);
                MyGradeBean myGradeBean = new MyGradeBean(Math.toIntExact(subject), model);
                List<MyGradeBean> myGradeBeanList = myGradeDBHelper.getAllQuestionByParams(myGradeBean);
                if (myGradeBeanList.size() == 0) {
                    showToastShort("暂无成绩！");
                } else {

                    readyGo(MyGradleActivity.class, args);
                }
                break;

            //更新题库
            case R.id.genxing:

                queryQuestionParams = new QuestionBean(null, subject, model, null);
                showToastShort("正在执行清除操作！请等待");
                for (int i = 0; i < questionBeanList.size(); i++) {
                    mHelper.deleteAll(queryQuestionParams);
                }

                PrefUtils.putStrListValue(getContext(), PRE_COLLECT, new ArrayList<>());

                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES, new ArrayList<>());

                int size = mHelper.getAllQuestionByParams(queryQuestionParams).size();
                if (size == 0) {
                    showLayoutDialog();
                    // 启动后台任务 查询后台数据库，导入到本地
                    new ImportDataTask().execute();
                }
                break;
            //科目一
            case R.id.subject1:
                subjectArgs(null, 1L, model, null);
                PrefUtils.putInt(getContext(), PRE_SUBJECT, Math.toIntExact(subject));
                break;
            //科目四
            case R.id.subject4:
                subjectArgs(null, 4L, model, null);
                PrefUtils.putInt(getContext(), PRE_SUBJECT, Math.toIntExact(subject));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTV_shunxuid.setText(String.valueOf(questionBeanListSize));


    }


    private void showLayoutDialog() {
        String model = PrefUtils.getString(App.context, PRE_MODEL, "c1");
        //加载布局并初始化组件
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.daoru_jiakao, null);
        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        progressBar2 = dialogView.findViewById(R.id.progress_par2);
        textProgress2 = dialogView.findViewById(R.id.text_progress2);
        dialogBtnCancel = dialogView.findViewById(R.id.dialog_btn_cancel);
        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(getContext());

        layoutDialog.setTitle("导入题目");

        layoutDialog.setView(dialogView);
        final AlertDialog dialog = layoutDialog.show();

        dialog.setCancelable(false);

        dialog.setCanceledOnTouchOutside(false);

        //设置组件
        dialogText.setText("正在导入科目" + subject + "的题目！" + "驾照类型" + model);


        dialogBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionBeanListSize == 0) {
                    ToastUtils.showToast(getContext(), "请等待导入！");
                } else {
                    // 当点击取消按钮时，关闭对话框
                    dialog.dismiss();
                }

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 将整数转换为字符串
                mTV_shunxuid.setText(String.valueOf(questionBeanListSize));
            }
        });


    }


    private class ImportDataTask extends AsyncTask<Void, Integer, Void> {

        String model = PrefUtils.getString(App.context, PRE_MODEL, "c1");

        @Override
        protected Void doInBackground(Void... voids) {


            Map<String, String> params = new HashMap<>();
            params.put(PRE_SUBJECT, String.valueOf(subject));
            params.put(PRE_MODEL, model);
            params.put("pageNum", String.valueOf(pageNum));
            params.put("pageSize", String.valueOf(pageSize));


            MyHttpUtils.getHttpMessage(AppUrl.DRIVING_LIST, params, ReqQuestionBean.class, new RequestCallBack<ReqQuestionBean>() {


                @Override
                public void requestSuccess(ReqQuestionBean json) {

                    int totalDataToImport = (int) json.getTotal();

                    questionBeanList = json.getRows();

                    questionBeanListSize = questionBeanList.size();

                    if (questionBeanList != null) {

                        progressBar2.setMax(totalDataToImport);

                        for (int i = 1; i <= totalDataToImport; i++) {
                            QuestionBean questionBean = questionBeanList.get(i);

                            boolean insert = mHelper.insert(questionBean);
                            if (insert) {
                                // 更新进度
                                publishProgress(i, totalDataToImport);
                            }


                        }
                        ToastUtils.showToast(getContext(), "成功导入科目" + subject + "的题目！" + "驾照类型" + model);


                    }


                }


                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer value0 = values[0] + 1;
            Integer value1 = values[1];
            progressBar2.setProgress(value0);
            textProgress2.setText("导入进度: " + value0 + " / " + value1);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
