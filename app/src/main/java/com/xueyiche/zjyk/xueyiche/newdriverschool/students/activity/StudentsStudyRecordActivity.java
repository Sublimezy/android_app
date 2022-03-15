package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.adapter.StudyRecordListAdapter;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudyRecordBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 张磊 on 2021/1/18.                                       #
 */
//学习记录
public class StudentsStudyRecordActivity extends NewBaseActivity implements View.OnClickListener {
    private RefreshLayout refreshLayout;
    private TextView tvTitle;
    private LinearLayout llBack;
    private RecyclerView rvList;
    private StudyRecordListAdapter studyRecordListAdapter;

    @Override
    protected int initContentView() {
        return R.layout.students_common_list_activity;
    }

    @Override
    protected void initView() {
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        llBack = view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        rvList = view.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(StudentsStudyRecordActivity.this));
        studyRecordListAdapter = new StudyRecordListAdapter(R.layout.study_list_item_layout);
        rvList.setAdapter(studyRecordListAdapter);


    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1500);
            }
        });
        studyRecordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StudyRecordBean.ContentBean contentBean = (StudyRecordBean.ContentBean) adapter.getItem(position);
                String subject_name = contentBean.getSubject_name();
                if (!TextUtils.isEmpty(subject_name)) {
                    if ("科目一".equals(subject_name)||"科目四".equals(subject_name)) {
                        Toast.makeText(StudentsStudyRecordActivity.this, "无详情！", Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = new Intent(App.context,StudentsStudyContentActivity.class);
                        intent.putExtra("training_id",""+(contentBean.getTraining_id()));
                        startActivity(intent);
                    }
                }

            }
        });
    }

    private void getDataFromNet() {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.studyinfolist)
                .addParams("stu_user_id", PrefUtils.getParameter("user_id"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        Log.e("studyinfolist", "" + string);
                        if (!TextUtils.isEmpty(string)) {
                            StudyRecordBean studyRecordBean = JsonUtil.parseJsonToBean(string, StudyRecordBean.class);
                            if (studyRecordBean != null) {
                                int code = studyRecordBean.getCode();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == code) {
                                            List<StudyRecordBean.ContentBean> content = studyRecordBean.getContent();
                                            if (content!=null&&content.size()>0) {
                                                studyRecordListAdapter.setNewData(content);
                                            }else {
                                                studyRecordListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout,null));
                                            }
                                        }else {
                                            studyRecordListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout,null));

                                        }
                                    }
                                });
                            }
                        }
                        return string;
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        stopProgressDialog();
                    }

                    @Override
                    public void onResponse(Object response) {
                        stopProgressDialog();
                    }
                });
    }

    @Override
    protected void initData() {
        tvTitle.setText("学习记录");
        getDataFromNet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }
}
