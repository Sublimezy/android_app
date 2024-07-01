package com.xueyiche.zjyk.jiakao.exam.fragment.subject.subject_ad;


import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_ALL_QUESTION;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_MISTAKES_QUESTION;
import static com.xueyiche.zjyk.jiakao.constants.Constant.TAKE_START_TIME;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.COLLECTION;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.MISTAKES;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.PRACTICE;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.SEQUENCE;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.SPECIALS;

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
import android.widget.Toast;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;
import com.xueyiche.zjyk.jiakao.exam.activity.question.PracticeNormalActivity;
import com.xueyiche.zjyk.jiakao.exam.activity.special.SpecialQuestionActivity;
import com.xueyiche.zjyk.jiakao.exam.database.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.exam.entity.dos.QuestionBean;
import com.xueyiche.zjyk.jiakao.exam.entity.dto.ReqQuestionBean;
import com.xueyiche.zjyk.jiakao.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.jiakao.myhttp.RequestCallBack;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//科目一的fragment
public class SubjectADFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private QuestionDBHelper mHelper;
    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid, tv_practice_test;
    private TextView mTV_four, mTV_three, mTV_two, mTV_five;
    private Integer questionBeanListSize;

    private ProgressBar progressBar;
    private TextView textProgress;
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
    String model = null;

    Integer pageNum = 1;
    Integer pageSize = 1000;
    Bundle args;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onStart() {
        super.onStart();

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


        //导入数据库进度条
        progressBar = view.findViewById(R.id.progress_par);
        textProgress = view.findViewById(R.id.text_progress);

        args = getArguments();

        if (args != null) {
            model = args.getString("model");
        }


        mHelper = QuestionDBHelper.getInstance(App.context);

        subject = (long) PrefUtils.getInt(getContext(), "subject", 1);

        subjectArgs(null, subject, model, null);

        mTV_five.setOnClickListener(this);
        tv_practice_test.setOnClickListener(this);
        ll_test_practice.setOnClickListener(this);
        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);

        subjectGroup.setOnCheckedChangeListener(this);


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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.subject1:
                subjectArgs(null, 1L, model, null);
                PrefUtils.putInt(getContext(), "subject", Math.toIntExact(subject));
                break;
            case R.id.subject4:
                subjectArgs(null, 4L, model, null);
                PrefUtils.putInt(getContext(), "subject", Math.toIntExact(subject));
                break;
        }
    }


    void subjectArgs(Long id, Long subject, String model, Long questionType) {

        this.subject = subject;

        args.putLong("subject", subject);

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
/*        List<QuestionBean> allCuoTiA = mHelper.findAllCuoTiA();
        List<QuestionBean> allShouCangA = mHelper.findAllShouCangA();
        List<MyResultBean> chengjiList = dbResult.findAllResult();
        chengji = chengjiList.size() == 0;
        cuotia = allCuoTiA.size() == 0;
        shoucanga = allShouCangA.size() == 0;*/

        switch (v.getId()) {

  /*            //我的成绩
            case R.id.tv_five:
                if (chengji) {
                    showToastShort("暂无成绩！");
                } else {
                    Intent intent5 = new Intent(App.context, MyResult.class);
                    intent5.putExtra("myresult", "1");
                    startActivity(intent5);
                }
                break;*/
            //顺序练习
            case R.id.ll_test_practice:
                args.putString("page", SEQUENCE.name());
                readyGo(PracticeNormalActivity.class, args);
                break;
            //我的错题
            case R.id.tv_three:
                mistakes = PrefUtils.getStrListValue(getContext(), "mistakes");
                if (mistakes.size() == 0) {
                    showToastShort("当前没有错题呦 ！");
                } else {
                    args.putString("page", MISTAKES.name());
                    readyGo(PracticeNormalActivity.class, args);
                }
                break;
            //我的收藏
            case R.id.tv_four:
                collect = PrefUtils.getStrListValue(getContext(), "collect");
                if (collect.size() == 0) {
                    showToastShort("当前没有收藏呦 ！");
                } else {
                    args.putString("page", COLLECTION.name());
                    readyGo(PracticeNormalActivity.class, args);
                }
                break;
            //专项练习
            case R.id.tv_two:
                args.putString("page", SPECIALS.name());
                readyGo(SpecialQuestionActivity.class, args);
                break;
            //模拟练习
            case R.id.tv_practice_test:

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);

                collect = PrefUtils.getStrListValue(getContext(), "collect");

                PrefUtils.putString(getContext(), TAKE_START_TIME, formattedDate);
                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES_QUESTION, new ArrayList<>());
                PrefUtils.putStrListValue(getContext(), PRE_ALL_QUESTION, new ArrayList<>());
                args.putString("page", PRACTICE.name());
                readyGo(PracticeNormalActivity.class, args);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTV_shunxuid.setText(String.valueOf(questionBeanListSize));
    }


    private void showLayoutDialog() {
        //加载布局并初始化组件
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.daoru_jiakao, null);
        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        progressBar2 = dialogView.findViewById(R.id.progress_par2);
        textProgress2 = dialogView.findViewById(R.id.text_progress2);
        Button dialogBtnConfirm = dialogView.findViewById(R.id.dialog_btn_confirm);
        Button dialogBtnCancel = dialogView.findViewById(R.id.dialog_btn_cancel);
        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(getContext());
        layoutDialog.setTitle("我是标题");

        layoutDialog.setView(dialogView);
        final AlertDialog dialog = layoutDialog.show();
        //设置组件
        dialogText.setText("我是自定义layout的弹窗！！");

        dialogBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "我是自定义layout的弹窗！！", Toast.LENGTH_SHORT).show();
            }
        });

        dialogBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当点击取消按钮时，关闭对话框
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getContext(), "对话框已关闭！", Toast.LENGTH_SHORT).show();
                // 将整数转换为字符串
                mTV_shunxuid.setText(String.valueOf(questionBeanListSize));
            }
        });


    }


    private class ImportDataTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            Map<String, String> params = new HashMap<>();
            params.put("subject", String.valueOf(subject));
            params.put("model", model);
            params.put("pageNum", String.valueOf(pageNum));
            params.put("pageSize", String.valueOf(pageSize));


            MyHttpUtils.getHttpMessage(AppUrl.DRIVING_LIST, params, ReqQuestionBean.class, new RequestCallBack<ReqQuestionBean>() {


                @Override
                public void requestSuccess(ReqQuestionBean json) {

                    int totalDataToImport = (int) json.getTotal();

                    questionBeanList = json.getRows();

                    questionBeanListSize = questionBeanList.size();

                    if (questionBeanList != null) {

                        progressBar.setMax(totalDataToImport);

                        progressBar2.setMax(totalDataToImport);

                        for (int i = 1; i <= totalDataToImport; i++) {
                            QuestionBean questionBean = questionBeanList.get(i);

                            boolean insert = mHelper.insert(questionBean);
                            if (insert) {
                                // 更新进度
                                publishProgress(i, totalDataToImport);
                            }


                        }
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
            progressBar.setProgress(value0);
            textProgress.setText("导入进度: " + value0 + " / " + value1);
            progressBar2.setProgress(value0);
            textProgress2.setText("导入进度: " + value0 + " / " + value1);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
