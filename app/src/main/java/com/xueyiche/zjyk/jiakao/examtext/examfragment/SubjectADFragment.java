package com.xueyiche.zjyk.jiakao.examtext.examfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.xueyiche.zjyk.jiakao.examtext.MoNiTestPage;
import com.xueyiche.zjyk.jiakao.examtext.kemuad.PracticeNormalActivity;
import com.xueyiche.zjyk.jiakao.examtext.kemuad.PracticeSpecialActivity;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;
import com.xueyiche.zjyk.jiakao.homepage.db.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;


//科目一的fragment
public class SubjectADFragment extends BaseFragment implements View.OnClickListener {
    private QuestionDBHelper mHelper;
    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid, tv_practice_test;
    private TextView mTV_four, mTV_three, mTV_two, mTV_five;
    private boolean cuotia;
    private boolean shoucanga;
    private boolean chengji;
    private ProgressBar progressBar;
    private TextView textProgress;
    private ProgressBar progressBar2;
    private TextView textProgress2;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mHelper = mHelper.getInstance(getContext());
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mHelper.close();
    }


    @Override
    protected View setInitView() {

        mHelper = new QuestionDBHelper(App.context);


        View view = View.inflate(App.context, R.layout.home_exam_subjecta, null);
        ll_test_practice = (LinearLayout) view.findViewById(R.id.ll_test_practice);
        //顺序练习
        mTV_shunxuid = (TextView) view.findViewById(R.id.tv_shunxuid);

        progressBar = view.findViewById(R.id.progress_par);
        textProgress = view.findViewById(R.id.text_progress);

        showLayoutDialog();

        // 启动后台任务
        new ImportDataTask().execute();

        //专项
        mTV_two = (TextView) view.findViewById(R.id.tv_two);
        //错题
        mTV_three = (TextView) view.findViewById(R.id.tv_three);
        //收藏
        mTV_four = (TextView) view.findViewById(R.id.tv_four);
        //我的成绩
        mTV_five = (TextView) view.findViewById(R.id.tv_five);
        mTV_five.setOnClickListener(this);

        tv_practice_test = (TextView) view.findViewById(R.id.tv_practice_test);
        tv_practice_test.setOnClickListener(this);
        ll_test_practice.setOnClickListener(this);

        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);

//        dbManager.copyDBFile();
        String one_number = PrefUtils.getString(App.context, "one_number", "");
        QuestionBean questionBean = new QuestionBean(null, "1", "c1", null);
        if (!TextUtils.isEmpty(one_number)) {
            mTV_shunxuid.setText(one_number + "/" + mHelper.getAllQuestionByParams(questionBean).size());
        } else {
            mTV_shunxuid.setText(0 + "/" + mHelper.getAllQuestionByParams(questionBean).size());
        }

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
                startActivity(new Intent(App.context, PracticeSpecialActivity.class));
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
                startActivity(new Intent(App.context, PracticeNormalActivity.class));
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
        TextView dialogText = (TextView) dialogView.findViewById(R.id.dialog_text);
        progressBar2 = (ProgressBar) dialogView.findViewById(R.id.progress_par2);
        textProgress2 = (TextView) dialogView.findViewById(R.id.text_progress2);
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
            }
        });


    }

    private class ImportDataTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            // 假设这里是导入数据到数据库的操作
            int totalDataToImport = 100; // 假设要导入的数据总数

            progressBar.setMax(totalDataToImport);

            progressBar2.setMax(totalDataToImport);

            for (int i = 0; i <= totalDataToImport; i++) {
                // 模拟导入数据的过程
                // 这里可以调用你的数据库操作方法来插入数据
                // insertDataIntoDatabase();

                // 更新进度
                publishProgress(i, totalDataToImport);

                // 模拟延迟
                try {
                    Thread.sleep(50); // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = (int) (((float) values[0] / values[1]) * 100);
            progressBar.setProgress(progress);
            textProgress.setText("Progress: " + values[0] + " / " + values[1]);
            progressBar2.setProgress(progress);
            textProgress2.setText("Progress: " + values[0] + " / " + values[1]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
