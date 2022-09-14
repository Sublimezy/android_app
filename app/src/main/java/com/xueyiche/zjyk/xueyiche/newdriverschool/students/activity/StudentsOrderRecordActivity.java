package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudentOrderRecordBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
//预约记录
public class StudentsOrderRecordActivity extends NewBaseActivity {


    @BindView(R.id.ll_exam_back)
    LinearLayout llExamBack;
    @BindView(R.id.tv_login_back)
    TextView tvLoginBack;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private StudentsOrderRecordAdapter testYearQuickAdapter;

    @Override
    protected int initContentView() {
        return R.layout.students_common_list_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {
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
    }

    @Override
    protected void initData() {
        tvLoginBack.setText("我的预约");
        rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        testYearQuickAdapter = new StudentsOrderRecordAdapter(R.layout.order_record_list_item_layout);
        rvList.setAdapter(testYearQuickAdapter);
        getDataFromNet();

    }

    private void getDataFromNet() {
        OkHttpUtils.post().url(AppUrl.reservationuserlist)
                .addParams("stu_user_id", PrefUtils.getParameter("user_id"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            StudentOrderRecordBean studentOrderRecordBean = JsonUtil.parseJsonToBean(string, StudentOrderRecordBean.class);
                            if (studentOrderRecordBean != null) {
                                int code = studentOrderRecordBean.getCode();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == code) {
                                            List<StudentOrderRecordBean.ContentBean> content = studentOrderRecordBean.getContent();
                                            if (content!=null&&content.size()>0) {
                                                testYearQuickAdapter.setNewData(studentOrderRecordBean.getContent());
                                            }else {
                                                testYearQuickAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout,null));
                                            }
                                        }else {
                                            testYearQuickAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout,null));

                                        }
                                    }
                                });
                            }
                        }
                        return string;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });
    }


    @OnClick({R.id.ll_exam_back, R.id.tv_login_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_login_back:
                finish();
                break;
        }
    }

    class StudentsOrderRecordAdapter extends BaseQuickAdapter<StudentOrderRecordBean.ContentBean, BaseViewHolder> {

        public StudentsOrderRecordAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, StudentOrderRecordBean.ContentBean item) {
            String entry_project = item.getEntry_project();
            String sys_time = item.getSys_time();
            String coach_name = item.getCoach_name();
            String coach_phone = item.getCoach_phone();
            String driving_practice = item.getDriving_practice();
            String practice_time = item.getPractice_time();
            String num_class = item.getNum_class();
            int training_id = item.getTraining_id();
            RelativeLayout rlName = helper.getView(R.id.rlName);
            TextView tvCallPhone = helper.getView(R.id.tvCallPhone);
            TextView tvTimeList = helper.getView(R.id.tvTimeList);
            LinearLayout llAll = helper.getView(R.id.llAll);
            String selected_period = item.getSelected_period();
            if (!TextUtils.isEmpty(coach_name)) {
                rlName.setVisibility(View.VISIBLE);
                tvTimeList.setVisibility(View.VISIBLE);
                helper.setText(R.id.tvTimeList, "" + selected_period);
                helper.setText(R.id.tvName, coach_name);
                helper.setText(R.id.tvThree,  "共"+num_class+"学时");
            } else {
                rlName.setVisibility(View.GONE);
                tvTimeList.setVisibility(View.GONE);
            }
            helper.setText(R.id.tvOne, driving_practice );
            helper.setText(R.id.tvTwo, "（"+ practice_time+"）");

            if (!TextUtils.isEmpty(entry_project)) {
                helper.setText(R.id.tvTitle, entry_project);
            }
            if (!TextUtils.isEmpty(sys_time)) {
                helper.setText(R.id.tvTime, "提交时间" + sys_time);
            }
            tvCallPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(coach_phone)) {
                        XueYiCheUtils.CallPhone(StudentsOrderRecordActivity.this, "是否拨打教练电话？", coach_phone);
                    } else {
                        showToastShort("无效号码!");
                    }
                }
            });
            llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("科目二".equals(entry_project) || "科目三".equals(entry_project)) {
                        if (!"预约考试".equals(driving_practice)) {
                            Intent intent = new Intent(App.context, StudentsOrderContentActivity.class);
                            intent.putExtra("training_id",""+training_id);
                            startActivity(intent);
                        }else {
                            showToastShort("无详情!");
                        }
                    } else {
                        showToastShort("无详情!");
                    }
                }
            });
        }
    }
}
