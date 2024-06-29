package com.xueyiche.zjyk.jiakao.exam.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;
import com.xueyiche.zjyk.jiakao.exam.MoNiTestPage;
import com.xueyiche.zjyk.jiakao.exam.dto.ReqQuestionBean;
import com.xueyiche.zjyk.jiakao.exam.kemuad.PracticeNormalActivity;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;
import com.xueyiche.zjyk.jiakao.homepage.db.QuestionDBHelper;

import com.xueyiche.zjyk.jiakao.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.jiakao.myhttp.RequestCallBack;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//科目一的fragment
public class SubjectADFragment extends BaseFragment implements View.OnClickListener{
    private QuestionDBHelper mHelper;
    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid, tv_practice_test;
    private TextView mTV_four, mTV_three, mTV_two, mTV_five;

    private ProgressBar progressBar;
    private TextView textProgress;
    private ProgressBar progressBar2;
    private TextView textProgress2;
    private QuestionBean queryQuestionParams;
    private List<QuestionBean> questionBeanList;
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
//        mHelper.close();
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected View setInitView() {

        args = getArguments();

        if (args != null) {
            subject = args.getLong("subject");
            model = args.getString("model");
        }

        mHelper = QuestionDBHelper.getInstance(App.context);

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

        //导入数据库进度条
        progressBar = view.findViewById(R.id.progress_par);
        textProgress = view.findViewById(R.id.text_progress);


        queryQuestionParams = new QuestionBean(null, subject, model, null);


        questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);

        if (questionBeanList.size() == 0) {
            showLayoutDialog();
            // 启动后台任务 查询后台数据库，导入到本地
            new ImportDataTask().execute();
        }


        mTV_five.setOnClickListener(this);
        tv_practice_test.setOnClickListener(this);
        ll_test_practice.setOnClickListener(this);
        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);


        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "1111";
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
            //专项练习
            case R.id.tv_two:
//                startActivity(new Intent(App.context, PracticeSpecialActivity.class));
                break;

/*            //我的错题
            case R.id.tv_three:
                if (cuotia) {
                    showToastShort("当前没有错题呦 ！");
                } else {
                    startActivity(new Intent(App.context, CuoTiA.class));
                }
                break;

            //我的收藏
            case R.id.tv_four:
                if (shoucanga) {
                    showToastShort("当前没有收藏呦 ！");
                } else {
                    startActivity(new Intent(App.context, CollectionA.class));
                }
                break;
            //我的成绩
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
                readyGo(PracticeNormalActivity.class, args);
                break;


            //模拟考试
            case R.id.tv_practice_test:
                Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                intent6.putExtra("moni_style", "1");
                startActivity(intent6);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String one_number = PrefUtils.getString(App.context, "one_number", "");
        if (!TextUtils.isEmpty(one_number)) {
//            mTV_shunxuid.setText(one_number + "/" + dbManager.getAllQuestionA().size());
        } else {
//            mTV_shunxuid.setText(0 + "/" + dbManager.getAllQuestionA().size());
        }

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
                mTV_shunxuid.setText(String.valueOf(questionBeanList.size()));
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
